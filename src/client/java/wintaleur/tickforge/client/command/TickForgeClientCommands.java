package wintaleur.tickforge.client.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import wintaleur.tickforge.client.command.implementation.HUDCommand;
import wintaleur.tickforge.common.command.CommandFactory;
import wintaleur.tickforge.common.command.CommandHelp;
import wintaleur.tickforge.common.command.CommandNames;
import wintaleur.tickforge.common.command.CommandRegistry;
import wintaleur.tickforge.common.utils.Utils;
import wintaleur.tickforge.server.TickForge;

import java.util.List;

/**
 * Branche CLIENT de /tickforge (alias /tf).
 *
 * <pre>
 * /tf client          -> liste des sous-commandes client
 * /tf client menu     -> HUDCommand
 * /tf server ...      -> transmis au serveur (voir {@link #serverPassthrough()})
 * </pre>
 *
 * Pour ajouter une commande client : créer une classe avec une méthode
 * {@code build()} puis l'ajouter avec {@code .then(...)} dans {@link #clientBranch()}.
 */
public final class TickForgeClientCommands {

    private TickForgeClientCommands() {}

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            LiteralCommandNode<FabricClientCommandSource> root = CommandRegistry.registerRoot(
                    dispatcher,
                    builder -> builder
                            .then(clientBranch())
                            .then(serverPassthrough())
            );

            warnIfMirrorOutdated(root.getChild(CommandNames.CLIENT));
        });
    }

    /**
     * Le serveur publie un miroir de cette branche pour les suggestions du chat
     * (voir TickForgeCommands#clientMirror). Si on ajoute une commande client sans
     * mettre à jour {@link CommandNames#CLIENT_COMMANDS}, elle fonctionnera mais ne
     * sera pas suggérée : on le dit dans les logs plutôt que de laisser dériver.
     */
    private static void warnIfMirrorOutdated(CommandNode<FabricClientCommandSource> clientBranch) {
        List<String> real = CommandHelp.childNames(clientBranch);
        List<String> declared = CommandNames.CLIENT_COMMANDS.stream().sorted().toList();

        if (!real.equals(declared)) {
            TickForge.LOGGER.warn(
                    "Branche client {} différente de CommandNames.CLIENT_COMMANDS {} : "
                            + "les suggestions du chat seront incomplètes.",
                    real, declared
            );
        }
    }

    private static LiteralArgumentBuilder<FabricClientCommandSource> clientBranch() {
        return CommandFactory.<FabricClientCommandSource>literal(CommandNames.CLIENT)
                .executes(TickForgeClientCommands::clientHelp)
                .then(HUDCommand.build());
    }

    /**
     * Les commandes client et serveur partagent la même racine (/tf). Fabric essaie
     * d'abord l'arbre client : si la racine existe mais que la suite ne correspond à
     * rien ("/tf server test"), il affiche "Incorrect argument" au lieu d'envoyer la
     * commande au serveur.
     *
     * <p>Ce nœud sans {@code executes} absorbe "/tf server ..." : Brigadier le parse
     * mais ne trouve aucune commande exécutable, ce qui donne "commande inconnue",
     * un cas que Fabric transmet au serveur. Si ce comportement change un jour dans
     * Fabric, supprimer simplement ce nœud.
     */
    private static LiteralArgumentBuilder<FabricClientCommandSource> serverPassthrough() {
        return CommandFactory.<FabricClientCommandSource>literal(CommandNames.SERVER)
                .then(RequiredArgumentBuilder.<FabricClientCommandSource, String>argument(
                        "args", StringArgumentType.greedyString()
                ));
    }

    private static int clientHelp(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(
                Utils.chatPrint("TickForge (client) : " + String.join(", ", CommandHelp.childNames(context)))
        );

        return Command.SINGLE_SUCCESS;
    }
}
