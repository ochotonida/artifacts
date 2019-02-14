package artifacts.common.item;

import artifacts.common.init.ModItems;
import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public abstract class BaublePocketPiston {

    @SubscribeEvent
    public static void onLivingKnockback(LivingKnockBackEvent event) {
        if (event.getAttacker() instanceof EntityPlayer && BaublesApi.isBaubleEquipped((EntityPlayer) event.getAttacker(), ModItems.POCKET_PISTON) != -1) {
            event.setStrength(event.getStrength() * 2);
        }
    }
}
