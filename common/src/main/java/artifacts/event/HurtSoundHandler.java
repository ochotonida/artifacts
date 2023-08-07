package artifacts.event;

import artifacts.Artifacts;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;

public class HurtSoundHandler {

    public static void onPlaySoundAtEntity(LivingEntity entity, float volume, float pitch) {
        if (Artifacts.CONFIG.common.modifyHurtSounds) {
            playHurtSound(ModItems.BUNNY_HOPPERS.get(), entity, SoundEvents.RABBIT_HURT, volume, pitch);
            playHurtSound(ModItems.KITTY_SLIPPERS.get(), entity, SoundEvents.CAT_HURT, volume, pitch);
        }
    }

    private static void playHurtSound(WearableArtifactItem item, LivingEntity entity, SoundEvent soundEvent, float volume, float pitch) {
        if (PlatformServices.platformHelper.isEquippedBy(entity, item)) {
            entity.playSound(soundEvent, volume, pitch );
        }
    }
}
