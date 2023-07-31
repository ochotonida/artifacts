package artifacts.client.item;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public abstract class ModRenderTypes extends RenderType {

    public ModRenderTypes(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int size, boolean doCrumbling, boolean depthSorting, Runnable onEnable, Runnable onDisable) {
        super(string, vertexFormat, mode, size, doCrumbling, depthSorting, onEnable, onDisable);
        throw new IllegalStateException();
    }

    // TODO this doesn't look the same as before, not actually unlit
    // creating a custom renderstate is too much work though
    private static final Function<ResourceLocation, RenderType> UNLIT_TRANSLUCENT = Util.memoize(Util.memoize(textureLocation -> {
        RenderType.CompositeState renderState = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_CUTOUT_NO_CULL_SHADER) // no idea what this is, good enough until something breaks
                .setTextureState(new RenderStateShard.TextureStateShard(textureLocation, false, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true);
        return create("artifacts_entity_unlit", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, renderState);
    }));

    public static RenderType unlit(ResourceLocation textureLocation) {
        return UNLIT_TRANSLUCENT.apply(textureLocation);
    }
}
