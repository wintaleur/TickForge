package wintaleur.tickforge.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import java.util.function.Function;

public final class CommandFactory {

    private CommandFactory() {
    }

    public static <S> LiteralArgumentBuilder<S> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    public static <S> LiteralArgumentBuilder<S> create(
            String name,
            Function<LiteralArgumentBuilder<S>,
                    LiteralArgumentBuilder<S>> configure
    ) {
        return configure.apply(literal(name));
    }
}
