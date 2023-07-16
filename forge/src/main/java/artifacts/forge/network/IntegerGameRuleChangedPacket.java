package artifacts.forge.network;

import artifacts.forge.registry.ModGameRules;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class IntegerGameRuleChangedPacket {

    private final String key;
    private final int value;

    @SuppressWarnings("unused")
    public IntegerGameRuleChangedPacket(FriendlyByteBuf buffer) {
        key = buffer.readUtf();
        value = buffer.readInt();
    }

    public IntegerGameRuleChangedPacket(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @SuppressWarnings("unused")
    void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(key);
        buffer.writeInt(value);
    }

    void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ModGameRules.updateValue(key, value));
        context.get().setPacketHandled(true);
    }
}
