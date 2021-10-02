package artifacts.client.render.curio.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

public class BeltModel extends HumanoidModel<LivingEntity> {

    protected final ModelPart charm = new ModelPart(this);

    private final float xOffset;
    private final float zOffset;
    private final float rotation;

    protected BeltModel(Function<ResourceLocation, RenderType> renderType, float xOffset, float zOffset, float rotation) {
        super(renderType, 0.5F, 0, 32, 32);
        this.xOffset = xOffset;
        this.zOffset = zOffset;
        this.rotation = rotation;

        setAllVisible(false);

        body = new ModelPart(this);

        // belt
        body.texOffs(0, 0);
        body.addBox(-4, 0, -2, 8, 12, 4, 0.5F);

        // charm
        body.addChild(charm);
    }

    public void setCharmPosition(int slot) {
        float xOffset = slot % 2 == 0 ? this.xOffset : -this.xOffset;
        float zOffset = slot % 4 < 2 ? this.zOffset : -this.zOffset;
        charm.setPos(xOffset, 9, zOffset);

        float rotation = slot % 4 < 2 ? 0 : (float) -Math.PI;
        rotation += slot % 2 == 0 ^ slot % 4 >= 2 ? this.rotation : -this.rotation;
        charm.yRot = rotation;
    }

    private static BeltModel belt(float xOffset, float zOffset, float rotation) {
        return belt(RenderType::entityCutoutNoCull, xOffset, zOffset, rotation);
    }

    private static BeltModel belt(Function<ResourceLocation, RenderType> renderType, float xOffset, float zOffset, float rotation) {
        return new BeltModel(renderType, xOffset, zOffset, rotation);
    }

    public static BeltModel antidoteVessel() {
        BeltModel model = belt(4, -3, -0.5F);

        // jar
        model.charm.texOffs(0, 16);
        model.charm.addBox(-2, 0, -2, 4, 6, 4);

        // lid
        model.charm.texOffs(0, 26);
        model.charm.addBox(-1, -1, -1, 2, 1, 2);

        return model;
    }

    public static BeltModel cloudInABottle() {
        return new BeltModel(RenderType::entityTranslucent, 3, -3, -0.5F) {
            private final ModelPart cloud;

            {
                // jar
                charm.texOffs(0, 16);
                charm.addBox(-2, 0, -2, 4, 5, 4);

                // lid
                charm.texOffs(0, 25);
                charm.addBox(-1, -1, -1, 2, 1, 2);

                // cloud
                cloud = new ModelPart(this).texOffs(8, 25);
                cloud.addBox(-1, 1.5F, -1, 2, 2, 2);
                charm.addChild(cloud);
            }

            @Override
            public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
                super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                cloud.yRot = (ageInTicks) / 50;
                cloud.y = Mth.cos((ageInTicks) / 30) / 2;
            }
        };
    }

    public static BeltModel crystalHeart() {
        BeltModel model = belt(RenderType::entityTranslucent, 2.5F, -3.01F, 0);

        // heart parts
        model.charm.texOffs(0, 16);
        model.charm.addBox(-2.5F, 0, 0, 2, 3, 1);
        model.charm.texOffs(6, 16);
        model.charm.addBox(0.5F, 0, 0, 2, 3, 1);
        model.charm.texOffs(0, 20);
        model.charm.addBox(-0.5F, 1, 0, 1, 4, 1);
        model.charm.texOffs(4, 20);
        model.charm.addBox(-1.5F, 3, 0, 1, 1, 1);
        model.charm.texOffs(8, 20);
        model.charm.addBox(0.5F, 3, 0, 1, 1, 1);

        return model;
    }

    public static HumanoidModel<LivingEntity> heliumFlamingo() {
        HumanoidModel<LivingEntity> model = new HumanoidModel<>(RenderType::entityCutoutNoCull, 0, 0, 64, 64);
        model.setAllVisible(false);

        model.body = new ModelPart(model);
        model.body.texOffs(16, 36).addBox(-1, 1, -14, 2, 3, 5);
        model.body.texOffs(0, 18).addBox(4, 9, -7, 4, 4, 14);
        model.body.texOffs(0, 0).addBox(-8, 9, -7, 4, 4, 14);
        model.body.texOffs(36, 0).addBox(-4, 9, 3, 8, 4, 4);
        model.body.texOffs(36, 8).addBox(-4, 9, -7, 8, 4, 4);
        model.body.texOffs(0, 36).addBox(-2, 1, -9, 4, 11, 4);

        return model;
    }

    public static BeltModel obsidianSkull() {
        BeltModel model = belt(4.5F, -4F, -0.5F);

        // skull
        model.charm.texOffs(0, 16);
        model.charm.addBox(-2.5F, 0, 0, 5, 3, 4);

        // teeth
        model.charm.texOffs(18, 16);
        model.charm.addBox(-1.5F, 3, 0, 1, 1, 2);
        model.charm.texOffs(18, 19);
        model.charm.addBox(0.5F, 3, 0, 1, 1, 2);

        return model;
    }

    public static BeltModel universalAttractor() {
        BeltModel model = belt(2.5F, -3, 0);

        // magnet
        model.charm.texOffs(0, 16);
        model.charm.addBox(-2.5F, 0, 0, 5, 2, 1);
        model.charm.texOffs(0, 19);
        model.charm.addBox(-2.5F, 2, 0, 2, 4, 1);
        model.charm.texOffs(6, 19);
        model.charm.addBox(0.5F, 2, 0, 2, 4, 1);

        return model;
    }

}
