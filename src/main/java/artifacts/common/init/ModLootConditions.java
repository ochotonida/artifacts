package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.loot.ConfigurableRandomChance;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;

public class ModLootConditions {

    public static final LootItemConditionType CONFIGURABLE_ARTIFACT_CHANCE = new LootItemConditionType(new ConfigurableRandomChance.Serializer());

    public static void register(RegistryEvent<GlobalLootModifierSerializer<?>> event) {
        Registry.register(
                Registry.LOOT_CONDITION_TYPE,
                new ResourceLocation(Artifacts.MODID, "configurable_random_chance"),
                ModLootConditions.CONFIGURABLE_ARTIFACT_CHANCE
        );
    }
}
