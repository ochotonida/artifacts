package artifacts.forge.registry;

import artifacts.forge.ArtifactsForge;
import artifacts.forge.loot.ConfigurableRandomChance;
import artifacts.forge.loot.EverlastingBeefChance;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModLootConditions {

    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, ArtifactsForge.MOD_ID);

    public static final RegistryObject<LootItemConditionType> CONFIGURABLE_ARTIFACT_CHANCE = register("configurable_random_chance", ConfigurableRandomChance.Serializer::new);
    public static final RegistryObject<LootItemConditionType> EVERLASTING_BEEF_CHANCE = register("everlasting_beef_chance", EverlastingBeefChance.Serializer::new);

    private static RegistryObject<LootItemConditionType> register(String name, Supplier<Serializer<? extends LootItemCondition>> serializer) {
        return LOOT_CONDITIONS.register(name, () -> new LootItemConditionType(serializer.get()));
    }
}
