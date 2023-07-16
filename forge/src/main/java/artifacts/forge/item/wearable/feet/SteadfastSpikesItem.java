package artifacts.forge.item.wearable.feet;

import artifacts.forge.ArtifactsForge;
import artifacts.forge.item.wearable.AttributeModifyingItem;
import artifacts.forge.registry.ModGameRules;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class SteadfastSpikesItem extends AttributeModifyingItem {

    public SteadfastSpikesItem() {
        super(Attributes.KNOCKBACK_RESISTANCE, UUID.fromString("d5e712e8-3f85-436a-bd1d-506d791f7abd"), ArtifactsForge.id("steadfast_spikes_knockback_resistance").toString());
    }

    @Override
    protected double getAmount() {
        return ModGameRules.STEADFAST_SPIKES_ENABLED.get() ? 1 : 0;
    }
}
