package wintaleur.tickforge.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import java.util.function.Function;

public final class CommandFactory {

    private CommandFactory() {
    }

    /**
     * Crée un littéral Brigadier après avoir vérifié son nom.
     *
     * <p>Un littéral ne peut jamais contenir d'espace : Brigadier découpe la saisie
     * sur les espaces, donc un littéral {@code "tickforge server"} serait enregistré
     * sans erreur mais ne pourrait jamais être tapé. On échoue donc dès le démarrage
     * du jeu plutôt que d'avoir une commande silencieusement inaccessible.
     * Pour imbriquer, utiliser {@code .then(literal("server"))}.
     */
    public static <S> LiteralArgumentBuilder<S> literal(String name) {
        requireValidName(name);
        return LiteralArgumentBuilder.literal(name);
    }

    public static <S> LiteralArgumentBuilder<S> create(
            String name,
            Function<LiteralArgumentBuilder<S>,
                    LiteralArgumentBuilder<S>> configure
    ) {
        return configure.apply(literal(name));
    }

    private static void requireValidName(String name) {
        if (name == null || name.isEmpty() || name.chars().anyMatch(Character::isWhitespace)) {
            throw new IllegalArgumentException(
                    "Nom de commande invalide : \"" + name + "\" "
                            + "(vide ou contenant un espace ; imbrique avec .then(literal(...)))"
            );
        }
    }
}
