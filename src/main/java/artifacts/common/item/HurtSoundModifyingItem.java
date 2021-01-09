package artifacts.common.item;

import artifacts.common.config.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class HurtSoundModifyingItem extends CurioItem {

    public HurtSoundModifyingItem(SoundEvent hurtSound) {
        MinecraftForge.EVENT_BUS.register(new HurtSoundHandler(hurtSound));
    }

    private class HurtSoundHandler {

        private final SoundEvent hurtSound;

        private HurtSoundHandler(SoundEvent hurtSound) {
            this.hurtSound = hurtSound;
        }

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        @SuppressWarnings("unused")
        public void onPlaySoundAtEntity(PlaySoundAtEntityEvent event) {
            if (Config.modifyHurtSounds
                    && isHurtSound(event.getSound())
                    && event.getEntity() instanceof LivingEntity
                    && isEquippedBy((LivingEntity) event.getEntity())) {
                event.getEntity().getEntityWorld().playSound(event.getEntity().getPosX(), event.getEntity().getPosY(), event.getEntity().getPosZ(), hurtSound, event.getCategory(), 1, (((LivingEntity) event.getEntity()).getRNG().nextFloat() - ((LivingEntity) event.getEntity()).getRNG().nextFloat()) * 0.2F + 1, false);
            }
        }

        private boolean isHurtSound(SoundEvent event) {
            return event == SoundEvents.ENTITY_PLAYER_HURT
                    || event == SoundEvents.ENTITY_PLAYER_HURT_DROWN
                    || event == SoundEvents.ENTITY_PLAYER_HURT_ON_FIRE
                    || event == SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH;
        }
    }
}
