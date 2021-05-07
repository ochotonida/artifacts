package artifacts.common.item;

import artifacts.common.config.ModConfig;
import net.minecraft.entity.LivingEntity;

public class FlamePendantItem extends PendantItem {

    public FlamePendantItem() {
        super("flame_pendant");
    }

    @Override
    protected void applyEffect(LivingEntity target, LivingEntity attacker) {
        if (!attacker.fireImmune() && attacker.attackable()) {
            attacker.setSecondsOnFire(ModConfig.server.flamePendant.fireDuration.get());
        }
    }
}
