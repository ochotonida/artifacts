package artifacts.integration.trinkets;

import artifacts.client.item.renderer.ArtifactRenderer;
import artifacts.equipment.client.EquipmentRenderingHandler;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.client.TrinketRenderer;
import eu.pb4.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class TrinketsRenderingHandler implements EquipmentRenderingHandler {
    
    @Override
    public void registerArtifactRenderer(Item item, Supplier<ArtifactRenderer> rendererSupplier) {
        TrinketRendererRegistry.registerRenderer(item, new ArtifactTrinketRenderer(Suppliers.memoize(rendererSupplier::get)));
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

        @Override
        public void submitFirstPersonLeftArm(ItemStack stack, TrinketSlotAccess slotAccess, EntityModel<? extends LivingEntityRenderState> contextModel, ModelPart armPart, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, LocalPlayer player, boolean isMainHand) {
            HumanoidArm mainArm = isMainHand ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
            submitFirstPersonArm(stack, slotAccess, poseStack, submitNodeCollector, light, player, HumanoidArm.LEFT, mainArm);
        }

        @Override
        public void submitFirstPersonRightArm(ItemStack stack, TrinketSlotAccess slotAccess, EntityModel<? extends LivingEntityRenderState> contextModel, ModelPart armPart, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, LocalPlayer player, boolean isMainHand) {
            HumanoidArm mainArm = isMainHand ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
            submitFirstPersonArm(stack, slotAccess, poseStack, submitNodeCollector, light, player, HumanoidArm.RIGHT, mainArm);
        }

        private void submitFirstPersonArm(ItemStack stack, TrinketSlotAccess slotAccess, PoseStack poseStack, SubmitNodeCollector submit, int light, LocalPlayer player, HumanoidArm arm, HumanoidArm mainArm) {
            int slotIndex = slotAccess.index();
            if (slotAccess.inventory().slotType().group().equals("offhand")) {
                slotIndex += 1;
            }
            // This check isn't strictly needed but prevents unnecessary work
            if (renderer.get().isVisibleInFirstPerson(slotIndex, arm, mainArm)) {
                // Trinkets doesn't provide a render state, extract one instead
                @SuppressWarnings("unchecked")
                AvatarRenderer<AbstractClientPlayer> playerRenderer = (AvatarRenderer<AbstractClientPlayer>) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player);
                AvatarRenderState renderState = playerRenderer.createRenderState(player, Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true));

                renderer.get().renderFirstPerson(stack, slotIndex, arm, poseStack, submit, renderState, player, light);
            }
        }
    }
}
