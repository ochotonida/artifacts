package artifacts.client.render;

import artifacts.Artifacts;
import artifacts.client.render.model.entity.MimicModel;
import artifacts.common.entity.MimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class MimicRenderer extends MobRenderer<MimicEntity, MimicModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/mimic.png");

    public MimicRenderer(EntityRendererManager manager) {
        super(manager, new MimicModel(), 0.45F);
    }

    @Override
    public ResourceLocation getEntityTexture(MimicEntity entity) {
        return TEXTURE;
    }
}
