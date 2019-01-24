package artifacts.common.network;

import artifacts.common.ModSoundEvents;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketBottledCloudJump implements IMessage {

    boolean isFart;

    @SuppressWarnings("unused")
    public PacketBottledCloudJump() { }

    public PacketBottledCloudJump(boolean isFart) {
        this.isFart = isFart;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        isFart = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(isFart);
    }

    public static class PacketHandler implements IMessageHandler<PacketBottledCloudJump, IMessage> {

        @Override
        public IMessage onMessage(PacketBottledCloudJump message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            player.getServerWorld().addScheduledTask(() -> {
                player.fallDistance = 0;
                player.jump();
                if (message.isFart) {
                    player.playSound(ModSoundEvents.FART, 1, 0.9F + player.getRNG().nextFloat() * 0.2F);
                } else {
                    player.playSound(SoundEvents.BLOCK_CLOTH_FALL, 1, 0.9F + player.getRNG().nextFloat() * 0.2F);
                }
            });

            return null;
        }
    }
}
