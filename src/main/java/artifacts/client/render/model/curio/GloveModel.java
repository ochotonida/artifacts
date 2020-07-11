package artifacts.client.render.model.curio;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.LivingEntity;

public class GloveModel extends PlayerModel<LivingEntity> {

    public GloveModel(boolean smallArms) {
        super(0.5F, smallArms);

        setVisible(false);

    }

    public void renderLeftArm(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        bipedLeftArm.showModel = true;
        bipedLeftArmwear.showModel = true;
        bipedRightArm.showModel = false;
        bipedRightArmwear.showModel = false;
        render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void renderRightArm(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        bipedRightArm.showModel = true;
        bipedRightArmwear.showModel = true;
        bipedLeftArm.showModel = false;
        bipedLeftArmwear.showModel = false;
        render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
