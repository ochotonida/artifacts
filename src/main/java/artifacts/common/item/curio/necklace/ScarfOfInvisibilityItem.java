package artifacts.common.item.curio.necklace;

import artifacts.common.init.ModGameRules;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class ScarfOfInvisibilityItem extends CurioItem {

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.SCARF_OF_INVISIBILITY_ENABLED.get();
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (ModGameRules.SCARF_OF_INVISIBILITY_ENABLED.get() && !slotContext.entity().level.isClientSide && slotContext.entity().tickCount % 15 == 0) {
            slotContext.entity().addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 39, 0, true, false));
        }
    }
}
