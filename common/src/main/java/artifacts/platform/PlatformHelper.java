package artifacts.platform;

import artifacts.item.wearable.WearableArtifactItem;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public interface PlatformHelper {

    boolean isEquippedBy(@Nullable LivingEntity entity, Item item);

    Stream<ItemStack> findAllEquippedBy(LivingEntity entity, Item item);

    KeyMapping getToggleKey(WearableArtifactItem item);
}
