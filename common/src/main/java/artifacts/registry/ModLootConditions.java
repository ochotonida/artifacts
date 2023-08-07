package artifacts.registry;

import artifacts.Artifacts;
import artifacts.loot.ConfigurableRandomChance;
import artifacts.loot.EverlastingBeefChance;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.function.Supplier;

public class ModLootConditions {

    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister.create(Artifacts.MOD_ID, Registries.LOOT_CONDITION_TYPE);

    public static final RegistrySupplier<LootItemConditionType> CONFIGURABLE_ARTIFACT_CHANCE = register("configurable_random_chance", ConfigurableRandomChance.Serializer::new);
    public static final RegistrySupplier<LootItemConditionType> EVERLASTING_BEEF_CHANCE = register("everlasting_beef_chance", EverlastingBeefChance.Serializer::new);

    private static RegistrySupplier<LootItemConditionType> register(String name, Supplier<Serializer<? extends LootItemCondition>> serializer) {
        return LOOT_CONDITIONS.register(name, () -> new LootItemConditionType(serializer.get()));
    }
}
