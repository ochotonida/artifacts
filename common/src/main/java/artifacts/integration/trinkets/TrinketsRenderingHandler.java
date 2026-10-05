package artifacts.integration.trinkets;

import artifacts.client.item.renderer.ArtifactRenderer;
import artifacts.equipment.client.EquipmentRenderingHandler;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.client.TrinketRenderer;
import eu.pb4.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class TrinketsRenderingHandler implements EquipmentRenderingHandler {
    
    @Override
    public void registerArtifactRenderer(Item item, Supplier<ArtifactRenderer> rendererSupplier) {
        TrinketRendererRegistry.registerRenderer(item, new ArtifactTrinketRenderer(Suppliers.memoize(rendererSupplier::get)));
    }

    @Override
    public @Nullable ArtifactRenderer getArtifactRenderer(Item item) {
        Optional<TrinketRenderer> renderer = TrinketRendererRegistry.getRenderer(item);
        if (renderer.isPresent() && renderer.get() instanceof ArtifactTrinketRenderer(
                Supplier<ArtifactRenderer> supplier
        )) {
            return supplier.get();
        }
        return null;
    }

    @Override
    public void renderArm(PlayerRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, HumanoidArm side) {
        if (!TrinketRenderer.renderInFirstPerson()) {
            return;
        }
        /* FIXME
        String groupId = side == player.getMainArm() ? "hand" : "offhand";
        TrinketsApi.getAttachment(player).forEachVisible(((slotAccess, stack) -> {
            if (slotAccess.inventory().slotType().group().equals(groupId)) {
                GloveArtifactRenderer gloveRenderer = getGloveRenderer(stack);
                if (gloveRenderer != null) {
                    gloveRenderer.renderFirstPersonArm(poseStack, submitNodeCollector, packedLight, player, side, stack.hasFoil());
                }
            }
        }));
         */
    }

    public record ArtifactTrinketRenderer(Supplier<ArtifactRenderer> renderer) implements TrinketRenderer {

        @Override
        public void submit(ItemStack stack, TrinketSlotAccess slotReference, EntityModel<? extends LivingEntityRenderState> contextModel, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, LivingEntityRenderState state, float limbAngle, float limbDistance) {
            int slotIndex = slotReference.index();
            if (slotReference.inventory().slotType().group().equals("offhand")) {
                slotIndex += 1;
            }
            renderer.get().render(stack, state, contextModel, slotIndex, poseStack, submitNodeCollector, light);
        }
    }
}
