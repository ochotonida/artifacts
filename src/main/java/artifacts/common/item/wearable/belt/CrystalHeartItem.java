package artifacts.common.item.wearable.belt;

import artifacts.Artifacts;
import artifacts.common.init.ModGameRules;
import artifacts.common.item.wearable.AttributeModifyingItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class CrystalHeartItem extends AttributeModifyingItem {

    public CrystalHeartItem() {
        super(Attributes.MAX_HEALTH, UUID.fromString("99fa0537-90b9-481a-bc76-4650987faba3"), Artifacts.id("crystal_heart_health_bonus").toString());
    }

    @Override
    protected double getAmount() {
        return Math.max(0, ModGameRules.CRYSTAL_HEART_HEALTH_BONUS.get());
    }

    @Override
    protected void onAttributeUpdated(LivingEntity entity) {
        if (entity.getHealth() > entity.getMaxHealth()) {
            entity.setHealth(entity.getMaxHealth());
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND, 1, 1);
    }
}
