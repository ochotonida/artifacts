package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.init.ModEntityTypes;
import artifacts.common.init.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class EntityTypeTags extends EntityTypeTagsProvider {

    private static final TagKey<EntityType<?>> MOB_IMPRISONMENT_TOOL_BLACKLIST = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("industrialforegoing", "mob_imprisonment_tool_blacklist"));

    public EntityTypeTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Artifacts.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(MOB_IMPRISONMENT_TOOL_BLACKLIST).add(ModEntityTypes.MIMIC.get());
        tag(ModTags.CREEPERS).add(EntityType.CREEPER);

        List<String> creepers = Arrays.asList(
                "jungle_creeper",
                "bamboo_creeper",
                "desert_creeper",
                "badlands_creeper",
                "hills_creeper",
                "savannah_creeper",
                "mushroom_creeper",
                "swamp_creeper",
                "dripstone_creeper",
                "cave_creeper",
                "dark_oak_creeper",
                "spruce_creeper",
                "beach_creeper",
                "snowy_creeper"
        );
        for (String creeper : creepers) {
            tag(ModTags.CREEPERS).addOptional(new ResourceLocation("creeperoverhaul", creeper));
        }
    }
}
