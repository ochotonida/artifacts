package artifacts.component;

import artifacts.component.ability.SwimInAir;
import artifacts.network.NetworkHandler;
import artifacts.network.payload.ToggleSwimFlyingPacket;
import artifacts.network.payload.UpdateSwimDataPacket;
import artifacts.registry.ModDataComponents;
import artifacts.registry.ModSoundEvents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class SwimData {

    public static final MapCodec<SwimData> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("is_swim_flying", false).forGetter(SwimData::isSwimFlying),
            Codec.BOOL.optionalFieldOf("should_break_surface_tension", false).forGetter(swimData -> swimData.shouldBreakSurfaceTension),
            Codec.DOUBLE.optionalFieldOf("swim_flying_charge", 1D).forGetter(SwimData::getSwimFlyingCharge)
    ).apply(instance, SwimData::new));
    public static final Codec<SwimData> CODEC = MAP_CODEC.codec();

    public static final StreamCodec<FriendlyByteBuf, SwimData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            SwimData::isSwimFlying,
            ByteBufCodecs.BOOL,
            swimData -> swimData.shouldBreakSurfaceTension,
            ByteBufCodecs.DOUBLE,
            SwimData::getSwimFlyingCharge,
            SwimData::new
    );

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

    public void update(SwimData other) {
        this.isSwimFlying = other.isSwimFlying;
        this.shouldBreakSurfaceTension = other.shouldBreakSurfaceTension;
        this.swimFlyingCharge = other.swimFlyingCharge;
    }

    public boolean isSwimFlying() {
        return isSwimFlying;
    }

    public boolean shouldBreakSurfaceTension() {
        // players don't collide with the fluid surface while swim flying,
        // but aren't prevented from running on the surface afterward if they didn't touch water during their flight
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
        }

        if (updateSwimProgress(player) || shouldToggleSwimState) {
            // send swim state back to the client if it was changed on the server
            syncToClient(player);
        }
    }

    private boolean updateSwimProgress(Player player) {
        if (shouldDepleteSwimFlyingCharge(player) && !player.isCreative()) {
            depleteSwimFlyingCharge(SwimInAir.getMaxFlightDuration(player));
        } else if (swimFlyingCharge < 1) {
            rechargeSwimFlyingCharge(SwimInAir.getRechargeDuration(player));
        } else {
            // No changes need to be sent to the client
            return false;
        }

        // Stop swimming automatically after depleting charge
        if (swimFlyingCharge == 0) {
            toggleSwimFlying(player);
        }

        return true;
    }

    private void depleteSwimFlyingCharge(int maxFlightDuration) {
        swimFlyingCharge -= 1D / maxFlightDuration;
        swimFlyingCharge = Math.max(0, swimFlyingCharge);
    }

    private void rechargeSwimFlyingCharge(int rechargeDuration) {
        swimFlyingCharge += 1D / rechargeDuration;
        swimFlyingCharge = Math.min(1, swimFlyingCharge);
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

    public void syncToServer(Player player) {
        if (player.level().isClientSide()) {
            NetworkHandler.sendToServer(new ToggleSwimFlyingPacket(isSwimFlying));
        }
    }

    private void syncToClient(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            NetworkHandler.sendToPlayer(serverPlayer, new UpdateSwimDataPacket(this));
        }
    }
}
