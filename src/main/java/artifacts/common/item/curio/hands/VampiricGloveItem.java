package artifacts.common.item.curio.hands;

import artifacts.common.init.ModGameRules;
import artifacts.common.item.curio.CurioItem;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class VampiricGloveItem extends CurioItem {

    public VampiricGloveItem() {
        addListener(EventPriority.LOWEST, LivingDamageEvent.class, this::onLivingDamage, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    private void onLivingDamage(LivingDamageEvent event, LivingEntity wearer) {
        if (DamageSourceHelper.isMeleeAttack(event.getSource())) {
            int maxHealthAbsorbed = ModGameRules.VAMPIRIC_GLOVE_MAX_HEALING_PER_HIT.get();
            float absorptionRatio = ModGameRules.VAMPIRIC_GLOVE_ABSORPTION_RATIO.get() / 100F;

            float damageDealt = Math.min(event.getAmount(), event.getEntity().getHealth());
            float damageAbsorbed = Math.min(maxHealthAbsorbed, absorptionRatio * damageDealt);

            if (damageAbsorbed > 0) {
                wearer.heal(damageAbsorbed);
            }
        }
    }
}
