package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.ModItems;
import artifacts.client.model.ModelLightningAmulet;
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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class BaubleLightningAmulet extends BaubleBase implements IRenderBauble {

    public static final ModelBase MODEL = new ModelLightningAmulet();
    public static final ResourceLocation TEXTURES = new ResourceLocation(Artifacts.MODID,"textures/entity/lightning_amulet.png");

    public BaubleLightningAmulet() {
        super("bauble_lightning_amulet", BaubleType.AMULET);
    }

    public static void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer && BaubleType.AMULET.hasSlot(BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleLightningAmulet))) {
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

    @Override
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, IRenderBauble.RenderType renderType, float partialTicks) {
        if (renderType == RenderType.BODY) {
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURES);
            MODEL.render(player, 0, 0, 0, 0, 0, 1/16F);
        }
    }
}
