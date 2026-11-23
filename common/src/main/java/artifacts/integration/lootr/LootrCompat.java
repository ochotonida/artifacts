package artifacts.integration.lootr;

import artifacts.entity.MimicEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import noobanidus.mods.lootr.common.particle.ParticleColorOption;

public class LootrCompat {

    private static final double PARTICLE_XZ_SPREAD = 0.5;
    private static final double PARTICLE_Y_SPREAD = 0.02;
    private static final double PARTICLE_Y_MOTION_SPREAD = 0.02;
    private static final double PARTICLE_Y_OFFSET = 0.95;
    private static final int PARTICLE_FREQUENCY = 3;

    public static boolean useVanillaTextures() {
        return LootrAPI.isVanillaTextures();
    }

    public static void maybeSpawnParticles(MimicEntity entity) {
        if (LootrAPI.shouldDisplayUnopenedParticles() && entity.level().isClientSide()) {
            RandomSource random = entity.level().getRandom();
            if (random.nextInt(PARTICLE_FREQUENCY) == 0) {
                spawnParticle(entity.level(), random, entity.position());
            }
        }
    }

    private static void spawnParticle(Level level, RandomSource random, Vec3 position) {
        double x = position.x + (random.nextDouble() - 0.5) * PARTICLE_XZ_SPREAD;
        double y = position.y + PARTICLE_Y_OFFSET + random.nextDouble() * PARTICLE_Y_SPREAD;
        double z = position.z + (random.nextDouble() - 0.5) * PARTICLE_XZ_SPREAD;
        double yMotion = random.nextDouble() * PARTICLE_Y_MOTION_SPREAD;

        ParticleOptions options = new ParticleColorOption(
                LootrRegistry.getUnopenedParticleType(),
                LootrAPI.DEFAULT_PARTICLE_COLOR,
                LootrAPI.DEFAULT_PARTICLE_COLOR,
                false
        );
        level.addParticle(options, x, y, z, 0, yMotion, 0);
    }
}
