package artifacts.mixin.ability.equipabletotem;

import artifacts.component.ability.EquipableTotem;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DeathProtection;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "checkTotemDeathProtection", at = @At("HEAD"), cancellable = true)
    private void checkTotemDeathProtection(DamageSource killingDamage, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

        // finds the first equipped totem with the equipable_totem component
        ItemStack totem = EquipableTotem.findTotem(self);

        // identical to the remainder of the logic in checkTotemDeathProtection
        // injecting on the variable assignments instead of HEAD might simplify this somewhat
        if (!killingDamage.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && totem != null) {
            DeathProtection deathProtection = Objects.requireNonNull(totem.get(DataComponents.DEATH_PROTECTION));
            ItemStack copy = totem.copy();
            totem.shrink(1);

            if (self instanceof ServerPlayer serverPlayer) {
                serverPlayer.awardStat(Stats.ITEM_USED.get(copy.getItem()));
                CriteriaTriggers.USED_TOTEM.trigger(serverPlayer, copy);
                copy.causeUseVibration(self, GameEvent.ITEM_INTERACT_FINISH);
            }

            self.setHealth(1F);
            deathProtection.applyEffects(copy, self);
            self.level().broadcastEntityEvent(self, EntityEvent.PROTECTED_FROM_DEATH);

            cir.setReturnValue(true); // early return intended!
        }
    }
}
