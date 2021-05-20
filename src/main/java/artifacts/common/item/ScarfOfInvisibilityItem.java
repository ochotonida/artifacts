package artifacts.common.item;

import artifacts.common.config.ModConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ScarfOfInvisibilityItem extends CurioItem {

    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (!ModConfig.server.isCosmetic(this) && !entity.level.isClientSide && entity.tickCount % 15 == 0) {
            entity.addEffect(new EffectInstance(Effects.INVISIBILITY, 39, 0, true, false));
        }
        if (entity.tickCount % 20 == 0) {
            damageStack(identifier, index, entity, stack);
        }
    }
}
