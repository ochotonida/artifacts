package artifacts.common.item.curio.hands;

import artifacts.common.config.ModConfig;
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
            int maxHealthAbsorbed = ModConfig.server.vampiricGlove.maxHealthAbsorbed.get();
            float absorptionRatio = (float) (double) ModConfig.server.vampiricGlove.absorptionRatio.get();

            float damageDealt = Math.min(event.getAmount(), event.getEntityLiving().getHealth());
            float damageAbsorbed = Math.min(maxHealthAbsorbed, absorptionRatio * damageDealt);
            if (damageAbsorbed >= 1) {
                wearer.heal(damageAbsorbed);
            }

            damageEquippedStacks(wearer);
        }
    }
}
