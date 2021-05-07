package artifacts.common.network;

import artifacts.common.init.ModItems;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class DoubleJumpPacket {

    @SuppressWarnings("unused")
    public DoubleJumpPacket(PacketBuffer buffer) {
    }

    public DoubleJumpPacket() {
    }

    @SuppressWarnings("unused")
    void encode(PacketBuffer buffer) {
    }

    void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        if (player != null) {
            context.get().enqueueWork(() -> {
                ModItems.CLOUD_IN_A_BOTTLE.get().jump(player);

                for (int i = 0; i < 20; ++i) {
                    double motionX = player.getRandom().nextGaussian() * 0.02;
                    double motionY = player.getRandom().nextGaussian() * 0.02 + 0.20;
                    double motionZ = player.getRandom().nextGaussian() * 0.02;
                    player.getLevel().sendParticles(ParticleTypes.POOF, player.getX(), player.getY(), player.getZ(), 1, motionX, motionY, motionZ, 0.15);
                }
            });
        }
        context.get().setPacketHandled(true);
    }
}
