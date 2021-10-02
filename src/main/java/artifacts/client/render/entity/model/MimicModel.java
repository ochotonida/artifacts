package artifacts.client.render.entity.model;

import artifacts.Artifacts;
import artifacts.common.entity.MimicEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;

public class MimicModel extends EntityModel<MimicEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Artifacts.MODID, "mimic"), "mimic");

    protected final ModelPart bottom;
    protected final ModelPart lid;

    public MimicModel(ModelPart part) {
        bottom = part.getChild("bottom");
        lid = part.getChild("lid");
    }

    @Override
    public void setupAnim(MimicEntity mimic, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void prepareMobModel(MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks) {
        setChestRotations(mimic, partialTicks, lid, bottom);
    }

    protected static void setChestRotations(MimicEntity mimic, float partialTicks, ModelPart lid, ModelPart bottom) {
        if (mimic.ticksInAir > 0) {
            lid.xRot = Math.max(-60, (mimic.ticksInAir - 1 + partialTicks) * -6) * 0.0174533F;
            bottom.xRot = Math.min(30, (mimic.ticksInAir - 1 + partialTicks) * 3) * 0.0174533F;
        } else {
            lid.xRot = 0;
            bottom.xRot = 0;
        }
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        lid.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();

        mesh.getRoot().addOrReplaceChild(
                "bottom",
                CubeListBuilder.create()
                        .texOffs(0, 15)
                        .addBox(-6, -4, -13, 12, 3, 12)
                        .texOffs(36, 15)
                        .addBox(-6, -1, -13, 12, 0, 12, new CubeDeformation(0.02F)),
                PartPose.offset(0, 15, 7)
        );
        mesh.getRoot().addOrReplaceChild(
                "lid",
                CubeListBuilder.create()
                        .texOffs(0, 0) // teeth
                        .addBox(-6, 0, -13, 12, 3, 12)
                        .texOffs(24, 0) // overlay
                        .addBox(-6, 0, -13, 12, 0, 12, new CubeDeformation(0.02F)),
                PartPose.offset(0, 15, 7)
        );

        return LayerDefinition.create(mesh, 64, 32);
    }
}
