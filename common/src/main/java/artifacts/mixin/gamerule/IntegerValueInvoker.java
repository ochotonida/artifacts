package artifacts.mixin.gamerule;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.BiConsumer;

@Mixin(GameRules.IntegerValue.class)
public interface IntegerValueInvoker {

    @Invoker("create")
    static GameRules.Type<GameRules.IntegerValue> invokeCreate(int defaultValue, BiConsumer<MinecraftServer, GameRules.IntegerValue> onChanged) {
        throw new AssertionError();
    }
}
