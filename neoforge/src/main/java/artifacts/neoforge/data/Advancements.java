package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.neoforge.data.tags.ItemTags;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.ConsumeItemTrigger;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.KilledTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.ItemLike;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class Advancements extends AdvancementProvider {

    public static final Map<String, String> TRANSLATIONS = new HashMap<>();

    public Advancements(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(Advancements::generate));
    }

    @SuppressWarnings("removal")
    private static void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {
        HolderLookup.RegistryLookup<Item> items = registries.lookupOrThrow(Registries.ITEM);
        HolderLookup.RegistryLookup<EntityType<?>> entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);

        Identifier amateurArcheologist = Artifacts.id("amateur_archaeologist");
        AdvancementHolder parent = advancement(amateurArcheologist, ModItems.FLAME_PENDANT.value(), "Amateur Archaeologist", "Find an Artifact")
                .parent(Identifier.withDefaultNamespace("adventure/root"))
                .addCriterion("find_artifact", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(items, ItemTags.ARTIFACTS).build()
                )).save(saver, amateurArcheologist);

        Identifier chestSlayer = Artifacts.id("chest_slayer");
        advancement(chestSlayer, ModItems.MIMIC_SPAWN_EGG.value(), "Chest Slayer", "Kill a Mimic")
                .parent(parent)
                .addCriterion("kill_mimic", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().of(entityTypes, ModEntityTypes.MIMIC.value())
                )).save(saver, chestSlayer);

        Identifier adventurousEater = Artifacts.id("adventurous_eater");
        advancement(adventurousEater, ModItems.ONION_RING.value(), "Adventurous Eater", "Eat an Artifact", true)
                .parent(parent)
                .addCriterion("eat_artifact", ConsumeItemTrigger.TriggerInstance.usedItem(
                        ItemPredicate.Builder.item().of(items, ModItems.ONION_RING.value())
                )).save(saver, adventurousEater);
    }

    private static Advancement.Builder advancement(Identifier id, ItemLike icon, String title, String description) {
        return advancement(id, icon, title, description, false);
    }

    private static Advancement.Builder advancement(Identifier id, ItemLike icon, String title, String description, boolean hidden) {
        TRANSLATIONS.put("%s.advancements.%s.title".formatted(id.getNamespace(), id.getPath()), title);
        TRANSLATIONS.put("%s.advancements.%s.description".formatted(id.getNamespace(), id.getPath()), description);
        return Advancement.Builder.advancement().display(display(id.getPath(), icon, hidden));
    }

    private static DisplayInfo display(String title, ItemLike icon, boolean hidden) {
        return new DisplayInfo(
                new ItemStackTemplate(icon.asItem()),
                Component.translatable("%s.advancements.%s.title".formatted(Artifacts.MOD_ID, title)),
                Component.translatable("%s.advancements.%s.description".formatted(Artifacts.MOD_ID, title)),
                Optional.empty(),
                AdvancementType.TASK,
                true,
                true,
                hidden
        );
    }
}
