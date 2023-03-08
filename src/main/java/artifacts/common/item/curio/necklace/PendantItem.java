package artifacts.common.item.curio.necklace;

import artifacts.common.item.curio.CurioItem;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.function.Supplier;

public abstract class PendantItem extends CurioItem {

    private final Supplier<Integer> strikeChance;

    public PendantItem(Supplier<Integer> strikeChance) {
        addListener(LivingAttackEvent.class, this::onLivingAttack);
        this.strikeChance = strikeChance;
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        LivingEntity attacker = DamageSourceHelper.getAttacker(event.getSource());
        if (!wearer.level.isClientSide()
                && event.getAmount() >= 1
                && attacker != null
                && wearer.getRandom().nextDouble() < strikeChance.get() / 100D) {
            applyEffect(wearer, attacker);
        }
    }

    protected abstract void applyEffect(LivingEntity target, LivingEntity attacker);

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND, 1, 1);
    }
}
