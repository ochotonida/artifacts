package artifacts.fabric;

import artifacts.ArtifactsClient;
import artifacts.fabric.client.TrinketRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class ArtifactsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ArtifactsClient.init();

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new TrinketRenderers());
    }
}
