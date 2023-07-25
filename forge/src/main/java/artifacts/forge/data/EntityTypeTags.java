package artifacts.forge.data;

import artifacts.Artifacts;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EntityTypeTags extends EntityTypeTagsProvider {

    private static final TagKey<EntityType<?>> MOB_DUPLICATOR_BLACKLIST = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("industrialforegoing", "mob_duplicator_blacklist"));

    public EntityTypeTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, Artifacts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(MOB_DUPLICATOR_BLACKLIST).add(ModEntityTypes.MIMIC.get());
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
