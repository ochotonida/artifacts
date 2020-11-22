package artifacts.common.network;

import artifacts.common.init.Items;
import artifacts.common.item.CloudInABottleItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

public class DoubleJumpPacket {

    public DoubleJumpPacket(PacketBuffer buffer) {
    }

    public DoubleJumpPacket() {
    }

    void encode(PacketBuffer buffer) {
    }

    void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        if (player != null) {
            context.get().enqueueWork(() -> {
                player.fallDistance = 0;
                CloudInABottleItem.jump(player);
                if (CuriosApi.getCuriosHelper().findEquippedCurio(Items.WHOOPEE_CUSHION, player).isPresent()) {
                    player.playSound(artifacts.common.init.SoundEvents.FART, 1, 0.9F + player.getRNG().nextFloat() * 0.2F);
                } else {
                    player.playSound(SoundEvents.BLOCK_WOOL_FALL, 1, 0.9F + player.getRNG().nextFloat() * 0.2F);
                }
                for (int i = 0; i < 20; ++i) {
                    double motionX = player.getRNG().nextGaussian() * 0.02;
                    double motionY = player.getRNG().nextGaussian() * 0.02 + 0.20;
                    double motionZ = player.getRNG().nextGaussian() * 0.02;
                    player.getServerWorld().spawnParticle(ParticleTypes.POOF, player.getPosX(), player.getPosY(), player.getPosZ(), 1, motionX, motionY, motionZ, 0.15);
                }
            });
        }
        context.get().setPacketHandled(true);
    }
}
