package artifacts.fabric.trinket;

import artifacts.item.wearable.WearableArtifactItem;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Stream;

public class TrinketsHelper {

    public static Stream<ItemStack> findAllEquippedBy(LivingEntity entity) {
        return TrinketsApi.getTrinketComponent(entity)
                .map(TrinketComponent::getAllEquipped)
                .orElse(List.of())
                .stream()
                .map(Tuple::getB)
                .filter(stack -> stack.getItem() instanceof WearableArtifactItem);
    }
}
