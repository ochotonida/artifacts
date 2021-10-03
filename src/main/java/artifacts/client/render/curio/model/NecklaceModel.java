package artifacts.client.render.curio.model;

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
import net.minecraft.world.entity.LivingEntity;

public class NecklaceModel extends HumanoidModel<LivingEntity> {

    public NecklaceModel(ModelPart part) {
        super(part, RenderType::entityTranslucent);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int light, int overlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.scale(0.5F, 0.5F, 0.5F);
        body.render(poseStack, buffer, light, overlay, red, green, blue, alpha);
        poseStack.popPose();
    }

    public static MeshDefinition createNecklace(CubeListBuilder body) {
        MeshDefinition mesh = createMesh(CubeDeformation.NONE, 0);

        mesh.getRoot().addOrReplaceChild(
                "body",
                body.texOffs(0, 0)
                        .addBox(-(2 * 8) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8, 2 * 12 + 1, 2 * 4 + 1),
                PartPose.ZERO
        );

        return mesh;
    }

    public static MeshDefinition createCenteredNecklace(CubeListBuilder body) {
        MeshDefinition mesh = createMesh(CubeDeformation.NONE, 0);

        mesh.getRoot().addOrReplaceChild(
                "body",
                body.texOffs(0, 0)
                        .addBox(-(2 * 8 + 1) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8 + 1, 2 * 12 + 1, 2 * 4 + 1),
                PartPose.ZERO
        );

        return mesh;
    }

    public static MeshDefinition createCharmOfSinking() {
        CubeListBuilder body = CubeListBuilder.create();

        body.texOffs(50, 0);
        body.addBox(-1, 3.5F, -5, 2, 4, 1);

        return createNecklace(body);
    }

    public static MeshDefinition createCrossNecklace() {
        CubeListBuilder body = CubeListBuilder.create();

        // cross vertical
        body.texOffs(52, 0);
        body.addBox(-0.5F, 4.5F, -5, 1, 4, 1);

        // cross horizontal
        body.texOffs(56, 0);
        body.addBox(-1.5F, 5.5F, -5, 3, 1, 1);

        return createCenteredNecklace(body);
    }

    public static MeshDefinition createPanicNecklace() {
        CubeListBuilder body = CubeListBuilder.create();

        // gem top
        body.texOffs(52, 0);
        body.addBox(-2.5F, 5.5F, -5, 2, 2, 1);
        body.texOffs(58, 0);
        body.addBox(0.5F, 5.5F, -5, 2, 2, 1);

        // gem middle
        body.texOffs(52, 3);
        body.addBox(-1.5F, 6.5F, -5, 3, 2, 1);

        // gem bottom
        body.texOffs(60, 4);
        body.addBox(-0.5F, 8.5F, -5, 1, 1, 1);

        return createCenteredNecklace(body);
    }

    public static MeshDefinition createPendant() {
        CubeListBuilder body = CubeListBuilder.create();

        // gem
        body.texOffs(50, 0);
        body.addBox(-1, 4.5F, -5, 2, 2, 1);

        return createNecklace(body);
    }
}
