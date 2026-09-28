package artifacts.neoforge.integration.curios;

import artifacts.equipment.client.EquipmentRenderingManager;
import artifacts.mixin.accessors.client.LivingEntityRendererAccessor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import top.theillusivec4.curios.client.CuriosLayer;

public class CuriosCompatClient {

    public static void setup(IEventBus modBus) {
        EquipmentRenderingManager.register(new CuriosRenderingHandler());

        modBus.addListener(CuriosCompatClient::onAddLayers);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        loop:
        for (EntityType<?> entity : event.getEntityTypes()) {
            if (event.getRenderer(entity) instanceof LivingEntityRenderer renderer && renderer.getModel() instanceof HumanoidModel<?>) {
                for (RenderLayer<?, ?> layer : ((LivingEntityRendererAccessor<?, ?>) renderer).getLayers()) {
                    if (layer instanceof CuriosLayer<?, ?>) {
                        continue loop;
                    }
                }
                renderer.addLayer(new CuriosLayer<>(renderer, event.getContext()));
            }
        }
    }
}
