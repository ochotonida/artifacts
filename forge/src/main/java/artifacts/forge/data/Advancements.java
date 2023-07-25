package artifacts.forge.data;

import artifacts.Artifacts;
import artifacts.forge.registry.ModItems;
import artifacts.registry.ModEntityTypes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class Advancements extends ForgeAdvancementProvider {

    public Advancements(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(Advancements::generate));
    }

    private static void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        ResourceLocation amateurArcheologist = Artifacts.id("amateur_archaeologist");
        Advancement parent = advancement(amateurArcheologist, ModItems.FLAME_PENDANT.get())
                .parent(new ResourceLocation("adventure/root"))
                .addCriterion("find_artifact", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(ItemTags.ARTIFACTS).build()
                )).save(saver, amateurArcheologist, existingFileHelper);

        ResourceLocation chestSlayer = Artifacts.id("chest_slayer");
        advancement(chestSlayer, ModItems.MIMIC_SPAWN_EGG.get())
                .parent(parent)
                .addCriterion("kill_mimic", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().of(ModEntityTypes.MIMIC.get())
                )).save(saver, chestSlayer, existingFileHelper);
    }

    private static Advancement.Builder advancement(ResourceLocation id, ItemLike icon) {
        return Advancement.Builder.advancement().display(display(id.getPath(), icon));
    }

    private static DisplayInfo display(String title, ItemLike icon) {
        return new DisplayInfo(
                new ItemStack(icon),
                Component.translatable("%s.advancements.%s.title".formatted(Artifacts.MOD_ID, title)),
                Component.translatable("%s.advancements.%s.description".formatted(Artifacts.MOD_ID, title)),
                null,
                FrameType.TASK,
                true,
                true,
                false
        );
    }
}
