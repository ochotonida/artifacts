package artifacts.registry;

import artifacts.loot.ArtifactRarityAdjustedChance;
import artifacts.loot.ConfigValueChance;
import artifacts.loot.ConfigValueCondition;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ModLootConditions {

    public static final Register<MapCodec<? extends LootItemCondition>> LOOT_CONDITION_TYPES = Register.create(Registries.LOOT_CONDITION_TYPE);

    public static final RegistryHolder<MapCodec<? extends LootItemCondition>, MapCodec<? extends LootItemCondition>> ARTIFACT_RARITY_ADJUSTED_CHANCE = LOOT_CONDITION_TYPES.register("artifact_rarity_adjusted_chance", () -> ArtifactRarityAdjustedChance.CODEC);
    public static final RegistryHolder<MapCodec<? extends LootItemCondition>, MapCodec<? extends LootItemCondition>> CONFIG_VALUE_CHANCE = LOOT_CONDITION_TYPES.register("config_value_chance", () -> ConfigValueChance.CODEC);
    public static final RegistryHolder<MapCodec<? extends LootItemCondition>, MapCodec<? extends LootItemCondition>> CONFIG_VALUE = LOOT_CONDITION_TYPES.register("config_value", () -> ConfigValueCondition.CODEC);

}
