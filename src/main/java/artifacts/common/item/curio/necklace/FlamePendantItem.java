package artifacts.common.item.curio.necklace;

import artifacts.common.init.ModGameRules;
import net.minecraft.world.entity.LivingEntity;

public class FlamePendantItem extends PendantItem {

    public FlamePendantItem() {
        super(ModGameRules.FLAME_PENDANT_STRIKE_CHANCE);
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.FLAME_PENDANT_FIRE_DURATION.get() <= 0 || ModGameRules.FLAME_PENDANT_STRIKE_CHANCE.get() <= 0;
    }

    @Override
    protected void applyEffect(LivingEntity target, LivingEntity attacker) {
        if (!attacker.fireImmune() && attacker.attackable()) {
            attacker.setSecondsOnFire(ModGameRules.FLAME_PENDANT_FIRE_DURATION.get());
        }
    }
}
