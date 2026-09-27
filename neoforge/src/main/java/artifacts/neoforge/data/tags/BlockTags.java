package artifacts.neoforge.data.tags;

import artifacts.Artifacts;
import artifacts.client.mimic.MimicChestMaterials;
import artifacts.integration.ModCompat;
import artifacts.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.references.BlockItemIds;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class BlockTags extends BlockTagsProvider {

    public BlockTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider, Artifacts.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE);
        tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL);
        tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE);
        tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_HOE);

        tag(ModTags.CAMPSITE_CHESTS).add(BlockItemIds.CHEST.block());
        for (String chestType : MimicChestMaterials.QUARK_WOODEN_CHEST_MATERIALS) {
            getOrCreateRawBuilder(ModTags.CAMPSITE_CHESTS).addOptionalElement(ModCompat.QUARK.id("%s_chest".formatted(chestType)));
        }

        //noinspection unchecked
        tag(ModTags.ROOTED_BOOTS_GRASS).add(
                BlockItemIds.GRASS_BLOCK.block(),
                BlockItemIds.MOSS_BLOCK.block(),
                BlockItemIds.MOSS_CARPET.block(),
                BlockItemIds.DIRT_PATH.block(),
                BlockItemIds.PODZOL.block()
        );
        tag(ModTags.SNOW_LAYERS).add(BlockItemIds.SNOW.block());
    }
}
