package artifacts.mixin.ability.hideseffectparticles;

import artifacts.config.value.Value;
import artifacts.equipment.EquipmentHelper;
import artifacts.registry.ModDataComponents;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @WrapWithCondition(method = "tickEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private boolean shouldShowEffectParticles(Level instance, ParticleOptions particle, double x, double y, double z, double xd, double yd, double zd) {
        LivingEntity self = (LivingEntity) (Object) this;
        // TODO backport componentQuery, filter disabled items & remove isInvisible check
        if (!self.isInvisible()) {
            return true;
        }
        return !EquipmentHelper.reduceEquipment(self, false, (stack, b) -> {
            Value<Boolean> component = stack.get(ModDataComponents.HIDES_EFFECT_PARTICLES.get());
            return b || component != null && component.get();
        });
    }
}
