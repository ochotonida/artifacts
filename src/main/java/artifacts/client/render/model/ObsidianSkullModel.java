package artifacts.client.render.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ObsidianSkullModel extends BipedModel<LivingEntity> {

    public final ModelRenderer belt;
    public final ModelRenderer skull;

    public ObsidianSkullModel() {
        super(0.5F, 0, 32, 32);

        bipedBody = new ModelRenderer(this, 0, 0);
        belt = new ModelRenderer(this, 0, 0);
        skull = new ModelRenderer(this, 0, 16);

        ModelRenderer skull2 = new ModelRenderer(this, 0, 28);
        ModelRenderer skull3 = new ModelRenderer(this, 24, 0);
        ModelRenderer skull4 = new ModelRenderer(this, 24, 3);
        ModelRenderer skull5 = new ModelRenderer(this, 24, 6);
        ModelRenderer skull6 = new ModelRenderer(this, 16, 28);
        ModelRenderer skull7 = new ModelRenderer(this, 24, 9);
        ModelRenderer skull8 = new ModelRenderer(this, 24, 13);
        ModelRenderer skull9 = new ModelRenderer(this, 24, 17);

        belt.addBox(-4, 0, -2, 8, 12, 4, 0.5F);

        skull.addBox(0, 0, 1, 7, 6, 6);
        skull2.addBox(0, 0, 0, 7, 2, 1);
        skull3.addBox(0, 2, 0, 1, 2, 1);
        skull4.addBox(3, 2, 0, 1, 2, 1);
        skull5.addBox(6, 2, 0, 1, 2, 1);
        skull6.addBox(0, 4, 0, 7, 2, 1);
        skull7.addBox(1, 6, 0, 1, 1, 3);
        skull8.addBox(3, 6, 0, 1, 1, 3);
        skull9.addBox(5, 6, 0, 1, 1, 3);

        skull.setRotationPoint(6, 18, -9);
        skull.rotateAngleY = -0.5F;

        skull.addChild(skull2);
        skull.addChild(skull3);
        skull.addChild(skull4);
        skull.addChild(skull5);
        skull.addChild(skull6);
        skull.addChild(skull7);
        skull.addChild(skull8);
        skull.addChild(skull9);

        bipedBody.addChild(belt);
        bipedBody.addChild(skull);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        skull.showModel = false;
        bipedBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        skull.showModel = true;
        matrixStackIn.scale(0.5F, 0.5F, 0.5F);
        belt.showModel = false;
        bipedBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        belt.showModel = true;
    }
}
