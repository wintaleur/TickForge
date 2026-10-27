package wintaleur.tickforge.client;

import net.fabricmc.api.ClientModInitializer;

import wintaleur.tickforge.client.command.TickForgeClientCommands;

public class TickForgeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Branche client : /tickforge client ... (alias /tf client ...)
		TickForgeClientCommands.register();
	}
}
