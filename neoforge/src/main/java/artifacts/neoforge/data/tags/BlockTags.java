package artifacts.neoforge.data.tags;

import artifacts.Artifacts;
import artifacts.client.mimic.MimicChestMaterials;
import artifacts.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class BlockTags extends BlockTagsProvider {

    public BlockTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, Artifacts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE);
        tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL);
        tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE);
        tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_HOE);

        tag(ModTags.CAMPSITE_CHESTS).add(Blocks.CHEST);
        for (String chestType : MimicChestMaterials.QUARK_WOODEN_CHEST_MATERIALS) {
            tag(ModTags.CAMPSITE_CHESTS).addOptional(ResourceLocation.fromNamespaceAndPath("quark", "%s_chest".formatted(chestType)));
        }

        tag(ModTags.ROOTED_BOOTS_GRASS).add(
                Blocks.GRASS_BLOCK,
                Blocks.MOSS_BLOCK,
                Blocks.MOSS_CARPET,
                Blocks.DIRT_PATH,
                Blocks.PODZOL
        );
        tag(ModTags.SNOW_LAYERS).add(Blocks.SNOW);
    }
}
