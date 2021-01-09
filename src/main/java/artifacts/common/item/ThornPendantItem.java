package artifacts.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public class ThornPendantItem extends PendantItem {

    public ThornPendantItem() {
        super("thorn_pendant");
    }

    @Override
    public void applyEffect(LivingEntity target, LivingEntity attacker) {
        if (attacker.attackable() && random.nextFloat() < 0.5F) {
            attacker.attackEntityFrom(DamageSource.causeThornsDamage(target), 2 + target.getRNG().nextInt(5));
        }
    }
}
