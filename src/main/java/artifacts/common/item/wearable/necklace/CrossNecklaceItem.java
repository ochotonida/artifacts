package artifacts.common.item.wearable.necklace;

import artifacts.common.init.ModGameRules;
import artifacts.common.item.wearable.WearableArtifactItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CrossNecklaceItem extends WearableArtifactItem {

    private boolean canApplyBonus(ItemStack stack) {
        return ModGameRules.CROSS_NECKLACE_BONUS_INVINCIBILITY_TICKS.get() > 0 && stack.getOrCreateTag().getBoolean("CanApplyBonus");
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.CROSS_NECKLACE_BONUS_INVINCIBILITY_TICKS.get() <= 0;
    }

    private static void setCanApplyBonus(ItemStack stack, boolean canApplyBonus) {
        stack.getOrCreateTag().putBoolean("CanApplyBonus", canApplyBonus);
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND, 1, 1);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().invulnerableTime <= 10) {
            setCanApplyBonus(stack, true);
        } else if (canApplyBonus(stack)) {
            slotContext.entity().invulnerableTime += Math.min(60 * 20, Math.max(0, ModGameRules.CROSS_NECKLACE_BONUS_INVINCIBILITY_TICKS.get()));
            setCanApplyBonus(stack, false);
        }
    }
}
