package artifacts.common.item.wearable.necklace;

import artifacts.common.init.ModGameRules;
import artifacts.common.item.wearable.WearableArtifactItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class PanicNecklaceItem extends WearableArtifactItem {

    public PanicNecklaceItem() {
        addListener(LivingHurtEvent.class, this::onLivingHurt);
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.PANIC_NECKLACE_SPEED_DURATION.get() <= 0 || ModGameRules.PANIC_NECKLACE_SPEED_LEVEL.get() <= 0;
    }

    private void onLivingHurt(LivingHurtEvent event, LivingEntity wearer) {
        if (!wearer.level.isClientSide() && event.getAmount() >= 1) {
            int duration = Math.max(0, ModGameRules.PANIC_NECKLACE_SPEED_DURATION.get() * 20);
            int level = Math.min(127, ModGameRules.PANIC_NECKLACE_SPEED_LEVEL.get() - 1);

            if (duration > 0 && level >= 0) {
                wearer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, duration, level, false, false));
            }
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND, 1, 1);
    }
}
