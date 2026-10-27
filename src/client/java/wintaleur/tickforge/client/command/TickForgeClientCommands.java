
package wintaleur.tickforge.client.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.vellum.mod.client.VellumScreens;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import wintaleur.tickforge.client.command.implementation.HUDCommand;
import wintaleur.tickforge.common.command.CommandFactory;
import wintaleur.tickforge.common.command.CommandRegistry;

import java.awt.*;

public final class TickForgeClientCommands {

    private TickForgeClientCommands() {}

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            CommandRegistry.register(dispatcher, "tickforge client", HUDCommand::build);
        });
    }
}
