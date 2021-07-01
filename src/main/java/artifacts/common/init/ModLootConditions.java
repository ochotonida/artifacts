package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.loot.ConfigurableRandomChance;
import net.minecraft.loot.LootConditionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;

public class ModLootConditions {

    public static final LootConditionType CONFIGURABLE_ARTIFACT_CHANCE = new LootConditionType(new ConfigurableRandomChance.Serializer());

    public static void register(RegistryEvent<GlobalLootModifierSerializer<?>> event) {
        Registry.register(
                Registry.LOOT_CONDITION_TYPE,
                new ResourceLocation(Artifacts.MODID, "configurable_random_chance"),
                ModLootConditions.CONFIGURABLE_ARTIFACT_CHANCE
        );
    }
}
