package artifacts.neoforge.integration.curios;

import artifacts.client.item.renderer.ArtifactRenderer;
import artifacts.client.item.renderer.GloveArtifactRenderer;
import artifacts.equipment.client.EquipmentRenderingHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.function.Supplier;

public class CuriosRenderingHandler implements EquipmentRenderingHandler {

    @Override
    public void registerArtifactRenderer(Item item, Supplier<ArtifactRenderer> rendererSupplier) {
        ICurioRenderer.register(item, () -> new ArtifactCurioRenderer(rendererSupplier.get()));
    }

    @Override
    public @Nullable ArtifactRenderer getArtifactRenderer(Item item) {
        if (ICurioRenderer.getOrNull(item) instanceof ArtifactCurioRenderer(ArtifactRenderer renderer)) {
            return renderer;
        }
        return null;
    }

    @Override
    public void renderArm(PlayerRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, HumanoidArm side) {
        if (renderState.avatarRenderState == null) {
            return;
        }
        InteractionHand hand = side == renderState.avatarRenderState.mainArm ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;

        CuriosApi.getCuriosInventory(player).ifPresent(handler -> handler.getCurios().values().forEach(stacksHandler -> {
            if (stacksHandler != null) {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                IDynamicStackHandler cosmeticStacks = stacksHandler.getCosmeticStacks();

                for (int slot = hand == InteractionHand.MAIN_HAND ? 0 : 1; slot < stacks.getSlots(); slot += 2) {
                    ItemStack stack = cosmeticStacks.getStackInSlot(slot);
                    if (stack.isEmpty() && stacksHandler.getRenders().get(slot)) {
                        stack = stacks.getStackInSlot(slot);
                    }

                    GloveArtifactRenderer renderer = getGloveRenderer(stack);
                    if (renderer != null) {
                        renderer.renderFirstPersonArm(poseStack, submitNodeCollector, packedLight, player, side, stack.hasFoil());
                    }
                }
            }
        }));
    }

    public record ArtifactCurioRenderer(ArtifactRenderer renderer) implements ICurioRenderer {

        @Override
        public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S renderState, RenderLayerParent<S, M> renderLayerParent, EntityRendererProvider.Context context, float yRotation, float xRotation) {
            renderer.render(stack, renderState, renderLayerParent.getModel(), slotContext.index(), poseStack, submitNodeCollector, packedLight);
        }
    }
}
