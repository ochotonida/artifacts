package artifacts.client.render.entity;

import artifacts.client.render.entity.model.MimicChestLayerModel;
import artifacts.client.render.entity.model.MimicModel;
import artifacts.common.config.ModConfig;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MimicChestLayer extends LayerRenderer<MimicEntity, MimicModel> {

    private final MimicChestLayerModel chestModel;
    public final RenderMaterial vanillaChestMaterial;
    public final List<RenderMaterial> chestMaterials;

    @SuppressWarnings("deprecation")
    public MimicChestLayer(IEntityRenderer<MimicEntity, MimicModel> entityRenderer) {
        super(entityRenderer);

        Calendar calendar = Calendar.getInstance();
        boolean isChristmas = calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26;

        chestModel = new MimicChestLayerModel();
        chestMaterials = new ArrayList<>();
        vanillaChestMaterial = Atlases.chooseMaterial(null, ChestType.SINGLE, isChristmas);

        if (ModList.get().isLoaded("lootr")) {
            ResourceLocation chestLocation = new ResourceLocation("lootr", "chest");
            chestMaterials.add(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, chestLocation));
        } else {
            if (!isChristmas && ModList.get().isLoaded("quark")) {
                List<String> chestTypes = Arrays.asList(
                        "oak",
                        "spruce",
                        "birch",
                        "jungle",
                        "acacia",
                        "dark_oak",
                        "warped",
                        "crimson"
                );

                ResourceLocation atlas = new ResourceLocation("textures/atlas/chest.png");
                for (String chestType : chestTypes) {
                    ResourceLocation chestLocation = new ResourceLocation("quark", String.format("model/chest/%s/normal", chestType));
                    chestMaterials.add(new RenderMaterial(atlas, chestLocation));
                }
            }

            chestMaterials.add(vanillaChestMaterial);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!mimic.isInvisible()) {
            matrixStack.pushPose();

            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180));
            matrixStack.translate(-0.5, -1.5, -0.5);

            getParentModel().copyPropertiesTo(chestModel);
            chestModel.prepareMobModel(mimic, limbSwing, limbSwingAmount, partialTicks);
            chestModel.setupAnim(mimic, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder builder = getChestMaterial(mimic).buffer(buffer, RenderType::entityCutout);
            chestModel.renderToBuffer(matrixStack, builder, packedLight, LivingRenderer.getOverlayCoords(mimic, 0), 1, 1, 1, 1);

            matrixStack.popPose();
        }
    }

    private RenderMaterial getChestMaterial(MimicEntity mimic) {
        if (!ModConfig.client.useModdedMimicTextures.get()) {
            return vanillaChestMaterial;
        }
        if (chestMaterials.size() == 1) {
            return chestMaterials.get(0);
        }
        return chestMaterials.get((int) (Math.abs(mimic.getUUID().getMostSignificantBits()) % chestMaterials.size()));
    }
}
