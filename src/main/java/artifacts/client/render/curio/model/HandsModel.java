package artifacts.client.render.curio.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class HandsModel extends HumanoidModel<LivingEntity> {

    protected HandsModel(int textureWidth, int textureHeight) {
        super(0, 0, textureWidth, textureHeight);
        setAllVisible(false);

        leftArm = new ModelPart(this);
        rightArm = new ModelPart(this);
    }

    public void renderHand(HumanoidArm handSide, PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        leftArm.visible = handSide == HumanoidArm.LEFT;
        rightArm.visible = !leftArm.visible;
        renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    private static HandsModel hands(int textureWidth, int textureHeight) {
        return new HandsModel(textureWidth, textureHeight);
    }

    public static HandsModel claws(boolean smallArms) {
        HandsModel model = hands(32, 16);

        int smallArmsOffset = smallArms ? 1 : 0;

        // claw 1 lower
        model.leftArm.texOffs(0, 0);
        model.leftArm.addBox(-smallArmsOffset, 10, -1.5F, 3, 5, 1);
        model.rightArm.texOffs(8, 0);
        model.rightArm.addBox(-3 + smallArmsOffset, 10, -1.5F, 3, 5, 1);

        // claw 2 lower
        model.leftArm.texOffs(0, 6);
        model.leftArm.addBox(-smallArmsOffset, 10, 0.5F, 3, 5, 1);
        model.rightArm.texOffs(8, 6);
        model.rightArm.addBox(-3 + smallArmsOffset, 10, 0.5F, 3, 5, 1);

        // claw 1 upper
        model.leftArm.texOffs(16, 0);
        model.leftArm.addBox(3 - smallArmsOffset, 10, -1.5F, 1, 4, 1);
        model.rightArm.texOffs(20, 0);
        model.rightArm.addBox(-4 + smallArmsOffset, 10, -1.5F, 1, 4, 1);

        // claw 2 upper
        model.leftArm.texOffs(16, 6);
        model.leftArm.addBox(3 - smallArmsOffset, 10, 0.5F, 1, 4, 1);
        model.rightArm.texOffs(20, 6);
        model.rightArm.addBox(-4 + smallArmsOffset, 10, 0.5F, 1, 4, 1);

        return model;
    }

    public static HandsModel glove(boolean smallArms) {
        return glove(smallArms, 32, 32);
    }

    public static HandsModel glove(boolean smallArms, int textureWidth, int textureHeight) {
        HandsModel model = hands(textureWidth, textureHeight);

        model.leftArm.setPos(5, smallArms ? 2.5F : 2, 0);
        model.rightArm.setPos(-5, smallArms ? 2.5F : 2, 0);

        // arms
        model.leftArm.texOffs(0, 0);
        model.leftArm.addBox(-1, -2, -2, smallArms ? 3 : 4, 12, 4, 0.5F);
        model.rightArm.texOffs(16, 0);
        model.rightArm.addBox(smallArms ? -2 : -3, -2, -2, smallArms ? 3 : 4, 12, 4, 0.5F);

        // sleeves
        model.leftArm.texOffs(0, 16);
        model.leftArm.addBox(-1, -2, -2, smallArms ? 3 : 4, 12, 4, 0.5F + 0.25F);
        model.rightArm.texOffs(16, 16);
        model.rightArm.addBox(smallArms ? -2 : -3, -2, -2, smallArms ? 3 : 4, 12, 4, 0.5F + 0.25F);

        return model;
    }

    public static HandsModel goldenHook(boolean smallArms) {
        HandsModel model = glove(smallArms, 64, 32);

        // hook
        model.leftArm.texOffs(32, 0);
        model.leftArm.addBox(smallArms ? -2 : -1.5F, 12, -0.5F, 5, 5, 1);
        model.rightArm.texOffs(48, 0);
        model.rightArm.addBox(smallArms ? -3 : -3.5F, 12, -0.5F, 5, 5, 1);

        // hook base
        model.leftArm.texOffs(32, 6);
        model.leftArm.addBox(smallArms ? 0 : 0.5F, 10, -0.5F, 1, 2, 1);
        model.rightArm.texOffs(48, 6);
        model.rightArm.addBox(smallArms ? -1 : -1.5F, 10, -0.5F, 1, 2, 1);

        return model;
    }
}
