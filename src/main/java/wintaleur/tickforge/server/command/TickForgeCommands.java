package wintaleur.tickforge.server.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import wintaleur.tickforge.common.command.CommandFactory;
import wintaleur.tickforge.common.command.CommandHelp;
import wintaleur.tickforge.common.command.CommandNames;
import wintaleur.tickforge.common.command.CommandRegistry;
import wintaleur.tickforge.common.utils.Utils;
import wintaleur.tickforge.server.command.implementation.TestCommand;

/**
 * Branche SERVEUR de /tickforge (alias /tf).
 *
 * <pre>
 * /tf                 -> aperçu des branches
 * /tf server          -> liste des sous-commandes serveur
 * /tf server test     -> TestCommand
 * /tf client ...      -> miroir (suggestions uniquement, voir {@link #clientMirror()})
 * </pre>
 *
 * Pour ajouter une commande serveur : créer une classe avec une méthode
 * {@code build()} puis l'ajouter avec {@code .then(...)} dans {@link #serverBranch()}.
 */
public final class TickForgeCommands {
    private TickForgeCommands() {}

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                CommandRegistry.registerRoot(dispatcher, root -> root
                        .executes(TickForgeCommands::overview)
                        .then(serverBranch())
                        .then(clientMirror())
                )
        );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> serverBranch() {
        return CommandFactory.<CommandSourceStack>literal(CommandNames.SERVER)
                .executes(TickForgeCommands::serverHelp)
                .then(TestCommand.build());
    }

    /**
     * Miroir de la branche client, publié dans l'arbre envoyé aux joueurs.
     *
     * <p>Le chat valide et suggère les commandes à partir de l'arbre reçu du serveur.
     * Les commandes client sont fusionnées dedans par Fabric, mais sous une racine
     * partagée (/tf) leurs sous-commandes n'y apparaissent pas : sans ce miroir,
     * "/tf client menu" s'exécute mais n'est ni suggéré ni reconnu dans le chat.
     *
     * <p>Ces nœuds ne s'exécutent presque jamais : un client avec le mod traite
     * "/tf client ..." lui-même, avant l'envoi au serveur. Ils ne répondent que
     * si le joueur n'a pas le mod, avec un message plus utile que "Incorrect argument".
     * Les noms viennent de {@link CommandNames#CLIENT_COMMANDS}.
     */
    private static LiteralArgumentBuilder<CommandSourceStack> clientMirror() {
        LiteralArgumentBuilder<CommandSourceStack> branch =
                CommandFactory.<CommandSourceStack>literal(CommandNames.CLIENT)
                        .executes(context -> clientOnly(context, CommandNames.CLIENT));

        for (String name : CommandNames.CLIENT_COMMANDS) {
            branch.then(
                    CommandFactory.<CommandSourceStack>literal(name)
                            .executes(context -> clientOnly(context, CommandNames.CLIENT + " " + name))
            );
        }

        return branch;
    }

    private static int clientOnly(CommandContext<CommandSourceStack> context, String path) {
        context.getSource().sendSuccess(
                () -> Utils.chatWarn(
                        "/" + CommandNames.ALIAS + " " + path
                                + " s'exécute côté client : installe TickForge sur ton client."
                ),
                false
        );

        return 0;
    }

    // Arrive ici pour "/tf" seul : le client n'a pas de commande sur la racine
    // et la transmet donc au serveur (voir TickForgeClientCommands).
    private static int overview(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(
                () -> Utils.chatPrint(
                        "TickForge : /" + CommandNames.ALIAS + " " + CommandNames.SERVER + " (serveur), /"
                                + CommandNames.ALIAS + " " + CommandNames.CLIENT + " (client)"
                ),
                false
        );

        return Command.SINGLE_SUCCESS;
    }

    private static int serverHelp(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(
                () -> Utils.chatPrint(
                        "TickForge (serveur) : " + String.join(", ", CommandHelp.childNames(context))
                ),
                false
        );

        return Command.SINGLE_SUCCESS;
    }
}
