package artifacts.common.init;

import artifacts.Artifacts;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@SuppressWarnings("SameParameterValue")
public class ModTags {

    public static final Tag.Named<Block> MINEABLE_WITH_DIGGING_CLAWS = create(ForgeRegistries.BLOCKS, "mineable/digging_claws");
    public static final Tag.Named<MobEffect> ANTIDOTE_VESSEL_CANCELLABLE = create(ForgeRegistries.MOB_EFFECTS, "antidote_vessel_cancellable");

    private static <T extends IForgeRegistryEntry<T>> Tag.Named<T> create(IForgeRegistry<T> registry, String id) {
        return create(registry, new ResourceLocation(Artifacts.MODID, id));
    }

    private static <T extends IForgeRegistryEntry<T>> Tag.Named<T> create(IForgeRegistry<T> registry, ResourceLocation id) {
        return ForgeTagHandler.createOptionalTag(registry, id);
    }
}
