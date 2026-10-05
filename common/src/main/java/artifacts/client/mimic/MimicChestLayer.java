package artifacts.client.mimic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteGetter;

public class MimicChestLayer extends RenderLayer<MimicRenderState, MimicModel> {

    private final MimicModel chestModel;
    private final SpriteGetter materials;

    public MimicChestLayer(RenderLayerParent<MimicRenderState, MimicModel> parent, EntityModelSet modelSet, SpriteGetter materials) {
        super(parent);
        chestModel = new MimicModel(modelSet.bakeLayer(MimicModel.CHEST_LAYER_LOCATION));
        this.materials = materials;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, MimicRenderState renderState, float yRotation, float xRotation) {
        if (!renderState.isInvisible) {
            poseStack.pushPose();

            poseStack.rotateDegrees(Axis.XP, 180);
            poseStack.translate(-0.5, -1.5, -0.5);

            chestModel.setupAnim(renderState);
            TextureAtlasSprite textureAtlasSprite = materials.get(renderState.chestMaterial);
            RenderType renderType = renderState.chestMaterial.renderType(RenderTypes::entityCutout);
            submitNodeCollector.submitModel(chestModel, renderState, poseStack, renderType, packedLight, LivingEntityRenderer.getOverlayCoords(renderState, 0), 0xFFFFFFFF, textureAtlasSprite, renderState.outlineColor);

            poseStack.popPose();
        }
    }
}
