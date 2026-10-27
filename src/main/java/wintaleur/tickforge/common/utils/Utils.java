package wintaleur.tickforge.common.utils;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class Utils {

    public static FabricClientCommandSource getClientSource(
            CommandContext<?> context
    ) {
        return context.getSource() instanceof FabricClientCommandSource source
                ? source
                : null;
    }

    public static CommandSourceStack getServerSource(CommandContext<?> context) {
        return context.getSource() instanceof CommandSourceStack source
                ? source
                : null;
    }

    public static Component chatPrint(String message) {
        return Component.literal(message).withStyle(ChatFormatting.WHITE);
    }

    public static Component chatWarn(String message) {
        return Component.literal(message).withStyle(ChatFormatting.YELLOW);
    }

    public static Component chatErr(String message) {
        return Component.literal(message).withStyle(ChatFormatting.RED);
    }
}