package artifacts.client.render.curio.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;

public class LegsModel {

    private static HumanoidModel<LivingEntity> legs(float delta, int textureWidth, int textureHeight) {
        HumanoidModel<LivingEntity> model = new HumanoidModel<>(RenderType::entityCutoutNoCull, 0, 0, textureWidth, textureHeight);
        model.setAllVisible(false);

        model.leftLeg = new ModelPart(model);
        model.rightLeg = new ModelPart(model);

        // legs
        model.leftLeg.texOffs(0, 0);
        model.leftLeg.addBox(-2, 0, -2, 4, 12, 4, delta);
        model.rightLeg.texOffs(16, 0);
        model.rightLeg.addBox(-2, 0, -2, 4, 12, 4, delta);

        return model;
    }

    private static HumanoidModel<LivingEntity> sleevedLegs(float delta, int textureWidth, int textureHeight) {
        HumanoidModel<LivingEntity> model = legs(delta, textureWidth, textureHeight);

        // pants sleeves
        model.leftLeg.texOffs(0, 16);
        model.leftLeg.addBox(-2, 0, -2, 4, 12, 4, delta + 0.25F);
        model.rightLeg.texOffs(16, 16);
        model.rightLeg.addBox(-2, 0, -2, 4, 12, 4, delta + 0.25F);

        return model;
    }

    public static HumanoidModel<LivingEntity> shoes(float delta) {
        HumanoidModel<LivingEntity> model = legs(delta, 32, 32);

        // shoe tip
        model.leftLeg.texOffs(0, 16);
        model.leftLeg.addBox(-2, 12 - 3 + delta * 3 / 4, -3F - delta * 5 / 4, 4, 3, 1, delta, delta / 4, delta / 4);
        model.rightLeg.texOffs(16, 16);
        model.rightLeg.addBox(-2, 12 - 3 + delta * 3 / 4, -3F - delta * 5 / 4, 4, 3, 1, delta, delta / 4, delta / 4);

        return model;
    }

    private static HumanoidModel<LivingEntity> slippers() {
        HumanoidModel<LivingEntity> model = sleevedLegs(0.51F, 64, 32);

        // heads
        model.leftLeg.texOffs(32, 0);
        model.leftLeg.addBox(-2.5F, 8.51F, -7.01F, 5, 4, 5);
        model.rightLeg.texOffs(32, 16);
        model.rightLeg.addBox(-2.5F, 8.51F, -7, 5, 4, 5);

        return model;
    }

    public static HumanoidModel<LivingEntity> aquaDashers(float delta) {
        HumanoidModel<LivingEntity> model = shoes(delta);

        // wings
        model.leftLeg.texOffs(0, 16);
        model.leftLeg.addBox(2 + delta, 0, -2 + 3 + delta * 3 / 2, 0, 12, 4, 0, delta, delta);
        model.rightLeg.texOffs(16, 16);
        model.rightLeg.addBox(-2 - delta, 0, -2 + 3 + delta * 3 / 2, 0, 12, 4, 0, delta, delta);

        return model;
    }

    public static HumanoidModel<LivingEntity> bunnyHoppers() {
        HumanoidModel<LivingEntity> model = slippers();

        ModelPart ear1Left = new ModelPart(model, 52, 0);
        ModelPart ear1Right = new ModelPart(model, 52, 16);
        ModelPart ear2Left = new ModelPart(model, 58, 0);
        ModelPart ear2Right = new ModelPart(model, 58, 16);
        ear1Left.yRot = -0.2617994F;
        ear1Right.yRot = -0.2617994F;
        ear2Left.yRot = 0.2617994F;
        ear2Right.yRot = 0.2617994F;
        model.leftLeg.addChild(ear1Left);
        model.rightLeg.addChild(ear1Right);
        model.leftLeg.addChild(ear2Left);
        model.rightLeg.addChild(ear2Right);

        ear1Left.addBox(-3.15F, 3.51F, -3.01F, 2, 5, 1);
        ear1Right.addBox(-3.15F, 3.51F, -3, 2, 5, 1);
        ear2Left.addBox(1.15F, 3.51F, -3.01F, 2, 5, 1);
        ear2Right.addBox(1.15F, 3.51F, -3, 2, 5, 1);

        // nose
        model.leftLeg.texOffs(32, 9);
        model.leftLeg.addBox(-0.5F, 10, -7.5F, 1, 1, 1);
        model.rightLeg.texOffs(32, 25);
        model.rightLeg.addBox(-0.5F, 10, -7.5F, 1, 1, 1);

        // tail
        model.leftLeg.texOffs(52, 6);
        model.leftLeg.addBox(-1, 9, 2, 2, 2, 2);
        model.rightLeg.texOffs(52, 22);
        model.rightLeg.addBox(-1, 9, 2, 2, 2, 2);

        return model;
    }

    public static HumanoidModel<LivingEntity> flippers() {
        HumanoidModel<LivingEntity> model = legs(0.5F, 64, 64);

        // flippers
        model.leftLeg.texOffs(0, 16);
        model.leftLeg.addBox(-2, 11.5F, -16, 9, 0, 20);
        model.rightLeg.texOffs(0, 36);
        model.rightLeg.addBox(-7, 11.5F, -16, 9, 0, 20);

        return model;
    }

    public static HumanoidModel<LivingEntity> kittySlippers() {
        HumanoidModel<LivingEntity> model = slippers();

        // ears
        model.leftLeg.texOffs(32, 9);
        model.leftLeg.addBox(-2, 7.51F, -4, 1, 1, 2);
        model.rightLeg.texOffs(32, 25);
        model.rightLeg.addBox(-2, 7.51F, -4, 1, 1, 2);
        model.leftLeg.texOffs(38, 9);
        model.leftLeg.addBox(1, 7.51F, -4, 1, 1, 2);
        model.rightLeg.texOffs(38, 25);
        model.rightLeg.addBox(1, 7.51F, -4, 1, 1, 2);

        // nose
        model.leftLeg.texOffs(44, 9);
        model.leftLeg.addBox(-1.5F, 10.51F, -8, 3, 2, 1);
        model.rightLeg.texOffs(44, 25);
        model.rightLeg.addBox(-1.5F, 10.51F, -8, 3, 2, 1);

        return model;
    }

    public static HumanoidModel<LivingEntity> steadfastSpikes() {
        HumanoidModel<LivingEntity> model = sleevedLegs(0.5F, 64, 32);

        // claws
        model.leftLeg.texOffs(32, 0);
        model.leftLeg.addBox(-1.5F, 9, -7, 1, 3, 5);
        model.rightLeg.texOffs(43, 0);
        model.rightLeg.addBox(-1.5F, 9, -7, 1, 3, 5);
        model.leftLeg.texOffs(32, 8);
        model.leftLeg.addBox(0.5F, 9, -7, 1, 3, 5);
        model.rightLeg.texOffs(43, 8);
        model.rightLeg.addBox(0.5F, 9, -7, 1, 3, 5);

        return model;
    }
}
