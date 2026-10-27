package wintaleur.tickforge.server.command;

import wintaleur.tickforge.common.command.CommandFactory;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public final class TickForgeCommands {
    private TickForgeCommands() {}

    public static <S> LiteralArgumentBuilder<S> build(LiteralArgumentBuilder<S> builder) {
        return builder
                .then(CommandFactory.<S>literal("testserver")
                        .executes(context -> {
                            if (context.getSource() instanceof CommandSourceStack source) {
                                Component message = Component.literal("Permissions serveurs accessibles!")
                                        .withStyle(ChatFormatting.GREEN);

                                source.sendSuccess(() -> message, false);
                            }
                            return 1;
                        })
                );
    }
}