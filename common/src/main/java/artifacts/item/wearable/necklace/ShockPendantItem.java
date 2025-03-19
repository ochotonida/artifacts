package artifacts.item.wearable.necklace;

import artifacts.registry.ModGameRules;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ShockPendantItem extends PendantItem {

    public ShockPendantItem() {
        super(ModGameRules.SHOCK_PENDANT_STRIKE_CHANCE, ModGameRules.SHOCK_PENDANT_COOLDOWN);
        EntityEvent.LIVING_HURT.register(this::onLivingHurt);
    }

    @Override
    public boolean hasNonCosmeticEffects() {
        return super.hasNonCosmeticEffects() || ModGameRules.SHOCK_PENDANT_DO_CANCEL_LIGHTNING_DAMAGE.get();
    }

    @Override
    protected void addEffectsTooltip(ItemStack stack, List<MutableComponent> tooltip) {
        if (ModGameRules.SHOCK_PENDANT_STRIKE_CHANCE.get() > 0) {
            tooltip.add(tooltipLine("strike_chance"));
        }
        if (ModGameRules.SHOCK_PENDANT_DO_CANCEL_LIGHTNING_DAMAGE.get()) {
            tooltip.add(tooltipLine("lightning_damage"));
        }
    }

    protected EventResult onLivingHurt(LivingEntity entity, DamageSource damageSource, float amount) {
        if (
                isEquippedBy(entity)
                && !entity.level().isClientSide()
                && ModGameRules.SHOCK_PENDANT_DO_CANCEL_LIGHTNING_DAMAGE.get()
                && amount > 0
                && damageSource.is(DamageTypeTags.IS_LIGHTNING)
        ) {
            return EventResult.interruptFalse();
        }
        return EventResult.pass();
    }

    @Override
    protected void applyEffect(LivingEntity target, LivingEntity attacker) {
        if (attacker.level().canSeeSky(BlockPos.containing(attacker.position()))) {
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(attacker.level());
            if (lightningBolt != null) {
                lightningBolt.moveTo(Vec3.atBottomCenterOf(attacker.blockPosition()));
                lightningBolt.setCause(attacker instanceof ServerPlayer player ? player : null);
                attacker.level().addFreshEntity(lightningBolt);
            }
        }
    }
}
