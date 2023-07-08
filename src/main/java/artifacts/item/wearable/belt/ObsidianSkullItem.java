package artifacts.item.wearable.belt;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ObsidianSkullItem extends WearableArtifactItem {

    public ObsidianSkullItem() {
        addListener(LivingHurtEvent.class, this::onLivingHurt);
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.OBSIDIAN_SKULL_FIRE_RESISTANCE_DURATION.get() <= 0;
    }

    private void onLivingHurt(LivingHurtEvent event, LivingEntity wearer) {
        if (
                !wearer.level().isClientSide
                && event.getAmount() >= 1
                && event.getSource().is(DamageTypeTags.IS_FIRE)
                && wearer instanceof Player player
                && !player.getCooldowns().isOnCooldown(this)
        ) {
            int cooldown = ModGameRules.OBSIDIAN_SKULL_FIRE_RESISTANCE_COOLDOWN.get() * 20;
            int fireResistanceDuration = ModGameRules.OBSIDIAN_SKULL_FIRE_RESISTANCE_DURATION.get() * 20;

            if (fireResistanceDuration > 0) {
                wearer.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, fireResistanceDuration, 0, false, false, true));
                if (cooldown > 0) {
                    player.getCooldowns().addCooldown(this, cooldown);
                }
            }
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_IRON, 1, 1);
    }
}
