package artifacts.common.item.curio.necklace;

import artifacts.common.config.ModConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class ThornPendantItem extends PendantItem {

    @Override
    protected void applyEffect(LivingEntity target, LivingEntity attacker) {
        if (attacker.attackable()) {
            int minDamage = ModConfig.server.thornPendant.minDamage.get();
            int maxDamage = ModConfig.server.thornPendant.maxDamage.get();
            attacker.hurt(DamageSource.thorns(target), minDamage + target.getRandom().nextInt(maxDamage - minDamage + 1));
        }
    }
}
