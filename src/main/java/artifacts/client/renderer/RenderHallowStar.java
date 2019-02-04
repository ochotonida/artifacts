package artifacts.client.renderer;

import artifacts.Artifacts;
import artifacts.client.model.entity.ModelHallowStar;
import artifacts.common.entity.EntityHallowStar;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RenderHallowStar extends Render<EntityHallowStar> {

    public static final Factory FACTORY = new Factory();

    private static final ResourceLocation TEXTURES = new ResourceLocation(Artifacts.MODID, "textures/entity/hallow_star/hallow_star.png");

    private static final ModelBase MODEL = new ModelHallowStar();

    public RenderHallowStar(RenderManager manager) {
        super(manager);
        shadowSize = 0.3F;
    }

    public void doRender(EntityHallowStar entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        bindEntityTexture(entity);
        GlStateManager.translate((float) x, (float) y, (float) z);
        MODEL.render(entity, 0, 0, (entity.ticksAlive + partialTicks) * 30, 0, 0, 1 / 16F);
        GlStateManager.popMatrix();

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityHallowStar entity) {
        return TEXTURES;
    }

    public static class Factory implements IRenderFactory<EntityHallowStar> {

        @Override
        public Render<? super EntityHallowStar> createRenderFor(RenderManager manager) {
            return new RenderHallowStar(manager);
        }
    }
}
