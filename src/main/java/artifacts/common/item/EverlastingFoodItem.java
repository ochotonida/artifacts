package artifacts.common.item;

import artifacts.common.config.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EverlastingFoodItem extends ArtifactItem {

    public EverlastingFoodItem(Food food) {
        super(new Item.Properties().food(food));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
        if (isEdible()) {
            entity.eat(world, stack.copy());
            if (!world.isClientSide && entity instanceof PlayerEntity) {
                ((PlayerEntity) entity).getCooldowns().addCooldown(this, Config.everlastingFoodCooldown);
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return Config.isCosmetic(this) ? 72000 : 24;
    }
}
