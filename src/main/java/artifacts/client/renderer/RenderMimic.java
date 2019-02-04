package artifacts.client.renderer;

import artifacts.Artifacts;
import artifacts.client.model.entity.ModelMimic;
import artifacts.common.entity.EntityMimic;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RenderMimic extends RenderLiving<EntityMimic> {

    public static final Factory FACTORY = new Factory();

    private static final ResourceLocation TEXTURES = new ResourceLocation(Artifacts.MODID, "textures/entity/mimic/mimic.png");

    protected RenderMimic(RenderManager renderManager) {
        super(renderManager, new ModelMimic(), 0.45F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMimic entity) {
        return TEXTURES;
    }

    public static class Factory implements IRenderFactory<EntityMimic> {

        @Override
        public Render<? super EntityMimic> createRenderFor(RenderManager manager) {
            return new RenderMimic(manager);
        }
    }
}