package artifacts.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityDamageSource;

public class FlamePendantItem extends PendantItem {

    public FlamePendantItem() {
        super("flame_pendant");
    }

    @Override
    public void applyEffect(LivingEntity target, LivingEntity attacker) {
        if (!attacker.isImmuneToFire() && attacker.attackable() && target.getRNG().nextFloat() < 0.40F) {
            attacker.setFire(8);
            attacker.attackEntityFrom(new EntityDamageSource("onFire", target).setFireDamage(), 2);
        }
    }
}
