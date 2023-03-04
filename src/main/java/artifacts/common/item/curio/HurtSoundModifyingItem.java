package artifacts.common.item.curio;

import artifacts.common.config.ModConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.PlayLevelSoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;

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
        public void onPlaySoundAtEntity(PlayLevelSoundEvent.AtEntity event) {
            if (ModConfig.client.modifyHurtSounds.get()
                    && event.getSound() != null
                    && isHurtSound(event.getSound().get())
                    && event.getEntity() instanceof LivingEntity entity
                    && CuriosApi.getCuriosHelper().findFirstCurio(entity, HurtSoundModifyingItem.this).isPresent()) {
                entity.getCommandSenderWorld().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), hurtSound, event.getSource(), 1, (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.2F + 1, false);
            }
        }

        private boolean isHurtSound(SoundEvent event) {
            return event == SoundEvents.PLAYER_HURT
                    || event == SoundEvents.PLAYER_HURT_DROWN
                    || event == SoundEvents.PLAYER_HURT_ON_FIRE
                    || event == SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH;
        }
    }
}
