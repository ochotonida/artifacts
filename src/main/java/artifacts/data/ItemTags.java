package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

public class ItemTags extends ItemTagsProvider {

    private static final Tag.Named<Item> ARTIFACTS = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(Artifacts.MODID, "artifacts"));

    private static final Tag.Named<Item> BELT = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "belt"));
    private static final Tag.Named<Item> CURIO = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "curio"));
    private static final Tag.Named<Item> FEET = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "feet"));
    private static final Tag.Named<Item> HANDS = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "hands"));
    private static final Tag.Named<Item> HEAD = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "head"));
    private static final Tag.Named<Item> NECKLACE = net.minecraft.tags.ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "necklace"));

    public ItemTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, new BlockTags(generator, existingFileHelper), Artifacts.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        // noinspection ConstantConditions
        tag(ARTIFACTS).add(ForgeRegistries.ITEMS.getValues().stream()
                .filter(item -> item.getRegistryName().getNamespace().equals(Artifacts.MODID))
                .filter(item -> item != ModItems.MIMIC_SPAWN_EGG.get())
                .collect(Collectors.toList()).toArray(new Item[]{})
        );

        tag(net.minecraft.tags.ItemTags.PIGLIN_LOVED).add(ModItems.GOLDEN_HOOK.get());

        tag(CURIO).add(ModItems.WHOOPEE_CUSHION.get());

        tag(BELT).add(
                ModItems.ANTIDOTE_VESSEL.get(),
                ModItems.CLOUD_IN_A_BOTTLE.get(),
                ModItems.CRYSTAL_HEART.get(),
                ModItems.OBSIDIAN_SKULL.get(),
                ModItems.UNIVERSAL_ATTRACTOR.get(),
                ModItems.HELIUM_FLAMINGO.get()
        );

        tag(FEET).add(
                ModItems.BUNNY_HOPPERS.get(),
                ModItems.FLIPPERS.get(),
                ModItems.KITTY_SLIPPERS.get(),
                ModItems.RUNNING_SHOES.get(),
                ModItems.STEADFAST_SPIKES.get(),
                ModItems.AQUA_DASHERS.get()
        );

        tag(HANDS).add(
                ModItems.DIGGING_CLAWS.get(),
                ModItems.FERAL_CLAWS.get(),
                ModItems.FIRE_GAUNTLET.get(),
                ModItems.POCKET_PISTON.get(),
                ModItems.POWER_GLOVE.get(),
                ModItems.VAMPIRIC_GLOVE.get(),
                ModItems.GOLDEN_HOOK.get()
        );

        tag(HEAD).add(
                ModItems.NIGHT_VISION_GOGGLES.get(),
                ModItems.NOVELTY_DRINKING_HAT.get(),
                ModItems.PLASTIC_DRINKING_HAT.get(),
                ModItems.SNORKEL.get(),
                ModItems.SUPERSTITIOUS_HAT.get(),
                ModItems.VILLAGER_HAT.get()
        );

        tag(NECKLACE).add(
                ModItems.CROSS_NECKLACE.get(),
                ModItems.FLAME_PENDANT.get(),
                ModItems.LUCKY_SCARF.get(),
                ModItems.PANIC_NECKLACE.get(),
                ModItems.SCARF_OF_INVISIBILITY.get(),
                ModItems.SHOCK_PENDANT.get(),
                ModItems.THORN_PENDANT.get(),
                ModItems.CHARM_OF_SINKING.get()
        );
    }

    private static class BlockTags extends BlockTagsProvider {

        public BlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
            super(generator, Artifacts.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {

        }
    }
}
