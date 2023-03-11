package artifacts.common.item.curio;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;
import java.util.function.Supplier;

public class MobEffectItem extends CurioItem {

    private final MobEffect mobEffect;
    private final int duration;
    protected final Supplier<Boolean> isEnabled;

    public MobEffectItem(MobEffect mobEffect, Supplier<Boolean> isEnabled) {
        this(mobEffect, 40, isEnabled);
    }

    public MobEffectItem(MobEffect mobEffect, int duration, Supplier<Boolean> isEnabled) {
        this.mobEffect = mobEffect;
        this.duration = duration;
        this.isEnabled = isEnabled;
    }

    @Override
    protected boolean isCosmetic() {
        return !isEnabled.get();
    }

    public boolean isEffectActive(LivingEntity entity) {
        if (!isEnabled.get()) {
            return false;
        }
        Optional<SlotResult> curio = CuriosApi.getCuriosHelper().findFirstCurio(entity, this);
        return curio.isPresent() && isActivated(curio.get().stack());
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (isEffectActive(slotContext.entity()) && !slotContext.entity().level.isClientSide()) {
            slotContext.entity().addEffect(new MobEffectInstance(mobEffect, duration - 1, 0, true, false));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (isEnabled.get() && !slotContext.entity().level.isClientSide()) {
            MobEffectInstance effectInstance = slotContext.entity().getEffect(mobEffect);
            if (effectInstance != null && effectInstance.getAmplifier() == 0 && !effectInstance.isVisible() && effectInstance.getDuration() < duration) {
                slotContext.entity().removeEffect(mobEffect);
            }
        }
    }
}
