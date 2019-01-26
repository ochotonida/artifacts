package artifacts.common.loot.functions;

import artifacts.Artifacts;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AddRandomEffect extends LootFunction {

    public AddRandomEffect(LootCondition[] conditions) {
        super(conditions);
    }

    @Override
    public ItemStack apply(ItemStack stack, Random random, LootContext context) {
        PotionType type = PotionType.REGISTRY.getRandomObject(random);
        if (type == PotionType.getPotionTypeForName("empty")) {
            // default to water instead of uncraftable potion
            type = PotionType.getPotionTypeForName("water");
        }
        // noinspection ConstantConditions
        PotionUtils.addPotionToItemStack(stack, type);
        return stack;
    }

    public static class Serializer extends LootFunction.Serializer<AddRandomEffect> {

        public Serializer() {
            super(new ResourceLocation(Artifacts.MODID, "add_random_effect"), AddRandomEffect.class);
        }

        @Override
        public void serialize(JsonObject object, AddRandomEffect function, JsonSerializationContext serializationContext) {

        }

        @Override
        public AddRandomEffect deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditions) {
            return new AddRandomEffect(conditions);
        }
    }
}
