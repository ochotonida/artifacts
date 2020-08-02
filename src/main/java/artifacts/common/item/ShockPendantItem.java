package artifacts.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.CuriosAPI;

public class ShockPendantItem extends PendantItem {

    public ShockPendantItem() {
        super("shock_pendant");
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getAmount() >= 1) {
            if (event.getSource() == DamageSource.LIGHTNING_BOLT && CuriosAPI.getCurioEquipped(this, event.getEntityLiving()).isPresent()) {
                event.setCanceled(true);
            } else if (event.getSource().getTrueSource() instanceof LivingEntity) {
                if (CuriosAPI.getCurioEquipped(this, event.getEntityLiving()).isPresent()) {
                    LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
                    if (attacker.world.canSeeSky(new BlockPos(attacker.getPositionVec())) && event.getEntityLiving().getRNG().nextFloat() < 0.25F) {
                        LightningBoltEntity lightningBolt = new LightningBoltEntity(attacker.world, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), false);
                        lightningBolt.setCaster(attacker instanceof ServerPlayerEntity ? (ServerPlayerEntity) attacker : null);
                        attacker.world.addEntity(lightningBolt);
                    }
                }
            }
        }
    }
}
