package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.item.curio.hands.DiggingClawsItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockTags extends BlockTagsProvider {

    public BlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Artifacts.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(DiggingClawsItem.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE);
        tag(DiggingClawsItem.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL);
        tag(DiggingClawsItem.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE);
        tag(DiggingClawsItem.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_HOE);
    }
}
