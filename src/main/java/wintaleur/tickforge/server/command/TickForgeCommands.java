package wintaleur.tickforge.server.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import wintaleur.tickforge.common.command.CommandRegistry;
import wintaleur.tickforge.server.command.implementation.TestCommand;

public final class TickForgeCommands {
    private TickForgeCommands() {}

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environmment) -> {
            CommandRegistry.register(dispatcher, "tickforge server", TestCommand::build);
        });
    }
}