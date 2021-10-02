package artifacts.common.item.curio.head;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class SnorkelItem extends CurioItem {

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!ModConfig.server.isCosmetic(this) && !slotContext.entity().level.isClientSide && slotContext.entity().tickCount % 15 == 0) {
            slotContext.entity().addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 39, 0, true, false));
        }
        if (slotContext.entity().tickCount % 20 == 0 && slotContext.entity().isEyeInFluid(FluidTags.WATER)) {
            damageStack(slotContext, stack);
        }
    }
}
