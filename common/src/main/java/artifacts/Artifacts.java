package artifacts;

import artifacts.config.ModConfig;
import artifacts.registry.ModLootConditions;
import artifacts.registry.ModPlacementModifierTypes;
import artifacts.registry.ModSoundEvents;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class Artifacts {

    public static final String MOD_ID = "artifacts";
    public static ModConfig CONFIG;

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static ResourceLocation id(String path, String... args) {
        return new ResourceLocation(MOD_ID, String.format(path, (Object[]) args));
    }

    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String path) {
        return ResourceKey.create(registry, id(path));
    }

    public static void init() {
        AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        ModSoundEvents.SOUND_EVENTS.register();
        ModLootConditions.LOOT_CONDITIONS.register();
        ModPlacementModifierTypes.PLACEMENT_MODIFIERS.register();
    }
}
