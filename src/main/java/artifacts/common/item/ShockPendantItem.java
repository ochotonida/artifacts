package artifacts.common.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class ShockPendantItem extends PendantItem {

    public ShockPendantItem() {
        super("shock_pendant");
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getAmount() >= 1) {
            if (event.getSource() == DamageSource.LIGHTNING_BOLT && CuriosApi.getCuriosHelper().findEquippedCurio(this, event.getEntityLiving()).isPresent()) {
                event.setCanceled(true);
            } else if (event.getSource().getTrueSource() instanceof LivingEntity) {
                if (CuriosApi.getCuriosHelper().findEquippedCurio(this, event.getEntityLiving()).isPresent()) {
                    LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
                    if (attacker.world.canSeeSky(new BlockPos(attacker.getPositionVec())) && event.getEntityLiving().getRNG().nextFloat() < 0.25F) {
                        LightningBoltEntity lightningBolt = EntityType.LIGHTNING_BOLT.create(attacker.world);
                        if (lightningBolt != null) {
                            lightningBolt.moveForced(Vector3d.copyCenteredHorizontally(attacker.getPosition()));
                            lightningBolt.setCaster(attacker instanceof ServerPlayerEntity ? (ServerPlayerEntity) attacker : null);
                            attacker.world.addEntity(lightningBolt);
                        }
                    }
                }
            }
        }
    }
}
