package artifacts.fabric.platform;

import artifacts.client.item.renderer.ArtifactRenderer;
import artifacts.component.SwimData;
import artifacts.fabric.client.CosmeticsHelper;
import artifacts.fabric.registry.ModComponents;
import artifacts.fabric.trinket.TrinketsHelper;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.platform.PlatformHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import io.github.fabricators_of_create.porting_lib.attributes.PortingLibAttributes;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FabricPlatformHelper implements PlatformHelper {

    @Override
    public boolean isEquippedBy(@Nullable LivingEntity entity, Item item) {
        return TrinketsApi.getTrinketComponent(entity)
                .map(component -> component.isEquipped(item))
                .orElse(false);
    }

    @Override
    public Stream<ItemStack> findAllEquippedBy(LivingEntity entity, Item item) {
        return TrinketsHelper.findAllEquippedBy(entity).filter(stack -> stack.getItem() == item);
    }

    @Override
    public Attribute getStepHeightAttribute() {
        return StepHeightEntityAttributeMain.STEP_HEIGHT;
    }

    @Override
    public Attribute getSwimSpeedAttribute() {
        return PortingLibAttributes.SWIM_SPEED;
    }

    @Override
    public Attribute getEntityGravityAttribute() {
        return PortingLibAttributes.ENTITY_GRAVITY;
    }

    @Override
    public boolean isCorrectTierForDrops(Tier tier, BlockState state) {
        int i = tier.getLevel();
        if (i < 3 && state.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        } else if (i < 2 && state.is(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        } else return i >= 1 || !state.is(BlockTags.NEEDS_STONE_TOOL);
    }

    @Nullable
    @Override
    public SwimData getSwimData(LivingEntity player) {
        return ModComponents.SWIM_DATA.getNullable(player);
    }

    @Override
    public boolean isEyeInWater(Player player) {
        return player.isEyeInFluid(FluidTags.WATER);
    }

    @Override
    public void registerArtifactRenderer(WearableArtifactItem item, Supplier<ArtifactRenderer> rendererSupplier) {
        TrinketRendererRegistry.registerRenderer(item, new ArtifactTrinketRenderer(rendererSupplier.get()));
    }

    @Nullable
    @Override
    public ArtifactRenderer getArtifactRenderer(Item item) {
        Optional<TrinketRenderer> renderer = TrinketRendererRegistry.getRenderer(item);
        if (renderer.isPresent() && renderer.get() instanceof ArtifactTrinketRenderer artifactTrinketRenderer) {
            return artifactTrinketRenderer.renderer();
        }
        return null;
    }

    private record ArtifactTrinketRenderer(ArtifactRenderer renderer) implements TrinketRenderer {

        @Override
        public void render(
                ItemStack stack,
                SlotReference slotReference,
                EntityModel<? extends LivingEntity> entityModel,
                PoseStack poseStack,
                MultiBufferSource multiBufferSource,
                int light,
                LivingEntity entity,
                float limbSwing,
                float limbSwingAmount,
                float partialTicks,
                float ageInTicks,
                float netHeadYaw,
                float headPitch
        ) {
            if (CosmeticsHelper.isCosmeticsDisabled(stack)) {
                return;
            }
            int index = slotReference.index() + (slotReference.inventory().getSlotType().getGroup().equals("hand") ? 0 : 1);
            renderer.render(
                    stack,
                    entity,
                    index,
                    poseStack,
                    multiBufferSource,
                    light,
                    limbSwing,
                    limbSwingAmount,
                    partialTicks,
                    ageInTicks,
                    netHeadYaw,
                    headPitch
            );
        }
    }
}
