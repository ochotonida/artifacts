package artifacts.network;

import artifacts.registry.ModGameRules;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;

public class IntegerGameRuleChangedPacket {

    private final String key;
    private final int value;

    public IntegerGameRuleChangedPacket(FriendlyByteBuf buffer) {
        key = buffer.readUtf();
        value = buffer.readInt();
    }

    public IntegerGameRuleChangedPacket(String key, int value) {
        this.key = key;
        this.value = value;
    }

    void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(key);
        buffer.writeInt(value);
    }

    void apply(Supplier<NetworkManager.PacketContext> context) {
        context.get().queue(() -> ModGameRules.updateValue(key, value));
    }
}
