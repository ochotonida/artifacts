package artifacts.equipment.client;

import artifacts.client.item.renderer.ArtifactRenderer;
import artifacts.client.item.renderer.GloveArtifactRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface EquipmentRenderingHandler {

    void registerArtifactRenderer(Item item, Supplier<ArtifactRenderer> rendererSupplier);

    @Nullable
    ArtifactRenderer getArtifactRenderer(Item item);

    @Nullable
    default GloveArtifactRenderer getGloveRenderer(ItemStack stack) {
        if (!stack.isEmpty() && getArtifactRenderer(stack.getItem()) instanceof GloveArtifactRenderer gloveRenderer) {
            return gloveRenderer;
        }
        return null;
    }

    void renderArm(PlayerRenderState renderState, PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int packedLight, HumanoidArm side);

}
