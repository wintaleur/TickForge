package wintaleur.tickforge.client;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import wintaleur.tickforge.client.command.TickForgeClientCommands;
import wintaleur.tickforge.common.command.CommandRegistry;

public class TickForgeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		TickForgeClientCommands.register();
	}
}