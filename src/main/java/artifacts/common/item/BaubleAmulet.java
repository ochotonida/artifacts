package artifacts.common.item;

import artifacts.client.model.ModelAmulet;
import artifacts.common.ModItems;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.render.IRenderBauble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber
public class BaubleAmulet extends BaubleBase implements IRenderBauble {

    protected ModelBase model = new ModelAmulet();

    protected ResourceLocation textures;

    public BaubleAmulet(String name, ResourceLocation textures) {
        super(name, BaubleType.AMULET);
        this.textures = textures;
        setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer && event.getAmount() >= 1) {

            // panic necklace
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.PANIC_NECKLACE) != -1) {
                event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.SPEED, 70, 1, true, false));
            }

            boolean hasUltimatePendant = BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.ULTIMATE_PENDANT) != -1;

            // shock pendant
            if (hasUltimatePendant || BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.SHOCK_PENDANT) != -1) {
                if (event.getSource() == DamageSource.LIGHTNING_BOLT) {
                    event.setCanceled(true);
                } else if (event.getSource().getTrueSource() instanceof EntityLiving) {
                    EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
                    Random random = ((EntityPlayer) event.getEntity()).getRNG();
                    if (attacker.world.canSeeSky(attacker.getPosition()) && random.nextFloat() < 0.15F) {
                        attacker.world.addWeatherEffect(new EntityLightningBolt(attacker.world, attacker.posX, attacker.posY, attacker.posZ, false));
                    }
                }
            }

            // flame pendant
            if (hasUltimatePendant || BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.FLAME_PENDANT) != -1) {
                if (event.getSource().getTrueSource() instanceof EntityLiving) {
                    EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
                    Random random = ((EntityPlayer) event.getEntity()).getRNG();
                    if (!attacker.isImmuneToFire() && attacker.attackable() && random.nextFloat() < 0.30F) {
                        attacker.setFire(4);
                        attacker.attackEntityFrom(new EntityDamageSource("onFire", event.getEntity()).setFireDamage(), 2);
                    }
                }
            }

            // thorn pendant
            if (hasUltimatePendant || BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.THORN_PENDANT) != -1) {
                if (event.getSource().getTrueSource() instanceof EntityLiving) {
                    EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
                    Random random = ((EntityPlayer) event.getEntity()).getRNG();
                    if (attacker.attackable() && random.nextFloat() < 0.45F) {
                        attacker.attackEntityFrom(DamageSource.causeThornsDamage(event.getEntity()), 1 + random.nextInt(4));
                    }
                }
            }
        }
    }

    @Override
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, IRenderBauble.RenderType renderType, float partialTicks) {
        if (renderType == RenderType.BODY) {
            GlStateManager.enableLighting();
            GlStateManager.enableRescaleNormal();
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(textures);
            model.render(player, 0, 0, player.ticksExisted + partialTicks, 0, 0, 1 / 16F);
        }
    }

    public BaubleAmulet setModel(ModelBase model) {
        this.model = model;
        return this;
    }
}
