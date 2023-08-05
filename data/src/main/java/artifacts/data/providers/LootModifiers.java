package artifacts.data.providers;

import artifacts.Artifacts;
import artifacts.forge.loot.RollLootTableModifier;
import artifacts.loot.ConfigurableRandomChance;
import artifacts.loot.EverlastingBeefChance;
import artifacts.loot.LootTableIdCondition;
import artifacts.registry.ModItems;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LootModifiers extends GlobalLootModifierProvider {

    protected List<Builder> lootBuilders = new ArrayList<>();

    public LootModifiers(PackOutput packOutput) {
        super(packOutput, Artifacts.MOD_ID);
    }

    private void addLoot() {
        for (EntityType<?> type : List.of(EntityType.COW, EntityType.MOOSHROOM)) {
            // noinspection ConstantConditions
            String name = "entities/" + ForgeRegistries.ENTITY_TYPES.getKey(type).getPath();
            lootBuilders.add(
                    new Builder(name)
                            .lootPoolCondition(EverlastingBeefChance.everlastingBeefChance())
                            .lootModifierCondition(LootTableIdCondition.builder(new ResourceLocation(name)))
                            .parameterSet(LootContextParamSets.ENTITY)
                            .lootPoolCondition(LootItemKilledByPlayerCondition.killedByPlayer())
                            .everlastingBeef()
            );
        }

        for (String biome : Arrays.asList("desert", "plains", "savanna", "snowy", "taiga")) {
            builder(String.format("chests/village/village_%s_house", biome), 0.02F)
                    .item(ModItems.VILLAGER_HAT.get());
        }
        builder("chests/spawn_bonus_chest", 1)
                .item(ModItems.WHOOPEE_CUSHION.get());
        builder("chests/village/village_armorer", 0.1F)
                .item(ModItems.STEADFAST_SPIKES.get())
                .item(ModItems.SUPERSTITIOUS_HAT.get())
                .item(ModItems.RUNNING_SHOES.get())
                .item(ModItems.VAMPIRIC_GLOVE.get());
        builder("chests/village/village_butcher", 0.05F)
                .item(ModItems.EVERLASTING_BEEF.get());
        builder("chests/village/village_tannery", 0.2F)
                .item(ModItems.UMBRELLA.get(), 3)
                .item(ModItems.WHOOPEE_CUSHION.get(), 2)
                .item(ModItems.KITTY_SLIPPERS.get())
                .item(ModItems.BUNNY_HOPPERS.get())
                .item(ModItems.SCARF_OF_INVISIBILITY.get());
        builder("chests/village/village_temple", 0.2F)
                .item(ModItems.CROSS_NECKLACE.get())
                .item(ModItems.ANTIDOTE_VESSEL.get())
                .item(ModItems.CHARM_OF_SINKING.get());
        builder("chests/village/village_toolsmith", 0.15F)
                .item(ModItems.DIGGING_CLAWS.get())
                .item(ModItems.POCKET_PISTON.get());
        builder("chests/village/village_weaponsmith", 0.1F)
                .item(ModItems.FERAL_CLAWS.get());
        builder("chests/abandoned_mineshaft", 0.3F)
                .item(ModItems.NIGHT_VISION_GOGGLES.get())
                .item(ModItems.PANIC_NECKLACE.get())
                .item(ModItems.OBSIDIAN_SKULL.get())
                .item(ModItems.SUPERSTITIOUS_HAT.get())
                .item(ModItems.DIGGING_CLAWS.get())
                .item(ModItems.CLOUD_IN_A_BOTTLE.get())
                .item(ModItems.VAMPIRIC_GLOVE.get())
                .item(ModItems.AQUA_DASHERS.get())
                .drinkingHat(1);
        builder("chests/bastion_hoglin_stable", 0.2F)
                .artifact(5)
                .item(ModItems.BUNNY_HOPPERS.get(), 3)
                .item(ModItems.FLAME_PENDANT.get(), 3)
                .item(ModItems.EVERLASTING_BEEF.get());
        builder("chests/bastion_treasure", 0.65F)
                .artifact(6)
                .item(ModItems.GOLDEN_HOOK.get(), 3)
                .item(ModItems.CROSS_NECKLACE.get(), 3)
                .item(ModItems.FIRE_GAUNTLET.get(), 2)
                .item(ModItems.STEADFAST_SPIKES.get())
                .item(ModItems.PANIC_NECKLACE.get())
                .item(ModItems.CRYSTAL_HEART.get())
                .item(ModItems.ANTIDOTE_VESSEL.get());
        builder("chests/buried_treasure", 0.25F)
                .item(ModItems.SNORKEL.get(), 5)
                .item(ModItems.FLIPPERS.get(), 5)
                .item(ModItems.UMBRELLA.get(), 5)
                .item(ModItems.GOLDEN_HOOK.get(), 5)
                .item(ModItems.FERAL_CLAWS.get(), 3)
                .item(ModItems.DIGGING_CLAWS.get(), 3)
                .item(ModItems.KITTY_SLIPPERS.get(), 3)
                .item(ModItems.BUNNY_HOPPERS.get(), 3)
                .item(ModItems.LUCKY_SCARF.get(), 3)
                .item(ModItems.AQUA_DASHERS.get(), 3)
                .drinkingHat(3);
        builder("chests/desert_pyramid", 0.2F)
                .item(ModItems.FLAME_PENDANT.get(), 2)
                .item(ModItems.THORN_PENDANT.get(), 2)
                .item(ModItems.WHOOPEE_CUSHION.get(), 2)
                .item(ModItems.CHARM_OF_SINKING.get(), 2)
                .item(ModItems.SHOCK_PENDANT.get())
                .item(ModItems.UMBRELLA.get())
                .item(ModItems.SCARF_OF_INVISIBILITY.get())
                .item(ModItems.UNIVERSAL_ATTRACTOR.get())
                .item(ModItems.VAMPIRIC_GLOVE.get());
        builder("chests/end_city_treasure", 0.3F)
                .artifact(3)
                .item(ModItems.CRYSTAL_HEART.get())
                .item(ModItems.CLOUD_IN_A_BOTTLE.get())
                .item(ModItems.HELIUM_FLAMINGO.get(), 4);
        builder("chests/jungle_temple", 0.3F)
                .item(ModItems.KITTY_SLIPPERS.get(), 2)
                .item(ModItems.BUNNY_HOPPERS.get());
        builder("chests/nether_bridge", 0.15F)
                .item(ModItems.CROSS_NECKLACE.get())
                .item(ModItems.NIGHT_VISION_GOGGLES.get())
                .item(ModItems.POCKET_PISTON.get())
                .item(ModItems.RUNNING_SHOES.get())
                .drinkingHat(1);
        builder("chests/pillager_outpost", 0.25F)
                .item(ModItems.PANIC_NECKLACE.get())
                .item(ModItems.POCKET_PISTON.get())
                .item(ModItems.STEADFAST_SPIKES.get())
                .item(ModItems.POWER_GLOVE.get())
                .item(ModItems.CROSS_NECKLACE.get())
                .item(ModItems.SCARF_OF_INVISIBILITY.get())
                .item(ModItems.CRYSTAL_HEART.get())
                .item(ModItems.CLOUD_IN_A_BOTTLE.get())
                .item(ModItems.SUPERSTITIOUS_HAT.get());
        builder("chests/ruined_portal", 0.15F)
                .item(ModItems.NIGHT_VISION_GOGGLES.get())
                .item(ModItems.THORN_PENDANT.get())
                .item(ModItems.FIRE_GAUNTLET.get())
                .item(ModItems.POWER_GLOVE.get())
                .item(ModItems.UNIVERSAL_ATTRACTOR.get())
                .item(ModItems.OBSIDIAN_SKULL.get())
                .item(ModItems.LUCKY_SCARF.get());
        builder("chests/shipwreck_treasure", 0.15F)
                .item(ModItems.GOLDEN_HOOK.get(), 3)
                .item(ModItems.SNORKEL.get())
                .item(ModItems.FLIPPERS.get())
                .item(ModItems.SCARF_OF_INVISIBILITY.get())
                .item(ModItems.STEADFAST_SPIKES.get())
                .item(ModItems.UNIVERSAL_ATTRACTOR.get())
                .item(ModItems.FERAL_CLAWS.get())
                .item(ModItems.NIGHT_VISION_GOGGLES.get())
                .item(ModItems.OBSIDIAN_SKULL.get())
                .item(ModItems.RUNNING_SHOES.get())
                .item(ModItems.CHARM_OF_SINKING.get());
        builder("chests/stronghold_corridor", 0.3F)
                .artifact(3)
                .item(ModItems.POWER_GLOVE.get())
                .item(ModItems.ANTIDOTE_VESSEL.get())
                .item(ModItems.SUPERSTITIOUS_HAT.get())
                .item(ModItems.LUCKY_SCARF.get())
                .item(ModItems.AQUA_DASHERS.get())
                .item(ModItems.HELIUM_FLAMINGO.get());
        builder("chests/underwater_ruin_big", 0.45F)
                .item(ModItems.SNORKEL.get(), 3)
                .item(ModItems.FLIPPERS.get(), 3)
                .item(ModItems.SUPERSTITIOUS_HAT.get())
                .item(ModItems.LUCKY_SCARF.get())
                .item(ModItems.FIRE_GAUNTLET.get())
                .item(ModItems.CROSS_NECKLACE.get())
                .item(ModItems.POWER_GLOVE.get())
                .item(ModItems.CLOUD_IN_A_BOTTLE.get());
        builder("chests/woodland_mansion", 0.25F)
                .artifact(1);
    }

    protected Builder builder(String lootTable, float baseChance) {
        Builder builder = new Builder(lootTable);
        builder.lootPoolCondition(ConfigurableRandomChance.configurableRandomChance(baseChance));
        builder.lootModifierCondition(LootTableIdCondition.builder(new ResourceLocation(lootTable)));
        lootBuilders.add(builder);
        return builder;
    }

    @Override
    protected void start() {
        addLoot();

        for (Builder lootBuilder : lootBuilders) {
            add("inject/" + lootBuilder.lootTable, lootBuilder.build());
        }
    }

    @SuppressWarnings({"UnusedReturnValue", "SameParameterValue"})
    protected static class Builder {

        private final String lootTable;
        private final LootPool.Builder lootPool = LootPool.lootPool();
        private final List<LootItemCondition> conditions;

        private LootContextParamSet paramSet = LootContextParamSets.CHEST;

        private Builder(String lootTable) {
            this.lootTable = lootTable;
            this.conditions = new ArrayList<>();
        }

        private RollLootTableModifier build() {
            return new RollLootTableModifier(conditions.toArray(new LootItemCondition[]{}), Artifacts.id("inject/%s", lootTable));
        }

        protected LootTable.Builder createLootTable() {
            return new LootTable.Builder().withPool(lootPool);
        }

        public LootContextParamSet getParameterSet() {
            return paramSet;
        }

        protected String getName() {
            return lootTable;
        }

        private Builder parameterSet(LootContextParamSet paramSet) {
            this.paramSet = paramSet;
            return this;
        }

        private Builder lootPoolCondition(LootItemCondition.Builder condition) {
            lootPool.when(condition);
            return this;
        }

        private Builder lootModifierCondition(LootItemCondition.Builder condition) {
            conditions.add(condition.build());
            return this;
        }

        private Builder item(Item item, int weight) {
            lootPool.add(LootTables.item(item, weight));
            return this;
        }

        private Builder item(Item item) {
            return item(item, 1);
        }

        private Builder artifact(int weight) {
            lootPool.add(LootTables.artifact(weight));
            return this;
        }

        private Builder drinkingHat(int weight) {
            lootPool.add(LootTables.drinkingHat(weight));
            return this;
        }

        private Builder everlastingBeef() {
            lootPool.add(LootTables.item(ModItems.EVERLASTING_BEEF.get(), 1)
                    .apply(
                            SmeltItemFunction.smelted().when(
                                    LootItemEntityPropertyCondition.hasProperties(
                                            LootContext.EntityTarget.THIS,
                                            EntityPredicate.Builder.entity().flags(
                                                    EntityFlagsPredicate.Builder.flags()
                                                            .setOnFire(true)
                                                            .build()
                                            )
                                    )
                            )
                    )
            );
            return this;
        }
    }
}
