package artifacts.common.item.curio.hands;

import artifacts.Artifacts;
import artifacts.common.init.ModGameRules;
import artifacts.common.init.ModTags;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.function.Consumer;

public class DiggingClawsItem extends CurioItem {

    public DiggingClawsItem() {
        addListener(EventPriority.LOW, PlayerEvent.BreakSpeed.class, this::onBreakSpeed);
        addListener(PlayerEvent.HarvestCheck.class, this::onHarvestCheck);
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.DIGGING_CLAWS_TOOL_TIER.get() <= 0 && ModGameRules.DIGGING_CLAWS_DIG_SPEED_BONUS.get() <= 0;
    }

    @Override
    protected void addEffectsTooltip(Consumer<MutableComponent> tooltip) {
        int tierLevel = getToolTierLevel();
        if (tierLevel > 0) {
            tooltip.accept(tooltipLine("mining_level", Component.translatable("%s.tooltip.tool_tier.%s".formatted(Artifacts.MODID, tierLevel))));
        }
        if (ModGameRules.DIGGING_CLAWS_DIG_SPEED_BONUS.get() > 0) {
            tooltip.accept(tooltipLine("mining_speed"));
        }
    }

    private boolean canHarvest(BlockState state) {
        Tier tier = getToolTier();
        return tier != null
                && TierSortingRegistry.isCorrectTierForDrops(tier, state)
                && state.is(ModTags.MINEABLE_WITH_DIGGING_CLAWS);
    }

    private int getToolTierLevel() {
        return Math.min(5, Math.max(0, ModGameRules.DIGGING_CLAWS_TOOL_TIER.get()));
    }

    private Tier getToolTier() {
        return switch (getToolTierLevel()) {
            case 0 -> null;
            case 1 -> Tiers.WOOD;
            case 2 -> Tiers.STONE;
            case 3 -> Tiers.IRON;
            case 4 -> Tiers.DIAMOND;
            default -> Tiers.NETHERITE;
        };
    }

    private void onBreakSpeed(PlayerEvent.BreakSpeed event, LivingEntity wearer) {
        if (canHarvest(event.getState())) {
            float speedBonus = Math.max(0, ModGameRules.DIGGING_CLAWS_DIG_SPEED_BONUS.get() / 10F);
            event.setNewSpeed(event.getNewSpeed() + speedBonus);
        }
    }

    private void onHarvestCheck(PlayerEvent.HarvestCheck event, LivingEntity wearer) {
        if (!event.canHarvest()) {
            event.setCanHarvest(canHarvest(event.getTargetBlock()));
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE, 1, 1);
    }
}
