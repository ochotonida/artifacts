package artifacts.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EverlastingFoodItem extends ArtifactItem {

    public EverlastingFoodItem(Properties properties, String name) {
        super(properties, name);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        if (isFood()) {
            entity.onFoodEaten(world, stack.copy());
            if (!world.isRemote && entity instanceof PlayerEntity) {
                ((PlayerEntity) entity).getCooldownTracker().setCooldown(this, 200);
            }
        }
        return stack;
    }
}
