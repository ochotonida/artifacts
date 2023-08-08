package artifacts.network;

import artifacts.item.wearable.belt.CloudInABottleItem;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Supplier;

public class DoubleJumpPacket {

    public DoubleJumpPacket(FriendlyByteBuf buffer) {

    }

    public DoubleJumpPacket() {

    }

    void encode(FriendlyByteBuf buffer) {

    }

    void apply(Supplier<NetworkManager.PacketContext> context) {
        if (context.get().getPlayer() instanceof ServerPlayer player) {
            context.get().queue(() -> {
                CloudInABottleItem.jump(player);

                for (int i = 0; i < 20; ++i) {
                    double motionX = player.getRandom().nextGaussian() * 0.02;
                    double motionY = player.getRandom().nextGaussian() * 0.02 + 0.20;
                    double motionZ = player.getRandom().nextGaussian() * 0.02;
                    ParticleOptions particleType = player.isInWater() ? ParticleTypes.BUBBLE : ParticleTypes.POOF;
                    player.serverLevel().sendParticles(particleType, player.getX(), player.getY(), player.getZ(), 1, motionX, motionY, motionZ, 0.15);
                }
            });
        }
    }
}
