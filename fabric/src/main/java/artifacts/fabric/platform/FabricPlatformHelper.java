package artifacts.fabric.platform;

import artifacts.client.item.renderer.ArtifactRenderer;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.platform.PlatformHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import io.github.fabricators_of_create.porting_lib.attributes.PortingLibAttributes;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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
        return TrinketsApi.getTrinketComponent(entity)
                .map(TrinketComponent::getAllEquipped)
                .orElse(List.of())
                .stream()
                .map(Tuple::getB)
                .filter(stack -> stack.getItem() == item);
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
    public ResourceLocation getQueriedLootTableId(LootContext lootContext) {
        return lootContext.getQueriedLootTableId(); // TODO this doesn't work yet
    }

    @Override
    public void registerArtifactRenderer(WearableArtifactItem item, Supplier<ArtifactRenderer> rendererSupplier) {
        TrinketRendererRegistry.registerRenderer(item, new ArtifactCurioRenderer(rendererSupplier.get()));
    }

    @Override
    public ArtifactRenderer getArtifactRenderer(Item item) {
        return (ArtifactRenderer) TrinketRendererRegistry.getRenderer(item).orElseThrow();
    }

    private record ArtifactCurioRenderer(ArtifactRenderer renderer) implements TrinketRenderer {

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
            renderer.render(
                    stack,
                    entity,
                    slotReference.index(),
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
