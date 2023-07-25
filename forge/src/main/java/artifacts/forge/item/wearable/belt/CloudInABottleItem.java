package artifacts.forge.item.wearable.belt;

import artifacts.forge.item.wearable.WearableArtifactItem;
import artifacts.forge.registry.ModGameRules;
import artifacts.forge.registry.ModItems;
import artifacts.registry.ModSoundEvents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CloudInABottleItem extends WearableArtifactItem {

    public CloudInABottleItem() {
        addListener(EventPriority.HIGHEST, LivingFallEvent.class, this::onLivingFall);
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.CLOUD_IN_A_BOTTLE_ENABLED.get();
    }

    public static void jump(Player player) {
        player.fallDistance = 0;

        double upwardsMotion = 0.5;
        if (player.hasEffect(MobEffects.JUMP)) {
            // noinspection ConstantConditions
            upwardsMotion += 0.1 * (player.getEffect(MobEffects.JUMP).getAmplifier() + 1);
        }
        upwardsMotion *= player.isSprinting() ? 1 + Math.max(0, ModGameRules.CLOUD_IN_A_BOTTLE_SPRINT_JUMP_VERTICAL_VELOCITY.get() / 100D) : 1;

        Vec3 motion = player.getDeltaMovement();
        double motionMultiplier = player.isSprinting() ? Math.max(0, ModGameRules.CLOUD_IN_A_BOTTLE_SPRINT_JUMP_HORIZONTAL_VELOCITY.get() / 100D) : 0;
        float direction = (float) (player.getYRot() * Math.PI / 180);
        player.setDeltaMovement(player.getDeltaMovement().add(
                -Mth.sin(direction) * motionMultiplier,
                upwardsMotion - motion.y,
                Mth.cos(direction) * motionMultiplier)
        );

        player.hasImpulse = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(player);

        player.awardStat(Stats.JUMP);
        if (player.isSprinting()) {
            player.causeFoodExhaustion(0.2F);
        } else {
            player.causeFoodExhaustion(0.05F);
        }

        if (CuriosApi.getCuriosHelper().findFirstCurio(player, ModItems.WHOOPEE_CUSHION.get()).isPresent()) {
            player.playSound(ModSoundEvents.FART.get(), 1, 0.9F + player.getRandom().nextFloat() * 0.2F);
        } else {
            player.playSound(SoundEvents.WOOL_FALL, 1, 0.9F + player.getRandom().nextFloat() * 0.2F);
        }
    }

    private void onLivingFall(LivingFallEvent event, LivingEntity wearer) {
        if (ModGameRules.CLOUD_IN_A_BOTTLE_ENABLED.get()) {
            event.setDistance(Math.max(0, event.getDistance() - 3));
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 1, 1);
    }
}
