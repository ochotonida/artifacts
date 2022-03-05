package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.init.ModEntityTypes;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class EntityTypeTags extends EntityTypeTagsProvider {

    private static final TagKey<EntityType<?>> MOB_IMPRISONMENT_TOOL_BLACKLIST = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("industrialforegoing", "mob_imprisonment_tool_blacklist"));

    public EntityTypeTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Artifacts.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(MOB_IMPRISONMENT_TOOL_BLACKLIST).add(ModEntityTypes.MIMIC.get());
    }
}
