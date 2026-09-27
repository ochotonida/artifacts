package artifacts.mixin.ability.nightvision.client;

import artifacts.registry.ModDataComponents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Supplier;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @ModifyReturnValue(method = "nightVisionScale", at = @At("RETURN"))
    private static float getNightVisionScale(float original, LivingEntity camera, float a) {
        MobEffectInstance effect = camera.getEffect(MobEffects.NIGHT_VISION);
        if (effect == null || !effect.endsWithin(12 * 20)) {
            return original;
        }
        double scale = ModDataComponents.REDUCED_NIGHT_VISION.on(camera)
                .includeInactive()
                .maxDouble(Supplier::get);
        if (scale == 0) {
            return original;
        }
        return Mth.lerp(Math.max(0, effect.getDuration() - a - 11 * 20) / (12 * 20 - 11 * 20), (float) scale, original);
    }
}
