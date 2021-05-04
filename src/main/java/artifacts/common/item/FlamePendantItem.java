package artifacts.common.item;

import artifacts.common.config.Config;
import net.minecraft.entity.LivingEntity;

public class FlamePendantItem extends PendantItem {

    public FlamePendantItem() {
        super("flame_pendant");
    }

    @Override
    public void applyEffect(LivingEntity target, LivingEntity attacker) {
        if (!attacker.fireImmune() && attacker.attackable() && target.getRandom().nextDouble() < Config.SERVER.flamePendantConfig.strikeChance) {
            attacker.setSecondsOnFire(Config.SERVER.flamePendantConfig.fireDuration);
        }
    }
}
