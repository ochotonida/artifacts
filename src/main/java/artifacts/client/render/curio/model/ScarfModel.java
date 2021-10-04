package artifacts.client.render.curio.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

public class ScarfModel extends HumanoidModel<LivingEntity> {

    private final ModelPart cloak = body.getChild("cloak");

    public ScarfModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
        super(part, renderType);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void prepareMobModel(LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
        if (entity instanceof AbstractClientPlayer player) {
            double x = Mth.lerp(partialTicks, player.xCloakO, player.xCloak) - Mth.lerp(partialTicks, player.xo, player.getX());
            double y = Mth.lerp(partialTicks, player.yCloakO, player.yCloak) - Mth.lerp(partialTicks, player.yo, player.getY());
            double z = Mth.lerp(partialTicks, player.zCloakO, player.zCloak) - Mth.lerp(partialTicks, player.zo, player.getZ());
            float f = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO);
            double d3 = Mth.sin(f * ((float) Math.PI / 180));
            double d4 = -Mth.cos(f * ((float) Math.PI / 180));
            float f1 = (float) y * 10;
            f1 = Mth.clamp(f1, -6, 32);
            float f2 = (float) (x * d3 + z * d4) * 100;
            f2 = Mth.clamp(f2, 0, 150);
            if (f2 < 0) {
                f2 = 0;
            }

            float f4 = Mth.lerp(partialTicks, player.oBob, player.bob);
            f1 = f1 + Mth.sin(Mth.lerp(partialTicks, player.walkDistO, player.walkDist) * 6) * 32 * f4;

            cloak.xRot = body.xRot + (6 + f2 / 2 + f1) / 180 * (float) Math.PI;
        }
    }

    public static MeshDefinition createScarf() {
        MeshDefinition mesh = createMesh(new CubeDeformation(0.5F), 0);

        mesh.getRoot().addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-6.01F, -2, -4, 12, 6, 8),
                PartPose.ZERO
        );

        mesh.getRoot().getChild("body").addOrReplaceChild(
                "cloak",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .addBox(-5, 0, 0, 5, 12, 2),
                PartPose.offset(0, 0, 1.99F)
        );

        return mesh;
    }
}
