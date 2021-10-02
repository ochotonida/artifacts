package artifacts.common.item;

import artifacts.common.config.ModConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EverlastingFoodItem extends ArtifactItem {

    public EverlastingFoodItem(FoodProperties food) {
        super(new Item.Properties().food(food));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (isEdible()) {
            entity.eat(world, stack.copy());
            if (!world.isClientSide && entity instanceof Player) {
                int cooldown = ModConfig.server.everlastingBeef.cooldown.get();
                ((Player) entity).getCooldowns().addCooldown(this, cooldown);
            }
        }

        stack.hurtAndBreak(1, entity, damager -> {
        });

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return ModConfig.server.isCosmetic(this) ? 72000 : ModConfig.server.everlastingBeef.useDuration.get();
    }
}
