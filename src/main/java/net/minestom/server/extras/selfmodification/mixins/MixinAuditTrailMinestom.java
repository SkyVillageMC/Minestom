package net.minestom.server.extras.selfmodification.mixins;

import net.minestom.server.MinecraftServer;
import net.minestom.server.log.Logger;
import org.spongepowered.asm.service.IMixinAuditTrail;

/**
 * Takes care of logging mixin operations
 */
public class MixinAuditTrailMinestom implements IMixinAuditTrail {

    public final static Logger LOGGER = new Logger(MinecraftServer.class);

    @Override
    public void onApply(String className, String mixinName) {
        LOGGER.debug("Applied mixin {} to class {}", mixinName, className);
    }

    @Override
    public void onPostProcess(String className) {
        LOGGER.debug("Post processing {}", className);
    }

    @Override
    public void onGenerate(String className, String generatorName) {
        LOGGER.debug("Generating class {} via generator {}", className, generatorName);
    }
}
