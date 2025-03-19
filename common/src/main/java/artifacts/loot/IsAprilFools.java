package artifacts.loot;

import artifacts.registry.ModLootConditions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.time.LocalDate;
import java.time.Month;

public record IsAprilFools() implements LootItemCondition {

    private static final boolean IS_APRIL_FOOLS = LocalDate.now().getMonth() == Month.APRIL && LocalDate.now().getDayOfMonth() == 1;

    @Override
    public LootItemConditionType getType() {
        return ModLootConditions.IS_APRIL_FOOLS.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return IS_APRIL_FOOLS;
    }

    public static Builder builder() {
        return IsAprilFools::new;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<IsAprilFools> {

        @Override
        public void serialize(JsonObject jsonObject, IsAprilFools object, JsonSerializationContext jsonSerializationContext) {

        }

        @Override
        public IsAprilFools deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return new IsAprilFools();
        }
    }
 }
