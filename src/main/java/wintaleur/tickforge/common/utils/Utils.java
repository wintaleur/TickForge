package wintaleur.tickforge.common.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

/**
 * Utilitaires communs client + serveur.
 *
 * <p>Ne doit référencer AUCUNE classe client (FabricClientCommandSource, Minecraft...) :
 * ce code est chargé sur les serveurs dédiés.
 */
public final class Utils {

    private Utils() {}

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
