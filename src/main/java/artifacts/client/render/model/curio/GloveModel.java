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

        setAllVisible(false);
    }

    public void renderHand(boolean mainHand, MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        boolean isRightHand = (Minecraft.getInstance().options.mainHand == HandSide.LEFT) != mainHand;
        rightSleeve.copyFrom(rightArm);
        leftSleeve.copyFrom(leftArm);
        leftArm.visible = !isRightHand;
        leftSleeve.visible = !isRightHand;
        rightArm.visible = isRightHand;
        rightSleeve.visible = isRightHand;
        renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
