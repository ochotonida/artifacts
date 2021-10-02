package artifacts.client.render.entity.model;

import artifacts.Artifacts;
import artifacts.common.entity.MimicEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;

public class MimicChestLayerModel extends EntityModel<MimicEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Artifacts.MODID, "mimic_overlay"), "mimic_overlay");

    protected final ModelPart bottom;
    protected final ModelPart lid;

    public MimicChestLayerModel(ModelPart part) {
        bottom = part.getChild("bottom");
        lid = part.getChild("lid");
    }

    @Override
    public void setupAnim(MimicEntity mimic, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void prepareMobModel(MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks) {
        MimicModel.setChestRotations(mimic, partialTicks, lid, bottom);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bottom.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        lid.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();

        mesh.getRoot().addOrReplaceChild(
                "bottom",
                CubeListBuilder.create()
                        .texOffs(0, 19)
                        .addBox(1, -9, 0, 14, 10, 14),
                PartPose.offset(0, 9, 1)
        );
        mesh.getRoot().addOrReplaceChild(
                "lid",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(1, 0, 0, 14, 5, 14)
                        .texOffs(0, 0) // latch
                        .addBox(7, -1 - 1, 15 - 1, 2, 4, 1),
                PartPose.offset(0, 9, 1)
        );

        return LayerDefinition.create(mesh, 64, 64);
    }
}
