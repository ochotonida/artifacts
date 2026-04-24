package artifacts.loot;

import artifacts.Artifacts;
import artifacts.registry.ModLootConditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.function.Supplier;

public record ConfigValueChance(ChanceConfig chanceConfig) implements LootItemCondition {

    public static final MapCodec<ConfigValueChance> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(ChanceConfig.CODEC.fieldOf("config").forGetter(c -> c.chanceConfig))
                    .apply(instance, ConfigValueChance::new)
    );

    @Override
    public LootItemConditionType getType() {
        return ModLootConditions.CONFIG_VALUE_CHANCE.value();
    }

    @Override
    public boolean test(LootContext context) {
        return context.getRandom().nextDouble() < chanceConfig.value.get();
    }

    public static LootItemCondition.Builder archaeologyChance() {
        return () -> new ConfigValueChance(ChanceConfig.ARCHAEOLOGY);
    }

    public static LootItemCondition.Builder entityEquipmentChance() {
        return () -> new ConfigValueChance(ChanceConfig.ENTITY_EQUIPMENT);
    }

    public static LootItemCondition.Builder everlastingBeefChance() {
        return () -> new ConfigValueChance(ChanceConfig.EVERLASTING_BEEF);
    }

    private enum ChanceConfig implements StringRepresentable {
        ARCHAEOLOGY("archaeology", Artifacts.CONFIG.general.archaeologyChance),
        ENTITY_EQUIPMENT("entity_equipment", Artifacts.CONFIG.general.entityEquipmentChance),
        EVERLASTING_BEEF("everlasting_beef", Artifacts.CONFIG.items.everlastingBeef.dropRate);

        private static final Codec<ChanceConfig> CODEC = StringRepresentable.fromEnum(ChanceConfig::values);

        final String name;
        final Supplier<Double> value;

        ChanceConfig(String name, Supplier<Double> value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
