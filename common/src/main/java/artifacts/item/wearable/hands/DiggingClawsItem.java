package artifacts.item.wearable.hands;

import artifacts.Artifacts;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import artifacts.registry.ModTags;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class DiggingClawsItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.DIGGING_CLAWS_TOOL_TIER.get() <= 0 && ModGameRules.DIGGING_CLAWS_DIG_SPEED_BONUS.get() <= 0;
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        int tierLevel = getToolTierLevel();
        if (tierLevel > 0) {
            tooltip.add(tooltipLine("mining_level", Component.translatable("%s.tooltip.tool_tier.%s".formatted(Artifacts.MOD_ID, tierLevel))));
        }
        if (ModGameRules.DIGGING_CLAWS_DIG_SPEED_BONUS.get() > 0) {
            tooltip.add(tooltipLine("mining_speed"));
        }
    }

    public static int getToolTierLevel() {
        return Math.min(5, Math.max(0, ModGameRules.DIGGING_CLAWS_TOOL_TIER.get()));
    }

    public static Tier getToolTier() {
        return switch (getToolTierLevel()) {
            case 0 -> null;
            case 1 -> Tiers.WOOD;
            case 2 -> Tiers.STONE;
            case 3 -> Tiers.IRON;
            case 4 -> Tiers.DIAMOND;
            default -> Tiers.NETHERITE;
        };
    }

    public static boolean canDiggingClawsHarvest(LivingEntity entity, BlockState state) {
        if (ModItems.DIGGING_CLAWS.get().isEquippedBy(entity)) {
            Tier tier = DiggingClawsItem.getToolTier();
            return tier != null
                    && PlatformServices.platformHelper.isCorrectTierForDrops(tier, state)
                    && state.is(ModTags.MINEABLE_WITH_DIGGING_CLAWS);
        }
        return false;
    }

    public static float getSpeedBonus(Player player, BlockState state) {
        if (ModItems.DIGGING_CLAWS.get().isEquippedBy(player) && player.hasCorrectToolForDrops(state)) {
            return Math.max(0, ModGameRules.DIGGING_CLAWS_DIG_SPEED_BONUS.get() / 10F);
        }
        return 0;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_NETHERITE;
    }
}
