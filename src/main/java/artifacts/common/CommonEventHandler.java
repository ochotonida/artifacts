package artifacts.common;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@SuppressWarnings("unused")
public class CommonEventHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource() instanceof EntityDamageSource && !(event.getSource() instanceof EntityDamageSourceIndirect) && !((EntityDamageSource) event.getSource()).getIsThornsDamage()) {
            if (event.getSource().getTrueSource() instanceof EntityPlayer) {
                EntityPlayer attacker = (EntityPlayer) event.getSource().getTrueSource();
                if (BaublesApi.isBaubleEquipped(attacker, ModItems.baubleMagmaStone) != -1 && !event.getEntity().isImmuneToFire()) {
                    event.getEntity().setFire(3);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingKnockback(LivingKnockBackEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleCobaltShield) != -1) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleLuckyHorseshoe) != -1) {
                if (event.getDistance() > 5) {
                    PotionEffect potioneffect = event.getEntityLiving().getActivePotionEffect(MobEffects.JUMP_BOOST);
                    float f = potioneffect == null ? 0.0F : (float)(potioneffect.getAmplifier() + 1);
                    int i = MathHelper.ceil((event.getDistance() - 3.0F - f) * event.getDamageMultiplier());
                    if (i > 0) {
                        event.getEntity().playSound(SoundEvents.ENTITY_GENERIC_SMALL_FALL, 1, 1);
                    }
                }
                event.setCanceled(true);
            }
        }
    }
}
