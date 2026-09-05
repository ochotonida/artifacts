package artifacts.component.ability;

import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.registry.ModDataComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DeathProtection;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

public record EquipableTotem(Value<Boolean> enabled) implements EquipmentAbility {

    public static final Codec<EquipableTotem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ValueTypes.enabledField().forGetter(EquipableTotem::enabled)
    ).apply(instance, EquipableTotem::new));

    public static final StreamCodec<ByteBuf, EquipableTotem> STREAM_CODEC = ValueTypes.BOOLEAN.streamCodec()
            .map(EquipableTotem::new, EquipableTotem::enabled);

    public static @Nullable ItemStack findTotem(LivingEntity entity) {
        AtomicReference<@Nullable ItemStack> totem = new AtomicReference<>();
        ModDataComponents.EQUIPABLE_TOTEM.on(entity).iterate((_, slot) -> {
            if (totem.get() == null && slot.get().has(DataComponents.DEATH_PROTECTION)) {
                totem.set(slot.get());
            }
        });
        return totem.get();
    }

    @Override
    public boolean isNonCosmetic() {
        return enabled().get();
    }

    @Override
    public void addToTooltip(TooltipWriter writer) {
        DeathProtection deathProtection = writer.components().get(DataComponents.DEATH_PROTECTION);
        if (deathProtection != null) {
            boolean causesTeleport = deathProtection.deathEffects()
                    .stream()
                    .anyMatch(consumeEffect -> consumeEffect.getType() == ConsumeEffect.Type.TELEPORT_RANDOMLY);
            if (causesTeleport) {
                writer.add("teleport");
            }
        }
    }
}
