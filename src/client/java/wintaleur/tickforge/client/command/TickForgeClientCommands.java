package wintaleur.tickforge.client.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import wintaleur.tickforge.client.command.implementation.HUDCommand;
import wintaleur.tickforge.common.command.CommandFactory;

import java.awt.*;

public final class TickForgeClientCommands {
    private TickForgeClientCommands() {}
    public static <S> LiteralArgumentBuilder<S> build(LiteralArgumentBuilder<S> builder) {
        return builder
                .then(CommandFactory.<S>literal("menu").executes(context -> {
                    // ouvrir le menu Vellum

                    return 1;
                }));
    }
}
