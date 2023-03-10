package artifacts.data;

import artifacts.Artifacts;
import artifacts.client.render.entity.MimicChestLayer;
import artifacts.common.init.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockTags extends BlockTagsProvider {

    public BlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Artifacts.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE);
        tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL);
        tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE);
        tag(ModTags.MINEABLE_WITH_DIGGING_CLAWS).addTag(net.minecraft.tags.BlockTags.MINEABLE_WITH_HOE);

        tag(ModTags.CAMPSITE_CHESTS).add(Blocks.CHEST);
        for (String chestType : MimicChestLayer.QUARK_CHEST_TYPES) {
            tag(ModTags.CAMPSITE_CHESTS).addOptional(new ResourceLocation("quark", String.format("%s_chest", chestType)));
        }
    }
}
