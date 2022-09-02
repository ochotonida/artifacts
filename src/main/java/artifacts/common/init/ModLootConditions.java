package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.loot.ConfigurableRandomChance;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModLootConditions {

    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister.create(Registry.LOOT_ITEM_REGISTRY, Artifacts.MODID);

    public static final RegistryObject<LootItemConditionType> CONFIGURABLE_ARTIFACT_CHANCE = LOOT_CONDITIONS.register("configurable_random_chance", () -> new LootItemConditionType(new ConfigurableRandomChance.Serializer()));
}
