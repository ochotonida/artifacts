package artifacts.fabric.mixin.item.wearable.superstitioushat;

import artifacts.fabric.trinket.TrinketsHelper;
import artifacts.item.wearable.WearableArtifactItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(method = "getMobLooting", at = @At("RETURN"), cancellable = true)
    private static void increaseLooting(LivingEntity entity, CallbackInfoReturnable<Integer> info) {
        int level = TrinketsHelper.findAllEquippedBy(entity)
                .map(ItemStack::getItem)
                .map(item -> (WearableArtifactItem) item)
                .map(WearableArtifactItem::getLootingLevel)
                .max(Integer::compareTo)
                .orElse(0);

        if (level > 0) {
            info.setReturnValue(info.getReturnValueI() + level);
        }
    }
}
