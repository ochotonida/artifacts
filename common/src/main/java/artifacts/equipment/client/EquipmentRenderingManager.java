package artifacts.equipment.client;

import artifacts.Artifacts;
import artifacts.client.item.renderer.ArtifactRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

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

    // TODO unused
    @Nullable
    public static ArtifactRenderer getArtifactRenderer(Item item) {
        for (EquipmentRenderingHandler handler : RENDERING_HANDLERS) {
            ArtifactRenderer renderer = handler.getArtifactRenderer(item);

            if (renderer != null) {
                return renderer;
            }
        }

        return null;
    }

    public static void renderFirstPersonArm(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int light, AbstractClientPlayer player, HumanoidArm side) {
        if (Artifacts.CONFIG.client.showFirstPersonGloves.get()) {
            for (EquipmentRenderingHandler handler : RENDERING_HANDLERS) {
                handler.renderArm(matrixStack, submitNodeCollector, light, player, side);
            }
        }
    }
}
