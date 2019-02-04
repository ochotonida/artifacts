package artifacts.client.model.layer;

import artifacts.Artifacts;
import artifacts.common.ModItems;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LayerGloves extends LayerBauble {

    public final ResourceLocation feralClawsTextures;
    public final ResourceLocation powerGloveTextures;
    public final ResourceLocation mechanicalGloveTextures;
    public final ResourceLocation fireGauntletTextures;
    public final ResourceLocation fireGauntletOverlayTextures;
    public final ResourceLocation pocketPistonTextures;

    public LayerGloves(boolean smallArms, RenderPlayer renderPlayer) {
        super(renderPlayer, new ModelPlayer(0.5F, smallArms));
        model.setVisible(false);
        feralClawsTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/feral_claws_" + (smallArms ? "slim" : "normal") + ".png");
        powerGloveTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/power_glove_" + (smallArms ? "slim" : "normal") + ".png");
        mechanicalGloveTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/mechanical_glove_" + (smallArms ? "slim" : "normal") + ".png");
        fireGauntletTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/fire_gauntlet_" + (smallArms ? "slim" : "normal") + ".png");
        fireGauntletOverlayTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/fire_gauntlet_overlay_" + (smallArms ? "slim" : "normal") + ".png");
        pocketPistonTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/pocket_piston_" + (smallArms ? "slim" : "normal") + ".png");
    }

    @Override
    protected void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        float lastLightmapX = OpenGlHelper.lastBrightnessX;
        float lastLightmapY = OpenGlHelper.lastBrightnessY;

        int light = 15728880;
        int lightmapX = light % 65536;
        int lightmapY = light / 65536;

        renderArm(EnumHandSide.LEFT, player, false, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        renderArm(EnumHandSide.RIGHT, player, false, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
        GlStateManager.disableLighting();
        renderArm(EnumHandSide.LEFT, player, true, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        renderArm(EnumHandSide.RIGHT, player, true, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.enableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastLightmapX, lastLightmapY);
    }

    private void renderArm(EnumHandSide hand, @Nonnull EntityPlayer player, boolean overlay, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!setTextures(player, hand, overlay)) {
            return;
        }

        if (hand == EnumHandSide.LEFT) {
            model.bipedLeftArm.showModel = true;
            model.bipedLeftArmwear.showModel = true;
        } else {
            model.bipedRightArm.showModel = true;
            model.bipedRightArmwear.showModel = true;
        }

        model.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        if (hand == EnumHandSide.LEFT) {
            model.bipedLeftArmwear.showModel = false;
            model.bipedLeftArm.showModel = false;
        } else {
            model.bipedRightArmwear.showModel = false;
            model.bipedRightArm.showModel = false;
        }
    }

    private boolean setTextures(EntityPlayer player, EnumHandSide hand, boolean overlay) {
        ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.RING.getValidSlots()[hand == EnumHandSide.LEFT ? 0 : 1]);
        ResourceLocation textures = overlay ? getOverlayTextures(stack) : getTextures(stack);
        if (textures != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(textures);
            return true;
        }
        return false;
    }

    private @Nullable ResourceLocation getTextures(ItemStack stack) {
        if (stack.getItem() == ModItems.POWER_GLOVE) {
            return powerGloveTextures;
        } else if (stack.getItem() == ModItems.FERAL_CLAWS) {
            return feralClawsTextures;
        } else if (stack.getItem() == ModItems.MECHANICAL_GLOVE) {
            return mechanicalGloveTextures;
        } else if (stack.getItem() == ModItems.FIRE_GAUNTLET) {
            return fireGauntletTextures;
        } else if (stack.getItem() == ModItems.POCKET_PISTON) {
            return pocketPistonTextures;
        }
        return null;
    }

    private @Nullable
    ResourceLocation getOverlayTextures(ItemStack stack) {
        if (stack.getItem() == ModItems.FIRE_GAUNTLET || stack.getItem() == ModItems.MAGMA_STONE) {
            return fireGauntletOverlayTextures;
        }
        return null;
    }
}
