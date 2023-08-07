package artifacts.fabric.integration;

import artifacts.fabric.mixin.compat.origins.ConditionFactoryAccessor;
import artifacts.item.UmbrellaItem;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.origins.Origins;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class OriginsCompat implements CompatHandler {

    @Override
    public void run() {
        RegistryEntryAddedCallback.event(ApoliRegistries.ENTITY_CONDITION).register((rawId, id, conditionFactory) -> {
            // Held-up umbrella blocks origins:exposed_to_sun condition
            if (conditionFactory.getSerializerId().equals(new ResourceLocation(Origins.MODID, "exposed_to_sun"))) {
                //noinspection unchecked
                ConditionFactoryAccessor<LivingEntity> conditionAccess = (ConditionFactoryAccessor<LivingEntity>) conditionFactory;

                // Wrapper around original condition
                conditionAccess.setCondition((instance, entity) -> conditionAccess.getCondition().apply(instance, entity)
                        && !UmbrellaItem.isHoldingUmbrellaUpright(entity));
            }
        });
    }

    @Override
    public String getModId() {
        return "origins";
    }
}
