package artifacts.client;

import artifacts.Artifacts;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.PlayLevelSoundEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashMap;
import java.util.Map;

public class HurtSoundEventHandler {

    private static final Map<WearableArtifactItem, SoundEvent> HURT_SOUNDS = new HashMap<>();

    public static void setup() {
        MinecraftForge.EVENT_BUS.addListener(HurtSoundEventHandler::onPlaySoundAtEntity);
        HURT_SOUNDS.put(ModItems.BUNNY_HOPPERS.get(), SoundEvents.RABBIT_HURT);
        HURT_SOUNDS.put(ModItems.KITTY_SLIPPERS.get(), SoundEvents.CAT_HURT);
    }
    
    public static void onPlaySoundAtEntity(PlayLevelSoundEvent.AtEntity event) {
        boolean canModifySound = Artifacts.CONFIG.client.modifyHurtSounds
                && event.getSound() != null
                && isHurtSound(event.getSound().get());
        if (canModifySound && event.getEntity() instanceof LivingEntity entity) {
            for (WearableArtifactItem item : HURT_SOUNDS.keySet()) {
                if (CuriosApi.getCuriosHelper().findFirstCurio(entity, item).isPresent()) {
                    playHurtSound(entity, item, event.getSource());
                }
            }
        }
    }

    private static void playHurtSound(LivingEntity entity, WearableArtifactItem item, SoundSource source) {
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        float pitch = (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.2F + 1;
        entity.getCommandSenderWorld().playLocalSound(x, y, z, HURT_SOUNDS.get(item), source, 1, pitch, false);
    }

    private static boolean isHurtSound(SoundEvent event) {
        return event == SoundEvents.PLAYER_HURT
                || event == SoundEvents.PLAYER_HURT_DROWN
                || event == SoundEvents.PLAYER_HURT_ON_FIRE
                || event == SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH;
    }
}
