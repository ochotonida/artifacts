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

import javax.annotation.Nonnull;

public class LayerDrinkingHat extends LayerBauble {

    public static final ResourceLocation TEXTURES = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/drinking_hat.png");

    protected ModelRenderer hat;
    protected ModelRenderer canLeft;
    protected ModelRenderer canRight;
    protected ModelRenderer strawLeft;
    protected ModelRenderer strawRight;
    protected ModelRenderer strawMiddle;

    public LayerDrinkingHat(RenderPlayer renderPlayer) {
        super(renderPlayer);

        hat = new ModelRenderer(model, 26, 41);
        canLeft = new ModelRenderer(model, 0, 41);
        canRight = new ModelRenderer(model, 12, 41);
        strawLeft = new ModelRenderer(model, 0, 32);
        strawRight = new ModelRenderer(model, 18, 32);
        strawMiddle = new ModelRenderer(model, 0, 50);

        hat.addBox(-4, -8, -4, 8, 8, 8, 1);
        canLeft.addBox(4, -11, -1, 3, 6, 3, 0);
        canRight.addBox(-7, -11, -1, 3, 6, 3, 0);
        strawLeft.addBox(5, -4, -3, 1, 1, 8, 0);
        strawRight.addBox(-6, -4, -3, 1, 1, 8, 0);
        strawMiddle.addBox(-6, -1, -5, 12, 1, 1, 0);

        strawLeft.rotateAngleX = 0.7853F;
        strawRight.rotateAngleX = 0.7853F;

        model.bipedHead.addChild(hat);
        model.bipedHead.addChild(strawRight);
        model.bipedHead.addChild(canLeft);
        model.bipedHead.addChild(canRight);
        model.bipedHead.addChild(strawLeft);
        model.bipedHead.addChild(strawMiddle);

        model.bipedHead.showModel = true;
    }

    @Override
    protected void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (BaublesApi.isBaubleEquipped(player, ModItems.DRINKING_HAT) == -1) {
            return;
        }

        if (player.isSneaking()) {
            GlStateManager.translate(0, 0.2F, 0);
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURES);

        hat.showModel = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty();

        model.bipedHead.render(scale);
    }
}
