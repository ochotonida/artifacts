package artifacts.data;

import artifacts.Artifacts;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockTags extends BlockTagsProvider {

    private static final Tag.Named<Block> MINEABLE_WITH_DIGGING_CLAWS = net.minecraft.tags.BlockTags.createOptional(new ResourceLocation(Artifacts.MODID, "mineable/digging_claws"));

    public BlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Artifacts.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE);
        tag(MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL);
        tag(MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE);
        tag(MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_HOE);
    }
}
