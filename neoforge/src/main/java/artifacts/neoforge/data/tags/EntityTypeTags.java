package artifacts.neoforge.data.tags;

import artifacts.Artifacts;
import artifacts.integration.ModCompat;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityTypeIds;
import net.neoforged.neoforge.common.Tags;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EntityTypeTags extends EntityTypeTagsProvider {

    public EntityTypeTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider, Artifacts.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // noinspection ConstantConditions
        tag(Tags.EntityTypes.CAPTURING_NOT_SUPPORTED).add(ModEntityTypes.MIMIC.getKey());
        tag(ModTags.CREEPERS).add(EntityTypeIds.CREEPER);

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
            getOrCreateRawBuilder(ModTags.CREEPERS).addOptionalElement(
                    ModCompat.CREEPER_OVERHAUL.id(creeper)
            );
        }
    }
}
