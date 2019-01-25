package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.ModItems;
import artifacts.client.model.ModelObsidianSkull;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.render.IRenderBauble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BaubleObsidianSkull extends BaubleBase implements IRenderBauble {

    protected ModelBase model = new ModelObsidianSkull();

    protected ResourceLocation textures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/obsidian_skull.png");

    public BaubleObsidianSkull() {
        super("obsidian_skull", BaubleType.BELT);
    }

    @Override
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType renderType, float partialticks) {
        if (renderType == RenderType.BODY) {
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(textures);
            model.render(player, 0, 0, 0, 0, 0, 1/16F);
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.OBSIDIAN_SKULL) != -1 && !((EntityPlayer) event.getEntity()).getCooldownTracker().hasCooldown(ModItems.OBSIDIAN_SKULL)) {
                if (event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.LAVA) {
                    event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE,400, 0,true,true));
                    ((EntityPlayer) event.getEntity()).getCooldownTracker().setCooldown(ModItems.OBSIDIAN_SKULL, 1600);
                }
            }
        }
    }
}
