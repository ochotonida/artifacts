package artifacts.item;

import artifacts.item.wearable.necklace.CharmOfSinkingItem;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.List;
import java.util.UUID;

public class UmbrellaItem extends ArtifactItem {

    public static final AttributeModifier UMBRELLA_SLOW_FALLING = new AttributeModifier(UUID.fromString("a7a25453-2065-4a96-bc83-df600e13f390"), "artifacts:umbrella_slow_falling", -0.875, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public UmbrellaItem() {
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
        TickEvent.PLAYER_PRE.register(this::onPlayerTick);
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.UMBRELLA_IS_GLIDER.get() && !ModGameRules.UMBRELLA_IS_SHIELD.get();
    }

    @Override
    protected void addEffectsTooltip(ItemStack stack, List<MutableComponent> tooltip) {
        if (ModGameRules.UMBRELLA_IS_GLIDER.get()) {
            tooltip.add(tooltipLine("glider"));
        }
        if (ModGameRules.UMBRELLA_IS_SHIELD.get()) {
            tooltip.add(tooltipLine("shield"));
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!ModGameRules.UMBRELLA_IS_SHIELD.get()) {
            return super.use(level, player, hand);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    public static boolean isHoldingUmbrellaUpright(LivingEntity entity, InteractionHand hand) {
        return entity.getItemInHand(hand).getItem() instanceof UmbrellaItem && (!entity.isUsingItem() || entity.getUsedItemHand() != hand);
    }

    public static boolean isHoldingUmbrellaUpright(LivingEntity entity) {
        return isHoldingUmbrellaUpright(entity, InteractionHand.MAIN_HAND) || isHoldingUmbrellaUpright(entity, InteractionHand.OFF_HAND);
    }

    private void onPlayerTick(Player player) {
        AttributeInstance gravity = player.getAttribute(PlatformServices.platformHelper.getEntityGravityAttribute());
        if (gravity != null) {
            boolean isInWater = player.isInWater() && !CharmOfSinkingItem.shouldSink(player);
            if (ModGameRules.UMBRELLA_IS_GLIDER.get() && !player.onGround() && !isInWater && player.getDeltaMovement().y < 0 && !player.hasEffect(MobEffects.SLOW_FALLING)
                    && (player.getOffhandItem().getItem() == ModItems.UMBRELLA.get()
                    || player.getMainHandItem().getItem() == ModItems.UMBRELLA.get()) && !(player.isUsingItem() && !player.getUseItem().isEmpty() && player.getUseItem().getItem().getUseAnimation(player.getUseItem()) == UseAnim.BLOCK)) {
                if (!gravity.hasModifier(UmbrellaItem.UMBRELLA_SLOW_FALLING)) {
                    gravity.addTransientModifier(UmbrellaItem.UMBRELLA_SLOW_FALLING);
                }
                player.fallDistance = 0;
            } else if (gravity.hasModifier(UmbrellaItem.UMBRELLA_SLOW_FALLING)) {
                gravity.removeModifier(UmbrellaItem.UMBRELLA_SLOW_FALLING);
            }
        }
    }
}
