package artifacts.common.item.curio.hands;

import artifacts.common.init.ModGameRules;
import artifacts.common.item.curio.CurioItem;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class PocketPistonItem extends CurioItem {

    public PocketPistonItem() {
        addListener(LivingAttackEvent.class, this::onLivingAttack, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        float knockbackBonus = Math.max(0, ModGameRules.POCKET_PISTON_KNOCKBACK_STRENGTH.get() / 10F);
        event.getEntity().knockback(knockbackBonus, Mth.sin((float) (wearer.getYRot() * (Math.PI / 180))), -Mth.cos((float) (wearer.getYRot() * (Math.PI / 180))));
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.PISTON_EXTEND, 1, 1);
    }
}
