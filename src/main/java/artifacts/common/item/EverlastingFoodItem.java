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
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        if (isFood()) {
            entity.onFoodEaten(world, stack.copy());
            if (!world.isRemote && entity instanceof PlayerEntity) {
                ((PlayerEntity) entity).getCooldownTracker().setCooldown(this, Config.everlastingFoodCooldown);
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 24;
    }
}
