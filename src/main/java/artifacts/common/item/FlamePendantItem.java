package artifacts.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityDamageSource;

public class FlamePendantItem extends PendantItem {

    public FlamePendantItem() {
        super("flame_pendant");
    }

    @Override
    public void applyEffect(LivingEntity target, LivingEntity attacker) {
        if (!attacker.fireImmune() && attacker.attackable() && target.getRandom().nextFloat() < 0.40F) {
            attacker.setSecondsOnFire(8);
            attacker.hurt(new EntityDamageSource("onFire", target).setIsFire(), 2);
        }
    }
}
