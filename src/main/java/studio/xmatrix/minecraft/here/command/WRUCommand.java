package studio.xmatrix.minecraft.mod.here.command;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class WRUCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = literal("wru")
                .then(argument("player", EntityArgumentType.player())
                        .executes(askPlayerBuilder()));
        dispatcher.register(literalArgumentBuilder);
    }

    private static Command<ServerCommandSource> askPlayerBuilder() {
        return c -> {
            ServerCommandSource source = c.getSource();
            MinecraftServer minecraftServer = source.getMinecraftServer();
            ServerPlayerEntity askPlayer = source.getPlayer();
            ServerPlayerEntity argumentPlayer = EntityArgumentType.getPlayer(c, "player");

            String text = String.format("§e%s§r 在询问 §3%s§r 的位置", askPlayer.getName().asString(),
                    argumentPlayer.getName().asString());
            minecraftServer.getPlayerManager().sendToAll(new LiteralText(text));
            return Command.SINGLE_SUCCESS;
        };
    }
}
