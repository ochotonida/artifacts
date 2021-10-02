package artifacts.common.item.curio.belt;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class CrystalHeartItem extends CurioItem {

    public CrystalHeartItem() {
        addListener(LivingDamageEvent.class, this::onLivingDamage);
    }

    private static AttributeModifier getHealthBonus() {
        int healthBonus = ModConfig.server.crystalHeart.healthBonus.get();
        return new AttributeModifier(UUID.fromString("99fa0537-90b9-481a-bc76-4650987faba3"), "artifacts:crystal_heart_health_bonus", healthBonus, AttributeModifier.Operation.ADDITION);
    }

    private void onLivingDamage(LivingDamageEvent event, LivingEntity wearer) {
        if (!event.isCanceled() && event.getAmount() >= 1) {
            damageEquippedStacks(wearer, (int) event.getAmount());
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack originalStack, ItemStack newStack) {
        if (!ModConfig.server.isCosmetic(this) && !slotContext.entity().level.isClientSide()) {
            AttributeInstance health = slotContext.entity().getAttribute(Attributes.MAX_HEALTH);
            AttributeModifier healthBonus = getHealthBonus();
            if (health != null && !health.hasModifier(healthBonus)) {
                health.addPermanentModifier(healthBonus);
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack originalStack, ItemStack newStack) {
        if (!slotContext.entity().level.isClientSide()) {
            AttributeInstance health = slotContext.entity().getAttribute(Attributes.MAX_HEALTH);
            AttributeModifier healthBonus = getHealthBonus();
            if (health != null && health.hasModifier(healthBonus)) {
                health.removeModifier(healthBonus);
                if (slotContext.entity().getHealth() > slotContext.entity().getMaxHealth()) {
                    slotContext.entity().setHealth(slotContext.entity().getMaxHealth());
                }
            }
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND, 1, 1);
    }
}
