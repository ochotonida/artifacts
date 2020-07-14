package artifacts.client.render.model.curio;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class MagnetModel extends BipedModel<LivingEntity> {

    public final ModelRenderer belt;
    public final ModelRenderer magnet;

    public MagnetModel() {
        super(0.5F, 0, 64, 32);

        setVisible(false);

        bipedBody = new ModelRenderer(this, 0, 0);
        belt = new ModelRenderer(this, 0, 0);
        magnet = new ModelRenderer(this, 24, 6);

        ModelRenderer magnet1 = new ModelRenderer(this, 24, 0);
        ModelRenderer magnet2 = new ModelRenderer(this, 32, 0);

        belt.addBox(-4, 0, -2, 8, 12, 4, 0.5F);
        magnet.addBox(0, 0, 0, 6, 2, 2);
        magnet1.addBox(0, 2, 0, 2, 4, 2);
        magnet2.addBox(4, 2, 0, 2, 4, 2);

        magnet.setRotationPoint(2, 18, -7);
        //magnet.rotateAngleY = -0.5F;

        magnet.addChild(magnet1);
        magnet.addChild(magnet2);
        bipedBody.addChild(belt);
        bipedBody.addChild(magnet);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        magnet.showModel = false;
        bipedBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        magnet.showModel = true;
        matrixStackIn.scale(0.5F, 0.5F, 0.5F);
        belt.showModel = false;
        bipedBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        belt.showModel = true;
    }
}
