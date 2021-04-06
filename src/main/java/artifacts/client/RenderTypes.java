package artifacts.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class RenderTypes extends RenderType {

    private RenderTypes(String name, VertexFormat fmt, int glMode, int size, boolean doCrumbling, boolean depthSorting, Runnable onEnable, Runnable onDisable) {
        super(name, fmt, glMode, size, doCrumbling, depthSorting, onEnable, onDisable);
        throw new IllegalStateException();
    }

    public static RenderType unlit(ResourceLocation textureLocation) {
        State renderState = State.builder()
                .setTextureState(new TextureState(textureLocation, false, false))
                .setTransparencyState(NO_TRANSPARENCY)
                .setAlphaState(DEFAULT_ALPHA)
                .setCullState(NO_CULL)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true);
        return create("artifacts_entity_unlit", DefaultVertexFormats.NEW_ENTITY, GL11.GL_QUADS, 256, true, false, renderState);
    }
}
