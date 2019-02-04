package artifacts.client.model;

import artifacts.Artifacts;
import artifacts.common.ModItems;
import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public class LayerSnorkel extends LayerBauble {

    public static final ResourceLocation TEXTURES = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/snorkel.png");
    protected ModelRenderer snorkelMouthPiece;
    protected ModelRenderer snorkelTubeThing;
    protected ModelRenderer snorkelGoggles;
    protected ModelRenderer snorkelGogglesOverlay;

    public LayerSnorkel(RenderPlayer renderPlayer) {
        super(renderPlayer);
        snorkelMouthPiece = new ModelRenderer(model, 0, 46);
        snorkelGoggles = new ModelRenderer(model, 28, 32);
        snorkelGogglesOverlay = new ModelRenderer(model, 28, 48);
        snorkelTubeThing = new ModelRenderer(model, 0, 32);

        snorkelMouthPiece.addBox(-2, -1.5F, -6, 8, 2, 2, 0);
        snorkelGoggles.addBox(-4, -7, -4, 8, 8, 8, 1);
        snorkelGogglesOverlay.addBox(-4, -7, -4, 8, 8, 8, 1);
        snorkelTubeThing.addBox(4, -5, -3, 2, 2, 12, 0);
        snorkelTubeThing.rotateAngleX = 0.7853F;

        model.bipedHead.addChild(snorkelMouthPiece);
        model.bipedHead.addChild(snorkelTubeThing);
        model.bipedHead.addChild(snorkelGoggles);
        model.bipedHead.addChild(snorkelGogglesOverlay);

        model.bipedHead.showModel = true;

        snorkelMouthPiece.showModel = false;
        snorkelTubeThing.showModel = false;
        snorkelGoggles.showModel = false;
        snorkelGogglesOverlay.showModel = false;
    }

    @Override
    protected void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (BaublesApi.isBaubleEquipped(player, ModItems.SNORKEL) == -1) {
            return;
        }

        if (player.isSneaking()) {
            GlStateManager.translate(0, 0.2F, 0);
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURES);

        snorkelMouthPiece.showModel = true;
        snorkelTubeThing.showModel = true;

        model.bipedHead.render(scale);

        snorkelMouthPiece.showModel = false;
        snorkelTubeThing.showModel = false;

        if (player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty()) {
            snorkelGoggles.showModel = true;
            model.bipedHead.render(scale);
            snorkelGoggles.showModel = false;

            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(1, 1, 1, 0.3F);

            snorkelGogglesOverlay.showModel = true;
            model.bipedHead.render(scale);
            snorkelGogglesOverlay.showModel = false;

            GlStateManager.disableBlend();
        }
    }
}
