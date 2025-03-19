package artifacts.mixin.item.wearable.pendant;

import artifacts.item.wearable.necklace.PendantItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(method = "doPostHurtEffects", at = @At("HEAD"))
    private static void doPostAttackEffects(LivingEntity livingEntity, Entity entity, CallbackInfo ci) {
        for (BiConsumer<LivingEntity, Entity> listener : PendantItem.LISTENERS) {
            listener.accept(livingEntity, entity);
        }
    }
}
