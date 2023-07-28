package artifacts.forge.item.wearable.necklace;

import artifacts.forge.event.ArtifactEventHandler;
import artifacts.registry.ModGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class ShockPendantItem extends PendantItem {

    public ShockPendantItem() {
        super(ModGameRules.SHOCK_PENDANT_STRIKE_CHANCE);
        ArtifactEventHandler.addListener(this, LivingHurtEvent.class, this::onLivingHurt);
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.SHOCK_PENDANT_STRIKE_CHANCE.get() <= 0 && !ModGameRules.SHOCK_PENDANT_DO_CANCEL_LIGHTNING_DAMAGE.get();
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        if (ModGameRules.SHOCK_PENDANT_STRIKE_CHANCE.get() > 0) {
            tooltip.add(tooltipLine("strike_chance"));
        }
        if (ModGameRules.SHOCK_PENDANT_DO_CANCEL_LIGHTNING_DAMAGE.get()) {
            tooltip.add(tooltipLine("lightning_damage"));
        }
    }

    private void onLivingHurt(LivingHurtEvent event, LivingEntity wearer) {
        if (!event.getEntity().level().isClientSide()
                && ModGameRules.SHOCK_PENDANT_DO_CANCEL_LIGHTNING_DAMAGE.get()
                && event.getAmount() > 0
                && event.getSource().is(DamageTypeTags.IS_LIGHTNING)) {
            event.setCanceled(true);
        }
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
