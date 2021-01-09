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
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getAmount() >= 1 && event.getSource() == DamageSource.LIGHTNING_BOLT && isEquippedBy(event.getEntityLiving())) {
            event.setCanceled(true);
        }
        super.onLivingHurt(event);
    }

    @Override
    public void applyEffect(LivingEntity target, LivingEntity attacker) {
        if (attacker.world.canSeeSky(new BlockPos(attacker.getPositionVec())) && target.getRNG().nextFloat() < 0.25F) {
            LightningBoltEntity lightningBolt = EntityType.LIGHTNING_BOLT.create(attacker.world);
            if (lightningBolt != null) {
                lightningBolt.moveForced(Vector3d.copyCenteredHorizontally(attacker.getPosition()));
                lightningBolt.setCaster(attacker instanceof ServerPlayerEntity ? (ServerPlayerEntity) attacker : null);
                attacker.world.addEntity(lightningBolt);
            }
        }
    }
}
