package artifacts.common.item.curio.hands;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class DiggingClawsItem extends CurioItem {

    private static final Tag.Named<Block> MINEABLE_WITH_DIGGING_CLAWS = BlockTags.createOptional(new ResourceLocation(Artifacts.MODID, "mineable/digging_claws"));

    public DiggingClawsItem() {
        addListener(EventPriority.LOW, PlayerEvent.BreakSpeed.class, this::onBreakSpeed);
        addListener(PlayerEvent.HarvestCheck.class, this::onHarvestCheck);
    }

    private boolean canHarvest(BlockState state) {
        Tier tier = ModConfig.server.diggingClaws.toolTier;
        return tier != null
                && TierSortingRegistry.isCorrectTierForDrops(tier, state)
                && state.is(MINEABLE_WITH_DIGGING_CLAWS);
    }

    private void onBreakSpeed(PlayerEvent.BreakSpeed event, LivingEntity wearer) {
        if (canHarvest(event.getState())) {
            event.setNewSpeed((float) (event.getNewSpeed() + ModConfig.server.diggingClaws.miningSpeedBonus.get()));
        }
    }

    private void onHarvestCheck(PlayerEvent.HarvestCheck event, LivingEntity wearer) {
        if (!event.canHarvest()) {
            event.setCanHarvest(canHarvest(event.getTargetBlock()));
            damageEquippedStacks(wearer);
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE, 1, 1);
    }
}
