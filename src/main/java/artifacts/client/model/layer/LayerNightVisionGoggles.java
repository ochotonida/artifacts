package artifacts.client.model.layer;

import artifacts.Artifacts;
import artifacts.common.init.ModItems;
import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class LayerNightVisionGoggles extends LayerBauble {

    public static final ResourceLocation TEXTURES = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/night_vision_goggles.png");
    protected final ModelRenderer headBand;
    protected final ModelRenderer goggleBase;
    protected final ModelRenderer eyeLeft;
    protected final ModelRenderer eyeRight;
    protected final ModelRenderer eyeLeftOverlay;
    protected final ModelRenderer eyeRightOverlay;

    public LayerNightVisionGoggles(RenderPlayer renderPlayer) {
        super(renderPlayer);
        headBand = new ModelRenderer(model, 0, 32);
        goggleBase = new ModelRenderer(model, 0, 53);
        eyeLeft = new ModelRenderer(model, 0, 48);
        eyeRight = new ModelRenderer(model, 10, 48);
        eyeLeftOverlay = new ModelRenderer(model, 20, 48);
        eyeRightOverlay = new ModelRenderer(model, 30, 48);
        headBand.addBox(-4, -8, -4, 8, 8, 8, 0.9F);
        goggleBase.addBox(-4, -6, -5 + 0.05F, 8, 4, 1);
        eyeLeft.addBox(1 + 0.5F, -5, -8 + 0.05F, 2, 2, 3);
        eyeRight.addBox(-3 - 0.5F, -5, -8 + 0.05F, 2, 2, 3);
        eyeLeftOverlay.addBox(1 + 0.5F, -5, -8 + 0.05F, 2, 2, 3);
        eyeRightOverlay.addBox(-3 - 0.5F, -5, -8 + 0.05F, 2, 2, 3);

        model.bipedHead.addChild(headBand);
        model.bipedHead.addChild(goggleBase);
        model.bipedHead.addChild(eyeLeft);
        model.bipedHead.addChild(eyeRight);
        model.bipedHead.addChild(eyeLeftOverlay);
        model.bipedHead.addChild(eyeRightOverlay);

        model.bipedHead.showModel = true;

        headBand.showModel = false;
        goggleBase.showModel = false;
        eyeLeft.showModel = false;
        eyeRight.showModel = false;
        eyeLeftOverlay.showModel = false;
        eyeRightOverlay.showModel = false;
    }

    @Override
    protected void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (BaublesApi.isBaubleEquipped(player, ModItems.NIGHT_VISION_GOGGLES) == -1) {
            return;
        }

        if (player.isSneaking()) {
            GlStateManager.translate(0, 0.2F, 0);
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURES);

        headBand.showModel = true;
        goggleBase.showModel = true;
        eyeLeft.showModel = true;
        eyeRight.showModel = true;

        model.bipedHead.render(scale);

        goggleBase.showModel = false;
        eyeLeft.showModel = false;
        eyeRight.showModel = false;
        headBand.showModel = false;


        float lastLightmapX = OpenGlHelper.lastBrightnessX;
        float lastLightmapY = OpenGlHelper.lastBrightnessY;

        int light = 15728880;
        int lightmapX = light % 65536;
        int lightmapY = light / 65536;

        eyeLeftOverlay.showModel = true;
        eyeRightOverlay.showModel = true;

        GlStateManager.disableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
        model.bipedHead.render(scale);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastLightmapX, lastLightmapY);
        GlStateManager.enableLighting();

        eyeLeftOverlay.showModel = false;
        eyeRightOverlay.showModel = false;
    }
}
