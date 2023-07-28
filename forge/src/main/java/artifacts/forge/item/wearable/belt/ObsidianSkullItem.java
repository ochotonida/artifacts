package artifacts.forge.item.wearable.belt;

import artifacts.forge.event.ArtifactEventHandler;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ObsidianSkullItem extends WearableArtifactItem {

    public ObsidianSkullItem() {
        ArtifactEventHandler.addListener(this, LivingHurtEvent.class, this::onLivingHurt);
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
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }
}
