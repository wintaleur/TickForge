package wintaleur.tickforge.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public final class CommandRegistry {

    private CommandRegistry() {}

    public static <S> void register(
            CommandDispatcher<S> dispatcher,
            LiteralArgumentBuilder<S> command
    ) {
        dispatcher.register(command);
    }

    public static <S> void register(
            CommandDispatcher<S> dispatcher,
            String name,
            java.util.function.Function<
                    LiteralArgumentBuilder<S>,
                    LiteralArgumentBuilder<S>
                    > configure
    ) {
        dispatcher.register(CommandFactory.create(name, configure));
    }
}