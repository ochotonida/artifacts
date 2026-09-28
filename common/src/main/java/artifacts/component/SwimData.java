package artifacts.component;

import artifacts.component.ability.SwimInAir;
import artifacts.network.NetworkHandler;
import artifacts.network.payload.UpdateSwimFlyingPacket;
import artifacts.registry.ModDataComponents;
import artifacts.registry.ModSoundEvents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class SwimData {

    public static final MapCodec<SwimData> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("is_swim_flying", false).forGetter(SwimData::isSwimFlying),
            Codec.BOOL.optionalFieldOf("should_break_surface_tension", false).forGetter(swimData -> swimData.shouldBreakSurfaceTension),
            Codec.DOUBLE.optionalFieldOf("swim_flying_charge", 1D).forGetter(SwimData::getSwimFlyingCharge)
    ).apply(instance, SwimData::new));
    public static final Codec<SwimData> CODEC = MAP_CODEC.codec();

    protected boolean isSwimFlying;
    protected boolean shouldBreakSurfaceTension;

    // TODO: might want to sync this to clients when logging in or on laggy servers
    protected double swimFlyingCharge;

    public SwimData() {
        this(false, false, 1);
    }

    public SwimData(boolean isSwimFlying, boolean shouldBreakSurfaceTension, double swimFlyingCharge) {
        this.isSwimFlying = isSwimFlying;
        this.shouldBreakSurfaceTension = shouldBreakSurfaceTension;
        this.swimFlyingCharge = swimFlyingCharge;
    }

    public boolean isSwimFlying() {
        return isSwimFlying;
    }

    public boolean shouldBreakSurfaceTension() {
        return shouldBreakSurfaceTension || isSwimFlying();
    }

    public double getSwimFlyingCharge() {
        return swimFlyingCharge;
    }

    public void update(Player player) {
        if (player.isInWater() || player.isInLava() || player.fallDistance > 6) {
            // prevent players from stepping onto the surface while swimming,
            // or when falling into water from too high
            shouldBreakSurfaceTension = true;
        } else if (player.onGround() || player.getAbilities().flying) {
            // reset surface tension when back on the ground or during creative flight
            shouldBreakSurfaceTension = false;
        }

        // stop swimming automatically when touching the ground,
        // start swimming automatically when swimming underwater
        boolean shouldToggleSwimState = isSwimFlying
                ? player.onGround()
                : player.isUnderWater() && player.isSwimming();

        if (shouldToggleSwimState) {
            toggleSwimFlying(player);
            // send swim state back to client after automatically updating on server
            syncSwimming(player);
        }

        updateSwimProgress(player);
    }

    private void updateSwimProgress(Player player) {
        if (shouldDepleteSwimFlyingCharge(player) && !player.isCreative()) {
            int maxFlightDuration = SwimInAir.getMaxFlightDuration(player);
            swimFlyingCharge -= 1D / maxFlightDuration;
            swimFlyingCharge = Math.max(0, swimFlyingCharge);
            // Stop swimming automatically after depleting charge and send change to client
            if (swimFlyingCharge == 0) {
                toggleSwimFlying(player);
                syncSwimming(player);
            }
        } else if (swimFlyingCharge < 1) {
            int rechargeDuration = SwimInAir.getRechargeDuration(player);
            swimFlyingCharge += 1D / rechargeDuration;
            swimFlyingCharge = Math.min(1, swimFlyingCharge);
        }
    }

    public boolean shouldDepleteSwimFlyingCharge(Player player) {
        return isSwimFlying && (!player.isUnderWater() || ModDataComponents.SINKING.on(player).findAny());
    }

    public void toggleSwimFlying(Player player) {
        if (isSwimFlying || SwimInAir.canSwim(player)) {
            isSwimFlying = !isSwimFlying;
            if (!isSwimFlying && !player.level().isClientSide() && !player.onGround()) {
                if (!player.isSilent()) {
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                            ModSoundEvents.POP.value(), player.getSoundSource(), 1F, 1F
                    );
                }
                // Add minimum 0.25s cooldown to prevent player from immediately entering fly state again
                ModDataComponents.SWIM_IN_AIR.on(player).iterate((ability, slot) ->
                        slot.addCooldown(Math.max(5, ability.cooldown().get() * 20))
                );
            }
        }
    }

    public void syncSwimming(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            NetworkHandler.sendToPlayer(serverPlayer, new UpdateSwimFlyingPacket(isSwimFlying));
        } else {
            NetworkHandler.sendToServer(new UpdateSwimFlyingPacket(isSwimFlying));
        }
    }
}
