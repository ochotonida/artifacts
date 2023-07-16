package artifacts.forge.loot;

import artifacts.forge.ArtifactsForge;
import artifacts.forge.registry.ModLootConditions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class EverlastingBeefChance  implements LootItemCondition {

    private static final EverlastingBeefChance INSTANCE = new EverlastingBeefChance();

    private EverlastingBeefChance() {

    }

    public LootItemConditionType getType() {
        return ModLootConditions.EVERLASTING_BEEF_CHANCE.get();
    }

    public boolean test(LootContext context) {
        return context.getRandom().nextDouble() < ArtifactsForge.CONFIG.common.getEverlastingBeefChance();
    }

    public static LootItemCondition.Builder everlastingBeefChance() {
        return () -> INSTANCE;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<EverlastingBeefChance> {

        public void serialize(JsonObject object, EverlastingBeefChance condition, JsonSerializationContext context) {

        }

        public EverlastingBeefChance deserialize(JsonObject object, JsonDeserializationContext context) {
            return INSTANCE;
        }
    }
}
