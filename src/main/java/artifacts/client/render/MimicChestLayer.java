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
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.state.properties.ChestType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.ModList;

import java.util.Calendar;

public class MimicChestLayer extends LayerRenderer<MimicEntity, MimicModel> {

    private final MimicChestLayerModel model;
    public final RenderMaterial material;


    public MimicChestLayer(IEntityRenderer<MimicEntity, MimicModel> entityRenderer) {
        super(entityRenderer);

        model = new MimicChestLayerModel();

        if (ModList.get().isLoaded("lootr")) {
            material = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation("lootr", "chest"));
        } else {
            Calendar calendar = Calendar.getInstance();
            boolean isChristmas = calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26;
            material = Atlases.chooseMaterial(null, ChestType.SINGLE, isChristmas);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!mimic.isInvisible()) {
            matrixStack.pushPose();

            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180));
            matrixStack.translate(-0.5, -1.5, -0.5);

            getParentModel().copyPropertiesTo(model);
            model.prepareMobModel(mimic, limbSwing, limbSwingAmount, partialTicks);
            model.setupAnim(mimic, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder builder = material.buffer(buffer, RenderType::entityCutout);
            model.renderToBuffer(matrixStack, builder, packedLight, LivingRenderer.getOverlayCoords(mimic, 0), 1, 1, 1, 1);

            matrixStack.popPose();
        }
    }
}
