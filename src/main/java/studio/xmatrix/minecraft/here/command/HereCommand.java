package studio.xmatrix.minecraft.mod.here.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.dimension.DimensionType;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.server.command.CommandManager.literal;


public class HereCommand {

    private static final Map<DimensionType, String> dimensionDisplay = new HashMap<DimensionType, String>() {{
        put(DimensionType.OVERWORLD, "§2主世界§r");
        put(DimensionType.THE_NETHER, "§4地狱§r");
        put(DimensionType.THE_END, "§5末地§r");
    }};

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = literal("here")
                .executes(hereBuilder());
        dispatcher.register(literalArgumentBuilder);
    }

    private static Command<ServerCommandSource> hereBuilder() {
        return c -> {
            ServerCommandSource source = c.getSource();
            MinecraftServer minecraftServer = source.getMinecraftServer();
            ServerPlayerEntity player = source.getPlayer();
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 15 * 20));

            String text = String.format("§e%s§r 在 %s§3「x:%d, y:%d, z:%d」§r向大家打招呼", player.getName().asString(),
                    dimensionDisplay.get(player.dimension),
                    (int) player.getPos().x, (int) player.getPos().y, (int) player.getPos().z);
            minecraftServer.getPlayerManager().sendToAll(new LiteralText(text));
            source.sendFeedback(new LiteralText("您将会被高亮15秒"), false);
            return Command.SINGLE_SUCCESS;
        };
    }
}
