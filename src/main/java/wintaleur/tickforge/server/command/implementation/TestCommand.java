package wintaleur.tickforge.server.command.implementation;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import wintaleur.tickforge.common.command.CommandFactory;
import wintaleur.tickforge.common.utils.Utils;

public final class TestCommand {
    private TestCommand() {}

    public static LiteralArgumentBuilder<CommandSourceStack> build(
            LiteralArgumentBuilder<CommandSourceStack> builder
    ) {
        return builder.then(
                CommandFactory.<CommandSourceStack>literal("test")
                        .executes(context -> {
                            var source = context.getSource();

                            if (source == null) return 0;

                            source.sendSuccess(
                                    () -> Utils.chatPrint("Permissions serveurs accessibles !"),
                                    false
                            );

                            return 1;
                        })
        );
    }
}