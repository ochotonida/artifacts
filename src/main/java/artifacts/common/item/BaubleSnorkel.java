package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.model.ModelSnorkel;
import baubles.api.BaubleType;
import baubles.api.render.IRenderBauble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BaubleSnorkel extends BaublePotionEffect implements IRenderBauble {

    public static final ModelBase MODEL = new ModelSnorkel();
    public static final ResourceLocation TEXTURES = new ResourceLocation(Artifacts.MODID,"textures/entity/snorkel.png");

    public BaubleSnorkel() {
        super("bauble_snorkel", BaubleType.HEAD, MobEffects.WATER_BREATHING, 0);
        setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1);
    }

    @Override
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType renderType, float partialTicks) {
        if (renderType == RenderType.HEAD) {
            GlStateManager.pushMatrix();

            Helper.translateToHeadLevel(player);
            Helper.translateToFace();
            Helper.defaultTransforms();

            GlStateManager.rotate(180, 0, 0, 1);
            GlStateManager.scale(1/0.55F, 1/0.55F, 1/0.55F);
            GlStateManager.scale(1/16F, 1/16F, 1/16F);
            GlStateManager.translate(0, 0, 4.25);

            Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURES);
            MODEL.render(player, 0, 0, 0, 0, 0, 1);

            GlStateManager.popMatrix();
        }
    }
}
