package artifacts.common.item;

import artifacts.ModItems;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class BaubleLightningScarf extends BaubleBase {

    public BaubleLightningScarf() {
        super("bauble_lightning_scarf", BaubleType.BODY);
    }

    public static void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer && BaubleType.BODY.hasSlot(BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleLightningScarf))) {
            if (event.getSource() == DamageSource.LIGHTNING_BOLT) {
                event.setCanceled(true);
            } else if (event.getSource().getTrueSource() instanceof EntityLiving && ((EntityPlayer) event.getEntity()).getRNG().nextInt(2) == 0) {
                EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
                if (attacker.world.canSeeSky(attacker.getPosition())) {
                    attacker.world.addWeatherEffect(new EntityLightningBolt(attacker.world, attacker.posX, attacker.posY, attacker.posZ, false));
                }
            }
        }
    }
}
