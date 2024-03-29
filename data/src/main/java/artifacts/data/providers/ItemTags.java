package artifacts.data.providers;

import artifacts.Artifacts;
import artifacts.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ItemTags extends ItemTagsProvider {

    public static final TagKey<Item> ARTIFACTS = TagKey.create(Registries.ITEM, Artifacts.id("artifacts"));
    public static final TagKey<Item> ORIGINS_MEAT = TagKey.create(Registries.ITEM, new ResourceLocation("origins", "meat"));
    public static final TagKey<Item> ORIGINS_SHIELDS = TagKey.create(Registries.ITEM, new ResourceLocation("origins", "shields"));

    public ItemTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, blockTags, Artifacts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // noinspection ConstantConditions
        tag(ARTIFACTS).add(ForgeRegistries.ITEMS.getValues().stream()
                .filter(item -> ForgeRegistries.ITEMS.getKey(item).getNamespace().equals(Artifacts.MOD_ID))
                .filter(item -> item != ModItems.MIMIC_SPAWN_EGG.get()).toList().toArray(new Item[]{})
        );

        tag(ORIGINS_MEAT).add(
                ModItems.EVERLASTING_BEEF.get(),
                ModItems.ETERNAL_STEAK.get()
        );
        tag(ORIGINS_SHIELDS).add(
                ModItems.UMBRELLA.get()
        );

        tag(net.minecraft.tags.ItemTags.PIGLIN_LOVED).add(ModItems.GOLDEN_HOOK.get());
    }
}
