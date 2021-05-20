package artifacts.client.render.curio.model.hands;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;

public abstract class HandsModel extends BipedModel<LivingEntity> {

    public HandsModel(int textureWidth, int textureHeight) {
        super(0, 0, textureWidth, textureHeight);
        setAllVisible(false);

        leftArm = new ModelRenderer(this);
        rightArm = new ModelRenderer(this);
    }

    public void renderHand(HandSide handSide, MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        leftArm.visible = handSide == HandSide.LEFT;
        rightArm.visible = !leftArm.visible;
        renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
