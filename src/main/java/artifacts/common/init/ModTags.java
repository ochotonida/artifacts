package artifacts.common.init;

import artifacts.Artifacts;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("SameParameterValue")
public class ModTags {

    public static final TagKey<Block> MINEABLE_WITH_DIGGING_CLAWS = create(Registry.BLOCK_REGISTRY, "mineable/digging_claws");
    public static final TagKey<Block> CAMPSITE_CHESTS = create(Registry.BLOCK_REGISTRY, "campsite_chests");
    public static final TagKey<MobEffect> ANTIDOTE_VESSEL_CANCELLABLE = create(Registry.MOB_EFFECT_REGISTRY, "antidote_vessel_cancellable");
    public static final TagKey<EntityType<?>> CREEPERS = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(Artifacts.MODID, "creepers"));

    private static <T> TagKey<T> create(ResourceKey<Registry<T>> registry, String id) {
        return TagKey.create(registry, new ResourceLocation(Artifacts.MODID, id));
    }
}
