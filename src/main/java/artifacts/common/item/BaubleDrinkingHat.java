package artifacts.common.item;

import artifacts.common.init.ModItems;
import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public abstract class BaubleDrinkingHat {

    @SubscribeEvent
    public static void onItemUseStart(LivingEntityUseItemEvent.Start event) {
        if (event.getEntityLiving() instanceof EntityPlayer && BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntityLiving(), ModItems.DRINKING_HAT) != -1) {
            if (event.getItem().getItemUseAction() == EnumAction.DRINK) {
                event.setDuration(event.getDuration() / 4);
            }
        }
    }
}
