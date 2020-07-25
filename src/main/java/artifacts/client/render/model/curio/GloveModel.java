package artifacts.client.render.model.curio;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;

public class GloveModel extends PlayerModel<LivingEntity> {

    public GloveModel(boolean smallArms) {
        super(0.5F, smallArms);

        setVisible(false);
    }

    public void renderHand(boolean mainHand, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (Minecraft.getInstance().gameSettings.mainHand == HandSide.LEFT) {
            mainHand = !mainHand;
        }
        bipedRightArmwear.copyModelAngles(bipedRightArm);
        bipedLeftArmwear.copyModelAngles(bipedLeftArm);
        bipedLeftArm.showModel = !mainHand;
        bipedLeftArmwear.showModel = !mainHand;
        bipedRightArm.showModel = mainHand;
        bipedRightArmwear.showModel = mainHand;
        render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
