package artifacts.item.wearable.head;

import artifacts.integration.OriginsCompat;
import artifacts.item.wearable.MobEffectItem;
import artifacts.registry.ModGameRules;
import dev.architectury.platform.Platform;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class SnorkelItem extends MobEffectItem {

    public SnorkelItem() {
        super(MobEffects.WATER_BREATHING, () -> ModGameRules.SNORKEL_ENABLED.get() && ModGameRules.SNORKEL_WATER_BREATHING_DURATION.get() != 0);
    }

    @Override
    protected void addEffectsTooltip(ItemStack stack, List<MutableComponent> tooltip) {
        if (ModGameRules.SNORKEL_IS_INFINITE.get()) {
            tooltip.add(tooltipLine("infinite"));
        } else {
            tooltip.add(tooltipLine("limited"));
        }
    }

    @Override
    protected int getDuration(LivingEntity entity) {
        int duration = ModGameRules.SNORKEL_WATER_BREATHING_DURATION.get();
        if (!ModGameRules.SNORKEL_IS_INFINITE.get()
                && entity instanceof Player
                && entity.getItemBySlot(EquipmentSlot.HEAD).is(Items.TURTLE_HELMET)
                && !isSubmerged(entity)
        ) {
            duration += 200;
        }
        return duration + 19;
    }

    private static boolean isSubmerged(LivingEntity entity) {
        return entity.isEyeInFluid(FluidTags.WATER)
                ^ (Platform.isModLoaded("origins") && !Platform.isModLoaded("forge") && OriginsCompat.hasWaterBreathing(entity));
    }

    @Override
    protected boolean shouldShowIcon() {
        return !ModGameRules.SNORKEL_IS_INFINITE.get();
    }

    @Override
    public boolean isEffectActive(LivingEntity entity) {
        if (!ModGameRules.SNORKEL_IS_INFINITE.get() && isSubmerged(entity)) {
            return false;
        }
        return super.isEffectActive(entity);
    }
}
