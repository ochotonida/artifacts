package artifacts.client.render.model.curio;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class AntidoteVesselModel extends BipedModel<LivingEntity> {

    public final ModelRenderer belt;
    public final ModelRenderer jar;

    public AntidoteVesselModel() {
        super(0.5F, 0, 64, 32);

        setVisible(false);

        bipedBody = new ModelRenderer(this, 0, 0);
        belt = new ModelRenderer(this, 0, 0);
        jar = new ModelRenderer(this, 0, 16);

        ModelRenderer lid = new ModelRenderer(this, 24, 0);

        belt.addBox(-4, 0, -2, 8, 12, 4, 0.5F);

        jar.addBox(0, 0, 0, 6, 8, 6);
        lid.addBox(1, -1, 1, 4, 1, 4);

        jar.setRotationPoint(6, 18, -10);
        jar.rotateAngleY = -0.5F;

        jar.addChild(lid);
        bipedBody.addChild(belt);
        bipedBody.addChild(jar);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        jar.showModel = false;
        bipedBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        jar.showModel = true;
        matrixStackIn.scale(0.5F, 0.5F, 0.5F);
        belt.showModel = false;
        bipedBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        belt.showModel = true;
    }
}
