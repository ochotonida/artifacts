package artifacts.common.item;

import artifacts.common.ModItems;
import artifacts.client.model.ModelAmulet;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.render.IRenderBauble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
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
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleShockPendant) != -1) {
                if (event.getSource() == DamageSource.LIGHTNING_BOLT) {
                    event.setCanceled(true);
                } else if (event.getSource().getTrueSource() instanceof EntityLiving && !((EntityPlayer) event.getEntity()).getCooldownTracker().hasCooldown(ModItems.baubleShockPendant)) {
                    EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
                    if (attacker.world.canSeeSky(attacker.getPosition())) {
                        attacker.world.addWeatherEffect(new EntityLightningBolt(attacker.world, attacker.posX, attacker.posY, attacker.posZ, false));
                        ((EntityPlayer) event.getEntity()).getCooldownTracker().setCooldown(ModItems.baubleShockPendant, 320);
                    }
                }
            }

            // fire amulet
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleFlamePendant) != -1) {
                if (event.getSource().getTrueSource() instanceof EntityLiving && !((EntityPlayer) event.getEntity()).getCooldownTracker().hasCooldown(ModItems.baubleFlamePendant)) {
                    EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
                    if (!attacker.isImmuneToFire() && attacker.attackable()) {
                        attacker.setFire(4);
                        attacker.attackEntityFrom(new EntityDamageSource("onFire", event.getEntity()).setFireDamage(), 2);
                        ((EntityPlayer) event.getEntity()).getCooldownTracker().setCooldown(ModItems.baubleFlamePendant, 160);
                    }
                }
            }

            // thorns amulet
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleThornPendant) != -1) {
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
