package artifacts.common.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ShockPendantItem extends PendantItem {

    public ShockPendantItem() {
        super("shock_pendant");
        addListener(LivingHurtEvent.class, this::onLivingHurt);
    }

    private void onLivingHurt(LivingHurtEvent event, LivingEntity wearer) {
        if (!event.getEntity().level.isClientSide
                && event.getAmount() >= 1
                && event.getSource() == DamageSource.LIGHTNING_BOLT) {
            event.setCanceled(true);
        }
    }

    @Override
    protected void applyEffect(LivingEntity target, LivingEntity attacker) {
        if (attacker.level.canSeeSky(new BlockPos(attacker.position()))) {
            LightningBoltEntity lightningBolt = EntityType.LIGHTNING_BOLT.create(attacker.level);
            if (lightningBolt != null) {
                lightningBolt.moveTo(Vector3d.atBottomCenterOf(attacker.blockPosition()));
                lightningBolt.setCause(attacker instanceof ServerPlayerEntity ? (ServerPlayerEntity) attacker : null);
                attacker.level.addFreshEntity(lightningBolt);
            }
        }
    }
}
