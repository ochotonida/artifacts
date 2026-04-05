package artifacts.integration;

import io.github.apace100.origins.power.OriginsPowerTypes;
import net.minecraft.world.entity.LivingEntity;

public class OriginsCompat {

    public static boolean hasWaterBreathing(LivingEntity entity) {
        return OriginsPowerTypes.WATER_BREATHING.isActive(entity);
    }
}
