package artifacts.equipment.client;

import artifacts.client.item.renderer.ArtifactRenderer;
import net.minecraft.world.item.Item;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

public class EquipmentRenderingManager {

    private static final Set<EquipmentRenderingHandler> RENDERING_HANDLERS = new LinkedHashSet<>();

    public static void register(EquipmentRenderingHandler integration) {
        RENDERING_HANDLERS.add(integration);
    }

    public static void registerArtifactRenderer(Item item, Supplier<ArtifactRenderer> rendererFactory) {
        for (EquipmentRenderingHandler handler : RENDERING_HANDLERS) {
            handler.registerArtifactRenderer(item, rendererFactory);
        }
    }
}
