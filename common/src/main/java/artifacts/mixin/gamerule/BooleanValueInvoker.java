package artifacts.mixin.gamerule;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.BiConsumer;

@Mixin(GameRules.BooleanValue.class)
public interface BooleanValueInvoker {

    @Invoker("create")
    static GameRules.Type<GameRules.BooleanValue> invokeCreate(boolean defaultValue, BiConsumer<MinecraftServer, GameRules.BooleanValue> onChanged) {
        throw new AssertionError();
    }
}
