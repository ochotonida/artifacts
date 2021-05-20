package artifacts.common.item;

import artifacts.common.config.ModConfig;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class PocketPistonItem extends CurioItem {

    public PocketPistonItem() {
        addListener(LivingAttackEvent.class, this::onLivingAttack, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        float knockbackBonus = (float) (double) ModConfig.server.pocketPiston.knockbackBonus.get();
        event.getEntityLiving().knockback(knockbackBonus, MathHelper.sin((float) (wearer.yRot * (Math.PI / 180))), -MathHelper.cos((float) (wearer.yRot * (Math.PI / 180))));
        damageEquippedStacks(wearer);
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.PISTON_EXTEND, 1, 1);
    }
}
