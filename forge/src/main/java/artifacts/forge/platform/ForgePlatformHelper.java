package artifacts.forge.platform;

import artifacts.client.item.renderer.ArtifactRenderer;
import artifacts.component.SwimData;
import artifacts.forge.capability.SwimDataCapability;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.platform.PlatformHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.TierSortingRegistry;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class ForgePlatformHelper implements PlatformHelper {

    @Override
    public boolean isEquippedBy(@Nullable LivingEntity entity, Item item) {
        return entity != null && CuriosApi.getCuriosHelper().findFirstCurio(entity, item).isPresent();
    }

    @Override
    public Stream<ItemStack> findAllEquippedBy(LivingEntity entity, Item item) {
        return CuriosApi.getCuriosHelper().findCurios(entity, item).stream().map(SlotResult::stack);
    }

    @Override
    public Attribute getStepHeightAttribute() {
        return ForgeMod.STEP_HEIGHT_ADDITION.get();
    }

    @Override
    public Attribute getSwimSpeedAttribute() {
        return ForgeMod.SWIM_SPEED.get();
    }

    @Override
    public Attribute getEntityGravityAttribute() {
        return ForgeMod.ENTITY_GRAVITY.get();
    }

    @Override
    public ResourceLocation getQueriedLootTableId(LootContext lootContext) {
        return lootContext.getQueriedLootTableId();
    }

    @Override
    public boolean isCorrectTierForDrops(Tier tier, BlockState state) {
        return TierSortingRegistry.isCorrectTierForDrops(tier, state);
    }

    @Nullable
    @Override
    public SwimData getSwimData(LivingEntity player) {
        // noinspection ConstantConditions
        return player.getCapability(SwimDataCapability.CAPABILITY).orElse(null);
    }

    @Override
    public boolean isEyeInWater(Player player) {
        return player.isEyeInFluidType(ForgeMod.WATER_TYPE.get());
    }

    @Override
    public void registerArtifactRenderer(WearableArtifactItem item, Supplier<ArtifactRenderer> rendererSupplier) {
        CuriosRendererRegistry.register(item, () -> new ArtifactCurioRenderer(rendererSupplier.get()));
    }

    @Override
    public ArtifactRenderer getArtifactRenderer(Item item) {
        return ((ArtifactCurioRenderer) CuriosRendererRegistry.getRenderer(item).orElseThrow()).renderer;
    }

    private record ArtifactCurioRenderer(ArtifactRenderer renderer) implements ICurioRenderer {

        @Override
        public <T extends LivingEntity, M extends EntityModel<T>> void render(
                ItemStack stack,
                SlotContext slotContext,
                PoseStack poseStack,
                RenderLayerParent<T, M> renderLayerParent,
                MultiBufferSource multiBufferSource,
                int light,
                float limbSwing,
                float limbSwingAmount,
                float partialTicks,
                float ageInTicks,
                float netHeadYaw,
                float headPitch
        ) {
            renderer.render(
                    stack,
                    slotContext.entity(),
                    slotContext.index(),
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
