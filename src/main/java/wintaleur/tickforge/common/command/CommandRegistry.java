package wintaleur.tickforge.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;

import java.util.function.Function;

public final class CommandRegistry {

    private CommandRegistry() {}

    public static <S> LiteralCommandNode<S> register(
            CommandDispatcher<S> dispatcher,
            LiteralArgumentBuilder<S> command
    ) {
        return dispatcher.register(command);
    }

    public static <S> LiteralCommandNode<S> register(
            CommandDispatcher<S> dispatcher,
            String name,
            Function<
                    LiteralArgumentBuilder<S>,
                    LiteralArgumentBuilder<S>
                    > configure
    ) {
        return register(dispatcher, CommandFactory.create(name, configure));
    }

    /**
     * Enregistre une commande racine et ses alias.
     *
     * <p>Chaque alias est une redirection vers la racine : il partage donc le même
     * arbre (pas de duplication) et la même condition {@code requires}.
     * Brigadier n'exécute pas la commande de la racine quand l'alias est tapé seul
     * ({@code /tf} sans argument), on la recopie donc explicitement sur l'alias.
     */
    public static <S> LiteralCommandNode<S> registerWithAliases(
            CommandDispatcher<S> dispatcher,
            String name,
            Function<
                    LiteralArgumentBuilder<S>,
                    LiteralArgumentBuilder<S>
                    > configure,
            String... aliases
    ) {
        LiteralCommandNode<S> root = register(dispatcher, name, configure);

        for (String alias : aliases) {
            register(
                    dispatcher,
                    CommandFactory.<S>literal(alias)
                            .requires(root.getRequirement())
                            .executes(root.getCommand())
                            .redirect(root)
            );
        }

        return root;
    }

    /**
     * Enregistre la racine TickForge : {@code /tickforge} et {@code /tf}.
     * Utilisé à l'identique par le client et le serveur, seul le contenu change.
     */
    public static <S> LiteralCommandNode<S> registerRoot(
            CommandDispatcher<S> dispatcher,
            Function<
                    LiteralArgumentBuilder<S>,
                    LiteralArgumentBuilder<S>
                    > configure
    ) {
        return registerWithAliases(dispatcher, CommandNames.ROOT, configure, CommandNames.ALIAS);
    }
}
