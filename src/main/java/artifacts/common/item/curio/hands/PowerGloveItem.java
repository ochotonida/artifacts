package artifacts.common.item.curio.hands;

import artifacts.Artifacts;
import artifacts.common.init.ModGameRules;
import artifacts.common.item.curio.AttributeModifyingItem;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class PowerGloveItem extends AttributeModifyingItem {

    public PowerGloveItem() {
        super(Attributes.ATTACK_DAMAGE, UUID.fromString("126a0b73-ae15-466c-a75b-28bbd61d1374"), Artifacts.id("power_glove_attack_damage_bonus").toString());
    }

    @Override
    protected double getAmount() {
        return Math.max(0, ModGameRules.POWER_GLOVE_ATTACK_DAMAGE_BONUS.get());
    }
}
