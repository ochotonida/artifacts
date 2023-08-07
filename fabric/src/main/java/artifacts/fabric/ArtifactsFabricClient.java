package artifacts.fabric;

import artifacts.Artifacts;
import artifacts.ArtifactsClient;
import artifacts.fabric.client.TrinketRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.server.packs.PackType;

public class ArtifactsFabricClient implements ClientModInitializer {

    public static final ModelResourceLocation UMBRELLA_HELD_MODEL = new ModelResourceLocation(Artifacts.id("umbrella_held"), "inventory");

    @Override
    public void onInitializeClient() {
        ArtifactsClient.init();

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new TrinketRenderers());

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(UMBRELLA_HELD_MODEL));
    }
}
