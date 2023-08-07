package artifacts.fabric.mixin.item.wearable;

import artifacts.Artifacts;
import artifacts.fabric.client.CosmeticsHelper;
import artifacts.item.ArtifactItem;
import artifacts.item.wearable.WearableArtifactItem;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(WearableArtifactItem.class)
public abstract class WearableArtifactItemMixin extends ArtifactItem {

    @Shadow
    public abstract SoundEvent getEquipSound();

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        if (TrinketItem.equipItem(user, stack)) {
            user.playSound(getEquipSound(), 1, 1);

            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }

        return super.use(level, user, hand);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack slotStack, ItemStack holdingStack, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        if (clickAction == ClickAction.SECONDARY && holdingStack.isEmpty()) {
            CosmeticsHelper.toggleCosmetics(slotStack);
            return true;
        }

        return false;
    }

    @Override
    protected void addTooltip(ItemStack stack, List<MutableComponent> tooltip) {
        if (isCosmetic()) {
            tooltip.add(Component.translatable("%s.tooltip.cosmetic".formatted(Artifacts.MOD_ID)).withStyle(ChatFormatting.ITALIC));
        }
        if (CosmeticsHelper.isCosmeticsDisabled(stack)) {
            tooltip.add(Component.translatable("%s.tooltip.cosmetics_disabled".formatted(Artifacts.MOD_ID)).withStyle(ChatFormatting.ITALIC));
        }
        if (!isCosmetic()) {
            addEffectsTooltip(stack, tooltip);
        }
    }
}
