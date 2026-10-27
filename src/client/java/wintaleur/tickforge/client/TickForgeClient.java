package wintaleur.tickforge.client;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import wintaleur.tickforge.client.command.TickForgeClientCommands;
import wintaleur.tickforge.common.command.CommandRegistry;

public class TickForgeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// commandes client
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			CommandRegistry.register(dispatcher, "tickforge", TickForgeClientCommands::build);
		});
	}
}