package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.init.ModItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

public class ItemTags extends ItemTagsProvider {

    private static final ITag.INamedTag<Item> ARTIFACTS = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(Artifacts.MODID, "artifacts"));

    private static final ITag.INamedTag<Item> BELT = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "belt"));
    private static final ITag.INamedTag<Item> CURIO = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "curio"));
    private static final ITag.INamedTag<Item> FEET = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "feet"));
    private static final ITag.INamedTag<Item> HANDS = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "hands"));
    private static final ITag.INamedTag<Item> HEAD = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "head"));
    private static final ITag.INamedTag<Item> NECKLACE = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "necklace"));

    public ItemTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, new BlockTags(generator, existingFileHelper), Artifacts.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        // noinspection ConstantConditions
        getOrCreateBuilder(ARTIFACTS).add(ForgeRegistries.ITEMS.getValues().stream()
                .filter(item -> item.getRegistryName().getNamespace().equals(Artifacts.MODID))
                .filter(item -> item != ModItems.MIMIC_SPAWN_EGG.get())
                .collect(Collectors.toList()).toArray(new Item[]{})
        );

        getOrCreateBuilder(CURIO).add(ModItems.WHOOPEE_CUSHION.get());

        getOrCreateBuilder(BELT).add(
                ModItems.ANTIDOTE_VESSEL.get(),
                ModItems.CLOUD_IN_A_BOTTLE.get(),
                ModItems.CRYSTAL_HEART.get(),
                ModItems.OBSIDIAN_SKULL.get(),
                ModItems.UNIVERSAL_ATTRACTOR.get()
        );

        getOrCreateBuilder(FEET).add(
                ModItems.BUNNY_HOPPERS.get(),
                ModItems.FLIPPERS.get(),
                ModItems.KITTY_SLIPPERS.get(),
                ModItems.RUNNING_SHOES.get(),
                ModItems.STEADFAST_SPIKES.get()
        );

        getOrCreateBuilder(HANDS).add(
                ModItems.DIGGING_CLAWS.get(),
                ModItems.FERAL_CLAWS.get(),
                ModItems.FIRE_GAUNTLET.get(),
                ModItems.POCKET_PISTON.get(),
                ModItems.POWER_GLOVE.get(),
                ModItems.VAMPIRIC_GLOVE.get()
        );

        getOrCreateBuilder(HEAD).add(
                ModItems.NIGHT_VISION_GOGGLES.get(),
                ModItems.NOVELTY_DRINKING_HAT.get(),
                ModItems.PLASTIC_DRINKING_HAT.get(),
                ModItems.SNORKEL.get(),
                ModItems.SUPERSTITIOUS_HAT.get(),
                ModItems.VILLAGER_HAT.get()
        );

        getOrCreateBuilder(NECKLACE).add(
                ModItems.CROSS_NECKLACE.get(),
                ModItems.FLAME_PENDANT.get(),
                ModItems.LUCKY_SCARF.get(),
                ModItems.PANIC_NECKLACE.get(),
                ModItems.SCARF_OF_INVISIBILITY.get(),
                ModItems.SHOCK_PENDANT.get(),
                ModItems.THORN_PENDANT.get()
        );
    }

    private static class BlockTags extends BlockTagsProvider {

        public BlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
            super(generator, Artifacts.MODID, existingFileHelper);
        }

        @Override
        protected void registerTags() {

        }
    }
}
