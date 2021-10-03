package artifacts.client.render.curio.model;

import artifacts.client.render.curio.CurioLayers;
import artifacts.client.render.curio.CurioRenderers;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

public class ArmsModel extends HumanoidModel<LivingEntity> {

    public ArmsModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
        super(part, renderType);
    }

    public ArmsModel(ModelPart part) {
        this(part, RenderType::entityCutoutNoCull);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(leftArm, rightArm);
    }

    public void renderArm(HumanoidArm handSide, PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        getArm(handSide).visible = true;
        getArm(handSide.getOpposite()).visible = false;
        renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public static ArmsModel createClawsModel(boolean smallArms) {
        return new ArmsModel(CurioRenderers.bakeLayer(CurioLayers.claws(smallArms)));
    }

    public static ArmsModel createGloveModel(boolean smallArms) {
        return new ArmsModel(CurioRenderers.bakeLayer(CurioLayers.glove(smallArms)));
    }

    public static ArmsModel createGoldenHookModel(boolean smallArms) {
        return new ArmsModel(CurioRenderers.bakeLayer(CurioLayers.goldenHook(smallArms)));
    }

    public static MeshDefinition createEmptyArms(CubeListBuilder leftArm, CubeListBuilder rightArm, boolean smallArms) {
        MeshDefinition mesh = createMesh(CubeDeformation.NONE, 0);

        mesh.getRoot().addOrReplaceChild(
                "left_arm",
                leftArm,
                PartPose.offset(5, smallArms ? 2.5F : 2, 0)
        );
        mesh.getRoot().addOrReplaceChild(
                "right_arm",
                rightArm,
                PartPose.offset(-5, smallArms ? 2.5F : 2, 0)
        );

        return mesh;
    }

    public static MeshDefinition createArms(CubeListBuilder leftArm, CubeListBuilder rightArm, boolean smallArms) {
        leftArm.texOffs(0, 0);
        rightArm.texOffs(16, 0);
        addArms(leftArm, rightArm, new CubeDeformation(0.5F), smallArms);

        return createEmptyArms(leftArm, rightArm, smallArms);
    }

    public static MeshDefinition createSleevedArms(CubeListBuilder leftArm, CubeListBuilder rightArm, boolean smallArms) {
        leftArm.texOffs(0, 16);
        rightArm.texOffs(16, 16);
        addArms(leftArm, rightArm, new CubeDeformation(0.75F), smallArms);

        return createArms(leftArm, rightArm, smallArms);
    }

    public static MeshDefinition createSleevedArms(boolean smallArms) {
        return createSleevedArms(CubeListBuilder.create(), CubeListBuilder.create(), smallArms);
    }

    private static void addArms(CubeListBuilder leftArm, CubeListBuilder rightArm, CubeDeformation deformation, boolean smallArms) {
        leftArm.addBox(-1, -2, -2, smallArms ? 3 : 4, 12, 4, deformation);
        rightArm.addBox(smallArms ? -2 : -3, -2, -2, smallArms ? 3 : 4, 12, 4, deformation);
    }

    public static MeshDefinition createClaws(boolean smallArms) {
        CubeListBuilder leftArm = CubeListBuilder.create();
        CubeListBuilder rightArm = CubeListBuilder.create();

        int smallArmsOffset = smallArms ? 1 : 0;

        // claw 1 lower
        leftArm.texOffs(0, 0);
        leftArm.addBox(-smallArmsOffset, 10, -1.5F, 3, 5, 1);
        rightArm.texOffs(8, 0);
        rightArm.addBox(-3 + smallArmsOffset, 10, -1.5F, 3, 5, 1);

        // claw 2 lower
        leftArm.texOffs(0, 6);
        leftArm.addBox(-smallArmsOffset, 10, 0.5F, 3, 5, 1);
        rightArm.texOffs(8, 6);
        rightArm.addBox(-3 + smallArmsOffset, 10, 0.5F, 3, 5, 1);

        // claw 1 upper
        leftArm.texOffs(16, 0);
        leftArm.addBox(3 - smallArmsOffset, 10, -1.5F, 1, 4, 1);
        rightArm.texOffs(20, 0);
        rightArm.addBox(-4 + smallArmsOffset, 10, -1.5F, 1, 4, 1);

        // claw 2 upper
        leftArm.texOffs(16, 6);
        leftArm.addBox(3 - smallArmsOffset, 10, 0.5F, 1, 4, 1);
        rightArm.texOffs(20, 6);
        rightArm.addBox(-4 + smallArmsOffset, 10, 0.5F, 1, 4, 1);

        return createEmptyArms(leftArm, rightArm, smallArms);
    }

    public static MeshDefinition createGoldenHook(boolean smallArms) {
        CubeListBuilder leftArm = CubeListBuilder.create();
        CubeListBuilder rightArm = CubeListBuilder.create();

        // hook
        leftArm.texOffs(32, 0);
        leftArm.addBox(smallArms ? -2 : -1.5F, 12, -0.5F, 5, 5, 1);
        rightArm.texOffs(48, 0);
        rightArm.addBox(smallArms ? -3 : -3.5F, 12, -0.5F, 5, 5, 1);

        // hook base
        leftArm.texOffs(32, 6);
        leftArm.addBox(smallArms ? 0 : 0.5F, 10, -0.5F, 1, 2, 1);
        rightArm.texOffs(48, 6);
        rightArm.addBox(smallArms ? -1 : -1.5F, 10, -0.5F, 1, 2, 1);

        return createSleevedArms(leftArm, rightArm, smallArms);
    }
}
