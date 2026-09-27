package artifacts.registry;

import artifacts.item.consumeeffects.DamageItemConsumeEffect;
import artifacts.item.consumeeffects.HealConsumeEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

public class ModConsumeEffects {

    public static final Register<ConsumeEffect.Type<?>> CONSUME_EFFECT_TYPES = Register.create(Registries.CONSUME_EFFECT_TYPE);

    public static final RegistryHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<?>> DAMAGE_ITEM = CONSUME_EFFECT_TYPES.register("damage_item", () -> new ConsumeEffect.Type<>(DamageItemConsumeEffect.CODEC, DamageItemConsumeEffect.STREAM_CODEC));
    public static final RegistryHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<?>> HEAL = CONSUME_EFFECT_TYPES.register("heal", () -> new ConsumeEffect.Type<>(HealConsumeEffect.CODEC, HealConsumeEffect.STREAM_CODEC));

}
