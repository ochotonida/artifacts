package artifacts.item.wearable;

import artifacts.registry.ModGameRules;
import artifacts.registry.ModSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class WhoopeeCushionItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return false;
    }

    @Override
    public void wornTick(LivingEntity entity, ItemStack stack) {
        if (!entity.level.isClientSide()) {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.getBoolean("HasFarted") && !entity.isShiftKeyDown()) {
                tag.putBoolean("HasFarted", false);
            } else if (!tag.getBoolean("HasFarted") && entity.isShiftKeyDown()) {
                tag.putBoolean("HasFarted", true);
                double fartChance = ModGameRules.WHOOPEE_CUSHION_FART_CHANCE.get() / 100D;
                if (entity.getRandom().nextFloat() < fartChance) {
                    entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSoundEvents.FART.get(), SoundSource.PLAYERS, 1, 0.9F + entity.getRandom().nextFloat() * 0.2F);
                }
            }
        }
    }

    @Override
    public SoundEvent getEquipSound() {
        return ModSoundEvents.FART.get();
    }
}
