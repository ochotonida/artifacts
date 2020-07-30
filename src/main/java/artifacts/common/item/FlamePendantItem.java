package artifacts.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class FlamePendantItem extends PendantItem {

    public FlamePendantItem() {
        super("flame_pendant");
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getAmount() >= 1 && event.getSource().getTrueSource() instanceof LivingEntity) {
            if (CuriosApi.getCuriosHelper().findEquippedCurio(this, event.getEntityLiving()).isPresent()) {
                LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
                if (!attacker.isImmuneToFire() && attacker.attackable() && event.getEntityLiving().getRNG().nextFloat() < 0.40F) {
                    attacker.setFire(8);
                    attacker.attackEntityFrom(new EntityDamageSource("onFire", event.getEntity()).setFireDamage(), 2);
                }
            }
        }
    }
}
