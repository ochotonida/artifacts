package artifacts.common.item.curio;

import artifacts.common.config.ModConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
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
        public void onPlaySoundAtEntity(PlaySoundAtEntityEvent event) {
            if (ModConfig.client.modifyHurtSounds.get()
                    && isHurtSound(event.getSound())
                    && event.getEntity() instanceof LivingEntity
                    && CuriosApi.getCuriosHelper().findEquippedCurio(HurtSoundModifyingItem.this, ((LivingEntity) event.getEntity())).isPresent()) {
                event.getEntity().getCommandSenderWorld().playLocalSound(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), hurtSound, event.getCategory(), 1, (((LivingEntity) event.getEntity()).getRandom().nextFloat() - ((LivingEntity) event.getEntity()).getRandom().nextFloat()) * 0.2F + 1, false);
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
