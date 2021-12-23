package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.init.ModEntityTypes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class EntityTypeTags extends EntityTypeTagsProvider {

    private static final Tag.Named<EntityType<?>> MOB_IMPRISONMENT_TOOL_BLACKLIST = net.minecraft.tags.EntityTypeTags.createOptional(new ResourceLocation("industrialforegoing", "mob_imprisonment_tool_blacklist"));

    public EntityTypeTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Artifacts.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(MOB_IMPRISONMENT_TOOL_BLACKLIST).add(ModEntityTypes.MIMIC.get());
    }
}
