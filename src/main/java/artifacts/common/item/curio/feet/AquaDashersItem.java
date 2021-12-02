package artifacts.common.item.curio.feet;

import artifacts.common.capability.SwimHandler;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class AquaDashersItem extends CurioItem {

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().tickCount % 20 == 0 && isSprintingOnFluid(slotContext.entity())) {
            damageStack(slotContext, stack);
        }
    }

    public boolean isSprinting(LivingEntity entity) {
        return isEquippedBy(entity)
                && entity.isSprinting()
                && entity.fallDistance < 6
                && !entity.getCapability(SwimHandler.CAPABILITY).map(SwimHandler::isWet).orElse(true);
    }

    private boolean isSprintingOnFluid(LivingEntity entity) {
        if (isSprinting(entity)) {
            BlockPos pos = new BlockPos(Mth.floor(entity.getX()), Mth.floor(entity.getY() - 0.2), Mth.floor(entity.getZ()));
            return !entity.level.getBlockState(pos).getFluidState().isEmpty();
        }
        return false;
    }
}
