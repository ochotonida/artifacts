package artifacts.forge.item.wearable.hands;

import artifacts.forge.ArtifactsForge;
import artifacts.forge.item.wearable.AttributeModifyingItem;
import artifacts.forge.registry.ModGameRules;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class PowerGloveItem extends AttributeModifyingItem {

    public PowerGloveItem() {
        super(Attributes.ATTACK_DAMAGE, UUID.fromString("126a0b73-ae15-466c-a75b-28bbd61d1374"), ArtifactsForge.id("power_glove_attack_damage_bonus").toString());
    }

    @Override
    protected double getAmount() {
        return Math.max(0, ModGameRules.POWER_GLOVE_ATTACK_DAMAGE_BONUS.get());
    }
}
