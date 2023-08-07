package artifacts.fabric.mixin.item.wearable.luckyscarf;

import artifacts.fabric.trinket.TrinketsHelper;
import artifacts.item.wearable.WearableArtifactItem;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ApplyBonusCount.class)
public class ApplyBonusCountMixin {

    @Shadow
    @Final
    Enchantment enchantment;

    @ModifyExpressionValue(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/world/item/enchantment/Enchantment;Lnet/minecraft/world/item/ItemStack;)I"))
    private int addFortuneLevel(int level, ItemStack stack, LootContext lootContext) {
        Entity entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);

        if (this.enchantment == Enchantments.BLOCK_FORTUNE && entity instanceof LivingEntity livingEntity) {
            level += TrinketsHelper.findAllEquippedBy(livingEntity)
                    .map(ItemStack::getItem)
                    .map(item -> (WearableArtifactItem) item)
                    .map(WearableArtifactItem::getFortuneLevel)
                    .max(Integer::compareTo)
                    .orElse(0);
        }

        return level;
    }
}
