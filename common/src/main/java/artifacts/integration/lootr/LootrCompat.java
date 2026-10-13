package artifacts.integration.lootr;

import artifacts.entity.MimicEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import noobanidus.mods.lootr.common.particle.ParticleColorOption;

public class LootrCompat {
    private static final double[] xBounds = new double[]{0.25, 0.75};
    private static final double[] zBounds = new double[]{0.25, 0.75};
    private static final double yOffset = 0.95;

    private static double bounded(RandomSource random, double[] bounds) {
        double min = bounds[0];
        double max = bounds[1];
        return min + random.nextDouble() * (max - min);
    }

    public static boolean useVanillaTextures() {
        return LootrAPI.isVanillaTextures();
    }

    public static void maybeSpawnParticles (MimicEntity entity) {
        if (LootrAPI.shouldDisplayUnopenedParticles() && entity.level().isClientSide()) {
            RandomSource random = entity.level().getRandom();
            if (random.nextInt(3) == 0) {
                double xOff = bounded(random, xBounds);
                double zOff = bounded(random, zBounds);
                Vec3 pos = entity.position().subtract(0.5, 0, 0.5);
                int color = LootrAPI.DEFAULT_PARTICLE_COLOR;
                entity.level().addParticle(
                    new ParticleColorOption(LootrRegistry.getUnopenedParticleType(), color, color, false),
                    pos.x + xOff,
                    pos.y + yOffset + random.nextDouble() * 0.02,
                    pos.z + zOff,
                    0,
                    random.nextDouble() * 0.02,
                    0
                );
            }
        }
    }
}
