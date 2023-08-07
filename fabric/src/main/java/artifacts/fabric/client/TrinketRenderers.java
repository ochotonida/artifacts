package artifacts.fabric.client;

import artifacts.Artifacts;
import artifacts.client.item.ArtifactRenderers;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class TrinketRenderers implements SimpleSynchronousResourceReloadListener {

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        ArtifactRenderers.register();
    }

    @Override
    public ResourceLocation getFabricId() {
        return Artifacts.id("trinket_renderers");
    }
}
