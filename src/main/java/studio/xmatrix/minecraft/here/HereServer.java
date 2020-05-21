package studio.xmatrix.minecraft.mod.here;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import studio.xmatrix.minecraft.mod.here.command.HereCommand;
import studio.xmatrix.minecraft.mod.here.command.WRUCommand;


public class HereServer implements DedicatedServerModInitializer {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitializeServer() {
        registerCommands();
    }

    private void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            if (!dedicated) { // 该分支在正常情况不会被执行
                LOGGER.error("Here can only be used on dedicated server, but now on an integrated server!");
                return;
            }
            HereCommand.register(dispatcher);
            WRUCommand.register(dispatcher);
        });
    }
}
