package artifacts.common;

import baubles.api.BaublesApi;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@SuppressWarnings("unused")
public class CommonEventHandler {

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
            return;
        }

        if (event.getEntityLiving().getRNG().nextDouble() < ModConfig.everlastingFoodChance) {
            Item item;

            if (event.getEntity() instanceof EntityPig) {
                if (event.getEntityLiving().isBurning()) {
                    item = ModItems.EVERLASTING_COOKED_PORKCHOP;
                } else {
                    item = ModItems.EVERLASTING_PORKCHOP;
                }
            } else if (event.getEntity() instanceof EntityCow) {
                if (event.getEntityLiving().isBurning()) {
                    item = ModItems.EVERLASTING_COOKED_BEEF;
                } else {
                    item = ModItems.EVERLASTING_BEEF;
                }
            } else if (event.getEntity() instanceof EntitySheep) {
                if (event.getEntityLiving().isBurning()) {
                    item = ModItems.EVERLASTING_COOKED_MUTTON;
                } else {
                    item = ModItems.EVERLASTING_MUTTON;
                }
            } else if (event.getEntity() instanceof EntityChicken) {
                if (event.getEntityLiving().isBurning()) {
                    item = ModItems.EVERLASTING_COOKED_CHICKEN;
                } else {
                    item = ModItems.EVERLASTING_CHICKEN;
                }
            } else if (event.getEntity() instanceof EntityRabbit) {
                if (event.getEntityLiving().isBurning()) {
                    item = ModItems.EVERLASTING_COOKED_RABBIT;
                } else {
                    item = ModItems.EVERLASTING_RABBIT;
                }
            } else if (event.getEntity() instanceof EntityZombie) {
                item = ModItems.EVERLASTING_ROTTEN_FLESH;
            } else if (event.getEntity() instanceof EntitySpider) {
                item = ModItems.EVERLASTING_SPIDER_EYE;
            } else {
                return;
            }

            event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, new ItemStack(item)));
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource() instanceof EntityDamageSource && !(event.getSource() instanceof EntityDamageSourceIndirect) && !((EntityDamageSource) event.getSource()).getIsThornsDamage()) {
            if (event.getSource().getTrueSource() instanceof EntityPlayer) {
                EntityPlayer attacker = (EntityPlayer) event.getSource().getTrueSource();
                if (BaublesApi.isBaubleEquipped(attacker, ModItems.MAGMA_STONE) != -1 && !event.getEntity().isImmuneToFire()) {
                    event.getEntity().setFire(3);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingKnockback(LivingKnockBackEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.COBALT_SHIELD) != -1) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.LUCKY_HORSESHOE) != -1) {
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
