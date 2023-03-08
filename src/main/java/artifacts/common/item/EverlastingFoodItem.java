package artifacts.common.item;

import artifacts.common.init.ModGameRules;
import artifacts.common.init.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
            if (!world.isClientSide && entity instanceof Player player) {
                int cooldown;
                if (this == ModItems.ETERNAL_STEAK.get()) {
                    cooldown = ModGameRules.ETERNAL_STEAK_COOLDOWN.get() * 20;
                } else {
                    cooldown = ModGameRules.EVERLASTING_BEEF_COOLDOWN.get() * 20;
                }
                if (cooldown > 0) {
                    player.getCooldowns().addCooldown(this, cooldown);
                }
            }
        }

        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (this == ModItems.ETERNAL_STEAK.get()) {
            if (!ModGameRules.ETERNAL_STEAK_ENABLED.get()) {
                return InteractionResultHolder.pass(player.getItemInHand(hand));
            }
        } else if (!ModGameRules.EVERLASTING_BEEF_ENABLED.get()) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
        return super.use(level, player, hand);
    }

    @Override
    public boolean isEdible() {
        if (this == ModItems.ETERNAL_STEAK.get()) {
            if (!ModGameRules.ETERNAL_STEAK_ENABLED.get()) {
                return false;
            }
        } else if (!ModGameRules.EVERLASTING_BEEF_ENABLED.get()) {
            return false;
        }
        return super.isEdible();
    }
}
