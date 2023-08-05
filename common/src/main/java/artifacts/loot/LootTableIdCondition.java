package artifacts.loot;

import artifacts.platform.PlatformServices;
import artifacts.registry.ModLootConditions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

// Copied from Forge
// Need to implement this ourselves since forge and porting lib use a different namespace
public class LootTableIdCondition implements LootItemCondition {

    private final ResourceLocation targetLootTableId;

    private LootTableIdCondition(ResourceLocation targetLootTableId) {
        this.targetLootTableId = targetLootTableId;
    }

    @Override
    public LootItemConditionType getType() {
        return ModLootConditions.LOOT_TABLE_ID.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return PlatformServices.platformHelper.getQueriedLootTableId(lootContext).equals(this.targetLootTableId);
    }

    public static Builder builder(final ResourceLocation targetLootTableId) {
        return new Builder(targetLootTableId);
    }

    public static class Builder implements LootItemCondition.Builder {

        private final ResourceLocation targetLootTableId;

        public Builder(ResourceLocation targetLootTableId) {
            if (targetLootTableId == null) {
                throw new IllegalArgumentException("Target loot table must not be null");
            }
            this.targetLootTableId = targetLootTableId;
        }

        @Override
        public LootItemCondition build() {
            return new LootTableIdCondition(this.targetLootTableId);
        }
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootTableIdCondition> {

        @Override
        public void serialize(JsonObject object, LootTableIdCondition instance, JsonSerializationContext context) {
            object.addProperty("loot_table_id", instance.targetLootTableId.toString());
        }

        @Override
        public LootTableIdCondition deserialize(JsonObject object, JsonDeserializationContext context) {
            return new LootTableIdCondition(new ResourceLocation(GsonHelper.getAsString(object, "loot_table_id")));
        }
    }
}
