package artifacts.common.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RollLootTableLootModifier extends LootModifier {

    private final ResourceLocation lootTable;

    public RollLootTableLootModifier(LootItemCondition[] conditions, ResourceLocation lootTable) {
        super(conditions);
        this.lootTable = lootTable;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        // noinspection deprecation - prevent triggering global loot modifiers again
        context.getLootTable(lootTable).getRandomItems(context, generatedLoot::add);
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<RollLootTableLootModifier> {

        @Override
        public RollLootTableLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
            ResourceLocation lootTable = new ResourceLocation(GsonHelper.getAsString(object, "lootTable"));
            return new RollLootTableLootModifier(conditions, lootTable);
        }

        @Override
        public JsonObject write(RollLootTableLootModifier instance) {
            JsonObject object = makeConditions(instance.conditions);
            object.addProperty("lootTable", instance.lootTable.toString());
            return object;
        }
    }
}
