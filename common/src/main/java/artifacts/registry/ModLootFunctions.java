package artifacts.registry;

import artifacts.loot.ReplaceWithLootTableFunction;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public class ModLootFunctions {

    public static final Register<MapCodec<? extends LootItemFunction>> LOOT_FUNCTION_TYPES = Register.create(Registries.LOOT_FUNCTION_TYPE);

    public static final RegistryHolder<MapCodec<? extends LootItemFunction>, MapCodec<? extends LootItemFunction>> REPLACE_WITH_LOOT_TABLE = LOOT_FUNCTION_TYPES.register("replace_with_loot_table", () -> ReplaceWithLootTableFunction.CODEC);

}
