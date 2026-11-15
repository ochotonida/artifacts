package artifacts.neoforge.data.advancement;

import artifacts.Artifacts;
import artifacts.neoforge.data.tags.ItemTags;
import artifacts.registry.ModAdvancements;
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
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.ItemLike;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ArtifactsAdvancementSubProvider extends AdvancementSubProvider {

    public static final Map<String, String> TRANSLATIONS = new HashMap<>();

    public ArtifactsAdvancementSubProvider(BootstrapContext<Advancement> output) {
        super(output);
    }

    @Override
    public void generate() {
        HolderGetter<Item> items = output.lookup(Registries.ITEM);
        HolderGetter<EntityType<?>> entityTypes = output.lookup(Registries.ENTITY_TYPE);

        // noinspection removal
        AdvancementHolder amateurArchaeologist = advancement(ModAdvancements.AMATEUR_ARCHAEOLOGIST, ModItems.FLAME_PENDANT.value())
                .parent(Identifier.withDefaultNamespace("adventure/root"))
                .addCriterion("find_artifact", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(items, ItemTags.ARTIFACTS).build()
                )).save(this.output, ModAdvancements.AMATEUR_ARCHAEOLOGIST);

        advancement(ModAdvancements.CHEST_SLAYER, ModItems.MIMIC_SPAWN_EGG.value())
                .parent(amateurArchaeologist)
                .addCriterion("kill_mimic", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().of(entityTypes, ModEntityTypes.MIMIC.value())
                )).save(this.output, ModAdvancements.CHEST_SLAYER);

        advancement(ModAdvancements.ADVENTUROUS_EATER, ModItems.ONION_RING.value(), true)
                .parent(amateurArchaeologist)
                .addCriterion("eat_artifact", ConsumeItemTrigger.TriggerInstance.usedItem(
                        ItemPredicate.Builder.item().of(items, ModItems.ONION_RING.value())
                )).save(this.output, ModAdvancements.ADVENTUROUS_EATER);
    }

    private static Advancement.Builder advancement(Identifier id, ItemLike icon) {
        return advancement(id, icon, false);
    }

    private static Advancement.Builder advancement(Identifier id, ItemLike icon, boolean hidden) {
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
