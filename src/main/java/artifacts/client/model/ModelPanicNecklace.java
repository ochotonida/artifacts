package artifacts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelPanicNecklace extends ModelBase {

    public ModelRenderer bipedBody;
    public ModelRenderer gem2;
    public ModelRenderer gem1;
    public ModelRenderer gem3;
    public ModelRenderer gem4;

    public ModelPanicNecklace() {
        textureWidth = 64;
        textureHeight = 32;
        bipedBody = new ModelRenderer(this, 0, 0);
        bipedBody.addBox(-8.5F, 0, -4, 17, 24, 8, 0);
        gem1 = new ModelRenderer(this, 50, 0);
        gem2 = new ModelRenderer(this, 56, 0);
        gem3 = new ModelRenderer(this, 50, 3);
        gem4 = new ModelRenderer(this, 58, 4);
        gem1.addBox(0, 0, 0, 2, 2, 1, 0);
        gem2.addBox(3, 0, 0, 2, 2, 1, 0);
        gem3.addBox(1, 1, 0, 3, 2, 1, 0);
        gem4.addBox(2, 3, 0, 1, 1, 1, 0);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, -0.02F, 0);
        GlStateManager.scale(7/6F, 7/6F, 7/6F);
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        bipedBody.render(f5);
        GlStateManager.translate(-1.25/8F, 3/8F, -2.25/8F);
        gem4.render(f5);
        gem3.render(f5);
        gem2.render(f5);
        gem1.render(f5);
        GlStateManager.popMatrix();
    }
}
