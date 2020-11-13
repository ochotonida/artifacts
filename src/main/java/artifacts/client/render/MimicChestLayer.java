package artifacts.client.render;

import artifacts.client.render.model.entity.MimicChestLayerModel;
import artifacts.client.render.model.entity.MimicModel;
import artifacts.common.entity.MimicEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.state.properties.ChestType;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Calendar;

public class MimicChestLayer extends LayerRenderer<MimicEntity, MimicModel> {

    private final MimicChestLayerModel model;
    private boolean isChristmas;

    public MimicChestLayer(IEntityRenderer<MimicEntity, MimicModel> entityRenderer) {
        super(entityRenderer);

        model = new MimicChestLayerModel();

        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) {
            isChristmas = true;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!mimic.isInvisible()) {
            matrixStack.push();

            matrixStack.rotate(Vector3f.XP.rotationDegrees(180));
            matrixStack.translate(-0.5, -1.5, -0.5);

            getEntityModel().copyModelAttributesTo(model);
            model.setLivingAnimations(mimic, limbSwing, limbSwingAmount, partialTicks);
            model.setRotationAngles(mimic, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder builder = getChestMaterial().getBuffer(buffer, RenderType::getEntityCutout);
            model.render(matrixStack, builder, packedLight, LivingRenderer.getPackedOverlay(mimic, 0), 1, 1, 1, 1);

            matrixStack.pop();
        }
    }

    private RenderMaterial getChestMaterial() {
        return Atlases.getChestMaterial(null, ChestType.SINGLE, isChristmas);
    }
}
