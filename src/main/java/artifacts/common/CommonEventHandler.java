package artifacts.common;

import artifacts.common.item.BaubleLightningAmulet;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class CommonEventHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        BaubleLightningAmulet.onLivingHurt(event);
    }
}
