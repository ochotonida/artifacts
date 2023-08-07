package artifacts.forge.curio;

import artifacts.item.wearable.WearableArtifactItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class WearableArtifactCurio implements ICurio {

    private final WearableArtifactItem item;
    private final ItemStack stack;

    public WearableArtifactCurio(WearableArtifactItem item, ItemStack stack) {
        this.item = item;
        this.stack = stack;
    }

    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public final void curioTick(SlotContext slotContext) {
        item.wornTick(slotContext.entity(), stack);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack originalStack) {
        item.onEquip(slotContext.entity(), stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
        item.onUnequip(slotContext.entity(), stack);
    }

    @Override
    public final ICurio.SoundInfo getEquipSound(SlotContext slotContext) {
        return new ICurio.SoundInfo(item.getEquipSound(), 1, 1);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext) {
        return true;
    }

    @Override
    public int getFortuneLevel(SlotContext slotContext, @Nullable LootContext lootContext) {
        return item.getFortuneLevel();
    }

    @Override
    public int getLootingLevel(SlotContext slotContext, DamageSource source, LivingEntity target, int baseLooting) {
        return item.getLootingLevel();
    }
}
