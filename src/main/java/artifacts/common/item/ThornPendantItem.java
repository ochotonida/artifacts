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
            attacker.hurt(DamageSource.thorns(target), 2 + target.getRandom().nextInt(5));
        }
    }
}
