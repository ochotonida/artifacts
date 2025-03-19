package artifacts.registry;

import artifacts.Artifacts;
import artifacts.loot.ArtifactRarityAdjustedChance;
import artifacts.loot.ConfigValueChance;
import artifacts.loot.IsAprilFools;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.function.Supplier;

public class ModLootConditions {

    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister.create(Artifacts.MOD_ID, Registries.LOOT_CONDITION_TYPE);

    public static final RegistrySupplier<LootItemConditionType> ARTIFACT_RARITY_ADJUSTED_CHANCE = register("artifact_rarity_adjusted_chance", ArtifactRarityAdjustedChance.Serializer::new);
    public static final RegistrySupplier<LootItemConditionType> CONFIG_VALUE_CHANCE = register("config_value_chance", ConfigValueChance.Serializer::new);
    public static final RegistrySupplier<LootItemConditionType> IS_APRIL_FOOLS = register("is_april_fools", IsAprilFools.Serializer::new);

    private static RegistrySupplier<LootItemConditionType> register(String name, Supplier<Serializer<? extends LootItemCondition>> serializer) {
        return RegistrySupplier.of(LOOT_CONDITIONS.register(name, () -> new LootItemConditionType(serializer.get())));
    }
}
