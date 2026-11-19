package artifacts.neoforge.integration.curios;

import artifacts.client.item.renderer.ArtifactRenderer;
import artifacts.equipment.client.EquipmentRenderingHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.function.Supplier;

public class CuriosRenderingHandler implements EquipmentRenderingHandler {

    @Override
    public void registerArtifactRenderer(Item item, Supplier<ArtifactRenderer> rendererSupplier) {
        ICurioRenderer.register(item, () -> new ArtifactCurioRenderer(rendererSupplier.get()));
    }

    public record ArtifactCurioRenderer(ArtifactRenderer renderer) implements ICurioRenderer {

        @Override
        public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S renderState, RenderLayerParent<S, M> renderLayerParent, EntityRendererProvider.Context context, float yRotation, float xRotation) {
            renderer.render(stack, renderState, renderLayerParent.getModel(), slotContext.index(), poseStack, submitNodeCollector, packedLight);
        }

        @Override
        public void renderFirstPersonHand(ItemStack stack, SlotContext slotContext, HumanoidArm arm, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, AvatarRenderState avatarRenderState, AbstractClientPlayer clientPlayer, int packedLight) {
            renderer.renderFirstPerson(stack, slotContext.index(), arm, poseStack, submitNodeCollector, avatarRenderState, clientPlayer, packedLight);
        }
    }
}
