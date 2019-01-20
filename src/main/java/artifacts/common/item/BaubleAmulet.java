package artifacts.common.item;

import artifacts.common.ModItems;
import artifacts.client.model.ModelAmulet;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.render.IRenderBauble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
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

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.translateToLocal("tooltip." + name + ".name"));
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer) {

            // lightning amulet
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleLightningAmulet) != -1) {
                if (event.getSource() == DamageSource.LIGHTNING_BOLT) {
                    event.setCanceled(true);
                } else if (event.getSource().getTrueSource() instanceof EntityLiving && !((EntityPlayer) event.getEntity()).getCooldownTracker().hasCooldown(ModItems.baubleLightningAmulet)) {
                    EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
                    if (attacker.world.canSeeSky(attacker.getPosition())) {
                        attacker.world.addWeatherEffect(new EntityLightningBolt(attacker.world, attacker.posX, attacker.posY, attacker.posZ, false));
                        ((EntityPlayer) event.getEntity()).getCooldownTracker().setCooldown(ModItems.baubleLightningAmulet, 320);
                    }
                }
            }

            // fire amulet
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleFireAmulet) != -1) {
                if (event.getSource().getTrueSource() instanceof EntityLiving && !((EntityPlayer) event.getEntity()).getCooldownTracker().hasCooldown(ModItems.baubleFireAmulet)) {
                    EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
                    if (!attacker.isImmuneToFire() && attacker.attackable()) {
                        attacker.setFire(4);
                        attacker.attackEntityFrom(new EntityDamageSource("onFire", event.getEntity()).setFireDamage(), 2);
                        ((EntityPlayer) event.getEntity()).getCooldownTracker().setCooldown(ModItems.baubleFireAmulet, 160);
                    }
                }
            }

            // thorns amulet
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleThornsAmulet) != -1) {
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
