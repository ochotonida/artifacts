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
import net.minecraft.item.ItemStack;
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
    }

    @Override
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, IRenderBauble.RenderType renderType, float partialTicks) {
        if (renderType == RenderType.BODY) {
            GlStateManager.enableLighting();
            GlStateManager.enableRescaleNormal();
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(textures);
            model.render(player, 0, 0, 0, 0, 0, 1/16F);
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer) {

            // lightning amulet
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.SHOCK_PENDANT) != -1) {
                if (event.getSource() == DamageSource.LIGHTNING_BOLT) {
                    event.setCanceled(true);
                } else if (event.getSource().getTrueSource() instanceof EntityLiving && !((EntityPlayer) event.getEntity()).getCooldownTracker().hasCooldown(ModItems.SHOCK_PENDANT)) {
                    EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
                    if (attacker.world.canSeeSky(attacker.getPosition())) {
                        attacker.world.addWeatherEffect(new EntityLightningBolt(attacker.world, attacker.posX, attacker.posY, attacker.posZ, false));
                        ((EntityPlayer) event.getEntity()).getCooldownTracker().setCooldown(ModItems.SHOCK_PENDANT, 320);
                    }
                }
            }

            // fire amulet
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.FLAME_PENDANT) != -1) {
                if (event.getSource().getTrueSource() instanceof EntityLiving && !((EntityPlayer) event.getEntity()).getCooldownTracker().hasCooldown(ModItems.FLAME_PENDANT)) {
                    EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
                    if (!attacker.isImmuneToFire() && attacker.attackable()) {
                        attacker.setFire(4);
                        attacker.attackEntityFrom(new EntityDamageSource("onFire", event.getEntity()).setFireDamage(), 2);
                        ((EntityPlayer) event.getEntity()).getCooldownTracker().setCooldown(ModItems.FLAME_PENDANT, 160);
                    }
                }
            }

            // thorns amulet
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.THORN_PENDANT) != -1) {
                if (event.getSource().getTrueSource() instanceof EntityLiving) {
                    EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
                    Random random = ((EntityPlayer) event.getEntity()).getRNG();
                    if (attacker.attackable() && random.nextFloat() < 0.45) {
                        attacker.attackEntityFrom(DamageSource.causeThornsDamage(event.getEntity()), 1 + random.nextInt(4));
                    }
                }
            }
        }
    }
}
