package wintaleur.tickforge.client.command.implementation;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.vellum.mod.client.VellumScreens;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import wintaleur.tickforge.common.command.CommandFactory;
import wintaleur.tickforge.common.utils.Utils;

public final class HUDCommand {
    private HUDCommand(){}

    public static <S> LiteralArgumentBuilder<S> build(LiteralArgumentBuilder<S> builder) {
        return builder.then(
                CommandFactory.<S>literal("menu").executes(context -> {

                    var source = Utils.getClientSource(context);

                    if (source == null) {
                        System.out.println("Tried to open Menu on an inexistant source or server.");
                        return 0;
                    };

                    source.getClient().execute(
                            () -> VellumScreens.open("tickforge:menu.html")
                    );

                    return 1;
                })
        );
    }
}
