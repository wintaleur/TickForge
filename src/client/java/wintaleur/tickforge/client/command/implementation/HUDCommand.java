package wintaleur.tickforge.client.command.implementation;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import wintaleur.tickforge.client.gui.TickForgeScreen;
import wintaleur.tickforge.common.command.CommandFactory;
import wintaleur.tickforge.common.command.CommandNames;

/** /tf client menu */
public final class HUDCommand {
    private static final String MENU_PAGE = "menu.html";

    private HUDCommand() {}

    public static LiteralArgumentBuilder<FabricClientCommandSource> build() {
        return CommandFactory.<FabricClientCommandSource>literal(CommandNames.MENU)
                .executes(HUDCommand::openMenu);
    }

    private static int openMenu(CommandContext<FabricClientCommandSource> context) {
        TickForgeScreen.open(MENU_PAGE);

        return Command.SINGLE_SUCCESS;
    }
}
