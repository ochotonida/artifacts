package artifacts.neoforge.loot;

import artifacts.neoforge.registry.ModLootModifiers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

import java.util.Optional;

public class ReplaceWithTableLootModifier extends AddTableLootModifier {

    public static final MapCodec<ReplaceWithTableLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> codecStart(instance)
            .and(ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("table").forGetter(ReplaceWithTableLootModifier::table))
            .apply(instance, ReplaceWithTableLootModifier::new)
    );

    public ReplaceWithTableLootModifier(Optional<Holder<LootItemCondition>> condition, int priority, ResourceKey<LootTable> lootTable) {
        super(condition, priority, lootTable);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        return super.doApply(new ObjectArrayList<>(1), context);
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return ModLootModifiers.REPLACE_WITH_TABLE.value();
    }
}
