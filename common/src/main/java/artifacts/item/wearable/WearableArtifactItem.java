package artifacts.item.wearable;

import artifacts.Artifacts;
import artifacts.client.ToggleKeyHandler;
import artifacts.item.ArtifactItem;
import artifacts.platform.PlatformServices;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public abstract class WearableArtifactItem extends ArtifactItem {

    public boolean isEquippedBy(@Nullable LivingEntity entity) {
        return PlatformServices.platformHelper.isEquippedBy(entity, this);
    }

    public Stream<ItemStack> findAllEquippedBy(LivingEntity entity) {
        return PlatformServices.platformHelper.findAllEquippedBy(entity, this);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    public void onEquip(LivingEntity entity, ItemStack stack) {

    }

    public void onUnequip(LivingEntity entity, ItemStack stack) {

    }

    public void wornTick(LivingEntity entity, ItemStack stack) {

    }

    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_GENERIC;
    }

    public int getFortuneLevel() {
        return 0;
    }

    public int getLootingLevel() {
        return 0;
    }

    public void toggleItem(ServerPlayer player) {
        findAllEquippedBy(player).forEach(stack -> setActivated(stack, !isActivated(stack)));
    }

    public static boolean isActivated(ItemStack stack) {
        return !stack.hasTag() || stack.getOrCreateTag().getBoolean("isActivated");
    }

    public static void setActivated(ItemStack stack, boolean active) {
        stack.getOrCreateTag().putBoolean("isActivated", active);
    }

    @Override
    protected void addEffectsTooltip(ItemStack stack, List<MutableComponent> tooltip) {
        super.addEffectsTooltip(stack, tooltip);
        KeyMapping key = ToggleKeyHandler.getToggleKey(this);
        if (key != null && (!key.isUnbound() || !isActivated(stack))) {
            tooltip.add(Component.translatable("%s.tooltip.toggle_keymapping".formatted(Artifacts.MOD_ID), key.getTranslatedKeyMessage()));
        }
    }
}
