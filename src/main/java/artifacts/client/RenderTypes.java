package artifacts.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class RenderTypes extends RenderType {

    private RenderTypes(String name, VertexFormat fmt, int glMode, int size, boolean doCrumbling, boolean depthSorting, Runnable onEnable, Runnable onDisable) {
        super(name, fmt, glMode, size, doCrumbling, depthSorting, onEnable, onDisable);
        throw new IllegalStateException("This class must not be instantiated");
    }

    public static RenderType unlit(ResourceLocation textureLocation) {
        State renderState = State.getBuilder()
                .texture(new TextureState(textureLocation, false, false))
                .transparency(NO_TRANSPARENCY)
                .alpha(DEFAULT_ALPHA)
                .cull(CULL_DISABLED)
                .lightmap(LIGHTMAP_ENABLED)
                .overlay(OVERLAY_ENABLED)
                .build(true);
        return makeType("artifacts_entity_unlit", DefaultVertexFormats.ENTITY, GL11.GL_QUADS, 256, true, false, renderState);
    }

    public static RenderType translucent(ResourceLocation textureLocation) {
        State renderState = State.getBuilder()
                .texture(new TextureState(textureLocation, false, false))
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .alpha(DEFAULT_ALPHA)
                .cull(CULL_DISABLED)
                .diffuseLighting(DIFFUSE_LIGHTING_ENABLED)
                .lightmap(LIGHTMAP_ENABLED)
                .overlay(OVERLAY_ENABLED)
                .build(true);
        return makeType("artifacts_entity_translucent", DefaultVertexFormats.ENTITY, GL11.GL_QUADS, 256, true, true, renderState);
    }
}
