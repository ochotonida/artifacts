package artifacts.item.wearable.hands;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import artifacts.util.DamageSourceHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class VampiricGloveItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.VAMPIRIC_GLOVE_ABSORPTION_RATIO.get() <= 0 || ModGameRules.VAMPIRIC_GLOVE_MAX_HEALING_PER_HIT.get() <= 0;
    }

    public static void onLivingDamage(LivingEntity entity, DamageSource damageSource, float amount) {
        LivingEntity attacker = DamageSourceHelper.getAttacker(damageSource);
        if (
                attacker != null
                        && ModItems.VAMPIRIC_GLOVE.get().isEquippedBy(attacker)
                        && DamageSourceHelper.isMeleeAttack(damageSource)
        ) {
            int maxHealthAbsorbed = ModGameRules.VAMPIRIC_GLOVE_MAX_HEALING_PER_HIT.get();
            float absorptionRatio = ModGameRules.VAMPIRIC_GLOVE_ABSORPTION_RATIO.get() / 100F;

            float damageDealt = Math.min(amount, entity.getHealth());
            float damageAbsorbed = Math.min(maxHealthAbsorbed, absorptionRatio * damageDealt);

            if (damageAbsorbed > 0) {
                attacker.heal(damageAbsorbed);
            }
        }
    }
}
