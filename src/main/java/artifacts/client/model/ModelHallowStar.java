package artifacts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelHallowStar extends ModelBase {

    public ModelRenderer box1;
    public ModelRenderer box2;

    public ModelHallowStar() {
        textureWidth = 32;
        textureHeight = 32;

        box1 = new ModelRenderer(this, 0, 0);
        box2 = new ModelRenderer(this, 0, 16);

        box1.addBox(-1.5F, -4, -4, 2, 8, 8);
        box2.addBox(-0.5F, -4, -4, 2, 8, 8);

        box1.rotateAngleX = (float) Math.PI / 4;
    }

    @Override
    public void render(Entity entity, float f, float f1, float ageInTicks, float f3, float f4, float scale) {
        GlStateManager.rotate(ageInTicks / 2.5F, 0.7071F, 0, 0.7071F);
        GlStateManager.rotate(ageInTicks, 0, 1, 0);
        box1.render(scale);
        box2.render(scale);
    }
}
