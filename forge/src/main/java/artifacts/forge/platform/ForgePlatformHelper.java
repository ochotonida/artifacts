package artifacts.forge.platform;

import artifacts.forge.client.InputEventHandler;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.platform.PlatformHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

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
    public KeyMapping getToggleKey(WearableArtifactItem item) {
        return InputEventHandler.getToggleKey(item);
    }
}
