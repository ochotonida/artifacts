package artifacts.common.item;

import artifacts.common.ModItems;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BaubleShield extends BaubleBase {

    public BaubleShield() {
        super("bauble_shield", BaubleType.CHARM);
        setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1);
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onLivingKnockback(LivingKnockBackEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleShield) != -1) {
                event.setCanceled(true);
            }
        }
    }
}
