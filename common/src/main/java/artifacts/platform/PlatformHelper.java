package artifacts.platform;

import artifacts.client.item.renderer.ArtifactRenderer;
import artifacts.component.SwimData;
import artifacts.item.wearable.WearableArtifactItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;
import java.util.stream.Stream;

public interface PlatformHelper {

    boolean isEquippedBy(@Nullable LivingEntity entity, Item item);

    Stream<ItemStack> findAllEquippedBy(LivingEntity entity, Item item);

    Attribute getStepHeightAttribute();

    Attribute getSwimSpeedAttribute();

    Attribute getEntityGravityAttribute();

    boolean isCorrectTierForDrops(Tier tier, BlockState state);

    @Nullable
    SwimData getSwimData(LivingEntity player);

    boolean isEyeInWater(Player player);

    void registerArtifactRenderer(WearableArtifactItem item, Supplier<ArtifactRenderer> rendererSupplier);

    @Nullable
    ArtifactRenderer getArtifactRenderer(Item item);
}
