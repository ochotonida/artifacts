package artifacts.forge.item.wearable.hands;

import artifacts.forge.event.ArtifactEventHandler;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import artifacts.util.DamageSourceHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class PocketPistonItem extends WearableArtifactItem {

    public PocketPistonItem() {
        ArtifactEventHandler.addListener(this, LivingAttackEvent.class, this::onLivingAttack, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.POCKET_PISTON_KNOCKBACK_STRENGTH.get() <= 0;
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        float knockbackBonus = Math.max(0, ModGameRules.POCKET_PISTON_KNOCKBACK_STRENGTH.get() / 10F);
        event.getEntity().knockback(knockbackBonus, Mth.sin((float) (wearer.getYRot() * (Math.PI / 180))), -Mth.cos((float) (wearer.getYRot() * (Math.PI / 180))));
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.PISTON_EXTEND;
    }
}
