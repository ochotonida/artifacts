package artifacts.mixin.accessors.client;

import net.minecraft.client.gui.Hud;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Hud.class)
public interface HudAccessor {

    @Accessor
    int getTickCount();

    @Accessor
    RandomSource getRandom();

}
