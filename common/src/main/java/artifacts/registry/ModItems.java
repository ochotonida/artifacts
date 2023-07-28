package artifacts.registry;

import artifacts.Artifacts;
import artifacts.item.wearable.WearableArtifactItem;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Artifacts.MOD_ID, Registries.ITEM);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Artifacts.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("main", () ->
            CreativeTabRegistry.create(
                    Component.translatable("%s.creative_tab".formatted(Artifacts.MOD_ID)),
                    () -> new ItemStack(ModItems.BUNNY_HOPPERS.get())
            )
    );

    public static RegistrySupplier<Item> MIMIC_SPAWN_EGG;
    public static RegistrySupplier<Item> UMBRELLA;
    public static RegistrySupplier<Item> EVERLASTING_BEEF;
    public static RegistrySupplier<Item> ETERNAL_STEAK;

    // head
    public static RegistrySupplier<WearableArtifactItem> PLASTIC_DRINKING_HAT;
    public static RegistrySupplier<WearableArtifactItem> NOVELTY_DRINKING_HAT;
    public static RegistrySupplier<WearableArtifactItem> SNORKEL;
    public static RegistrySupplier<WearableArtifactItem> NIGHT_VISION_GOGGLES;
    public static RegistrySupplier<WearableArtifactItem> VILLAGER_HAT;
    public static RegistrySupplier<WearableArtifactItem> SUPERSTITIOUS_HAT;

    // necklace
    public static RegistrySupplier<WearableArtifactItem> LUCKY_SCARF;
    public static RegistrySupplier<WearableArtifactItem> SCARF_OF_INVISIBILITY;
    public static RegistrySupplier<WearableArtifactItem> CROSS_NECKLACE;
    public static RegistrySupplier<WearableArtifactItem> PANIC_NECKLACE;
    public static RegistrySupplier<WearableArtifactItem> SHOCK_PENDANT;
    public static RegistrySupplier<WearableArtifactItem> FLAME_PENDANT;
    public static RegistrySupplier<WearableArtifactItem> THORN_PENDANT;
    public static RegistrySupplier<WearableArtifactItem> CHARM_OF_SINKING;

    // belt
    public static RegistrySupplier<WearableArtifactItem> CLOUD_IN_A_BOTTLE;
    public static RegistrySupplier<WearableArtifactItem> OBSIDIAN_SKULL;
    public static RegistrySupplier<WearableArtifactItem> ANTIDOTE_VESSEL;
    public static RegistrySupplier<WearableArtifactItem> UNIVERSAL_ATTRACTOR;
    public static RegistrySupplier<WearableArtifactItem> CRYSTAL_HEART;
    public static RegistrySupplier<WearableArtifactItem> HELIUM_FLAMINGO;

    // hands
    public static RegistrySupplier<WearableArtifactItem> DIGGING_CLAWS;
    public static RegistrySupplier<WearableArtifactItem> FERAL_CLAWS;
    public static RegistrySupplier<WearableArtifactItem> POWER_GLOVE;
    public static RegistrySupplier<WearableArtifactItem> FIRE_GAUNTLET;
    public static RegistrySupplier<WearableArtifactItem> POCKET_PISTON;
    public static RegistrySupplier<WearableArtifactItem> VAMPIRIC_GLOVE;
    public static RegistrySupplier<WearableArtifactItem> GOLDEN_HOOK;

    // feet
    public static RegistrySupplier<WearableArtifactItem> AQUA_DASHERS;
    public static RegistrySupplier<WearableArtifactItem> BUNNY_HOPPERS;
    public static RegistrySupplier<WearableArtifactItem> KITTY_SLIPPERS;
    public static RegistrySupplier<WearableArtifactItem> RUNNING_SHOES;
    public static RegistrySupplier<WearableArtifactItem> STEADFAST_SPIKES;
    public static RegistrySupplier<WearableArtifactItem> FLIPPERS;

    // curio
    public static RegistrySupplier<WearableArtifactItem> WHOOPEE_CUSHION;

    @SuppressWarnings("UnstableApiUsage")
    public static Item.Properties createProperties() {
        return new Item.Properties().arch$tab(CREATIVE_TAB);
    }
}
