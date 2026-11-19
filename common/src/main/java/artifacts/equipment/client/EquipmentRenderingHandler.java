package artifacts.equipment.client;

import artifacts.client.item.renderer.ArtifactRenderer;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public interface EquipmentRenderingHandler {

    void registerArtifactRenderer(Item item, Supplier<ArtifactRenderer> rendererSupplier);

}
