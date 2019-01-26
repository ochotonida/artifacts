package artifacts.common.loot.functions;

import artifacts.Artifacts;
import artifacts.common.ModConfig;
import artifacts.common.ModItems;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GenerateEverlastingFish extends LootFunction {

    public GenerateEverlastingFish(LootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    public ItemStack apply(ItemStack stack, Random random, LootContext context) {
        if (random.nextDouble() < ModConfig.everlastingFishChance) {
            switch (random.nextInt(4)) {
                case 0:
                    return new ItemStack(ModItems.EVERLASTING_SALMON);
                case 1:
                    return new ItemStack(ModItems.EVERLASTING_CLOWNFISH);
                default:
                    return new ItemStack(ModItems.EVERLASTING_COD);
            }
        }
        return stack;
    }

    public static class Serializer extends LootFunction.Serializer<GenerateEverlastingFish> {

        public Serializer() {
            super(new ResourceLocation(Artifacts.MODID, "generate_everlasting_fish"), GenerateEverlastingFish.class);
        }

        @Override
        public void serialize(JsonObject object, GenerateEverlastingFish function, JsonSerializationContext serializationContext) {

        }

        @Override
        public GenerateEverlastingFish deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditions) {
            return new GenerateEverlastingFish(conditions);
        }
    }
}
