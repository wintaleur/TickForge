package wintaleur.tickforge.server.command.implementation;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import wintaleur.tickforge.common.command.CommandFactory;
import wintaleur.tickforge.common.utils.Utils;

/** /tf server test */
public final class TestCommand {
    private TestCommand() {}

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return CommandFactory.<CommandSourceStack>literal("test")
                .executes(context -> {
                    context.getSource().sendSuccess(
                            () -> Utils.chatPrint("Permissions serveurs accessibles !"),
                            false
                    );

                    return Command.SINGLE_SUCCESS;
                });
    }
}
