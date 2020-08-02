package artifacts.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.CuriosAPI;

public class ThornPendantItem extends PendantItem {

    public ThornPendantItem() {
        super("thorn_pendant");
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getAmount() >= 1 && event.getSource().getTrueSource() instanceof LivingEntity) {
            if (CuriosAPI.getCurioEquipped(this, event.getEntityLiving()).isPresent()) {
                LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
                if (attacker.attackable() && random.nextFloat() < 0.5F) {
                    attacker.attackEntityFrom(DamageSource.causeThornsDamage(event.getEntity()), 2 + event.getEntityLiving().getRNG().nextInt(5));
                }
            }
        }
    }
}
