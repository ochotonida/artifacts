package artifacts.fabric.mixin.item.wearable.drinkinghat;

import artifacts.item.wearable.head.DrinkingHatItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    protected int useItemRemaining;

    @Shadow
    public abstract ItemStack getUseItem();

    @Inject(method = "startUsingItem", at = @At(value = "INVOKE_ASSIGN", shift = At.Shift.AFTER, target = "Lnet/minecraft/world/item/ItemStack;getUseDuration()I"))
    private void decreaseDrinkingDuration(InteractionHand hand, CallbackInfo info) {
        //noinspection ConstantConditions
        useItemRemaining = DrinkingHatItem.getDrinkingHatUseDuration(
                (LivingEntity) (Object) this,
                getUseItem().getUseAnimation(),
                useItemRemaining
        );
    }
}
