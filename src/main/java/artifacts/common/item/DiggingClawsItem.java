package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.hands.ClawsModel;
import artifacts.common.config.ModConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;

public class DiggingClawsItem extends GloveItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/digging_claws.png");

    public DiggingClawsItem() {
        addListener(EventPriority.LOW, PlayerEvent.BreakSpeed.class, this::onBreakSpeed);
        addListener(PlayerEvent.HarvestCheck.class, this::onHarvestCheck);
    }

    private boolean canHarvest(BlockState state) {
        List<String> toolTypes = ModConfig.server.diggingClaws.toolTypes.get();
        int diggingClawsHarvestLevel = ModConfig.server.diggingClaws.harvestLevel.get() - 1;
        return state.getHarvestLevel() <= diggingClawsHarvestLevel &&
                (toolTypes.contains(state.getHarvestTool().getName()) || toolTypes.contains("*"));
    }

    private void onBreakSpeed(PlayerEvent.BreakSpeed event, LivingEntity wearer) {
        if (canHarvest(event.getState())) {
            event.setNewSpeed((float) (event.getNewSpeed() + ModConfig.server.diggingClaws.miningSpeedBonus.get()));
        }
    }

    private void onHarvestCheck(PlayerEvent.HarvestCheck event, LivingEntity wearer) {
        if (!event.canHarvest()) {
            int diggingClawsHarvestLevel = ModConfig.server.diggingClaws.harvestLevel.get() - 1;
            event.setCanHarvest(event.getTargetBlock().getHarvestLevel() <= diggingClawsHarvestLevel);
            damageEquippedStacks(wearer);
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    protected ResourceLocation getSlimTexture() {
        return getTexture();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected ClawsModel createModel(boolean smallArms) {
        return new ClawsModel(smallArms);
    }
}
