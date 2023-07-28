package artifacts.forge.mixin.item.wearable;

import artifacts.item.wearable.WearableArtifactItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Mixin(WearableArtifactItem.class)
public abstract class WearableArtifactItemMixin implements ICurioItem {

    @Shadow
    public abstract void wornTick(LivingEntity entity, ItemStack stack);

    @Shadow
    public abstract void onEquip(LivingEntity entity, ItemStack originalStack, ItemStack newStack);

    @Shadow
    public abstract void onUnequip(LivingEntity entity, ItemStack originalStack, ItemStack newStack);

    @Shadow
    public abstract SoundEvent getEquipSound();

    @Override
    public final void curioTick(SlotContext slotContext, ItemStack stack) {
        wornTick(slotContext.entity(), stack);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack originalStack, ItemStack newStack) {
        onEquip(slotContext.entity(), originalStack, newStack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack originalStack) {
        onUnequip(slotContext.entity(), originalStack, newStack);
    }

    @Override
    public final ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(getEquipSound(), 1, 1);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
