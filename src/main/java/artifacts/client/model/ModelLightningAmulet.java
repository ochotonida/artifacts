package artifacts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelLightningAmulet extends ModelBase {

    public ModelRenderer bipedBody;
    public ModelRenderer bipedBodyOverlay;

    public ModelLightningAmulet() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.bipedBody = new ModelRenderer(this, 0, 0);
        this.bipedBodyOverlay = new ModelRenderer(this, 0, 16);
        this.bipedBody.addBox(-4, 0, -2, 8, 12, 4, 0.4F);
        this.bipedBodyOverlay.addBox(-4, 0, -2, 8, 12, 4, 0.4F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale) {
        this.bipedBody.render(scale);

        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.color(1, 1, 1, 0.6F);
        this.bipedBodyOverlay.render(scale);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
    }
}