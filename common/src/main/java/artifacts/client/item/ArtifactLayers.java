package artifacts.client.item;

import artifacts.Artifacts;
import artifacts.client.item.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ArtifactLayers {

    public static final ModelLayerLocation
            DRINKING_HAT = createLayerLocation("drinking_hat"),
            SNORKEL = createLayerLocation("snorkel"),
            NIGHT_VISION_GOGGLES = createLayerLocation("night_vision_goggles"),
            SUPERSTITIOUS_HAT = createLayerLocation("superstitious_hat"),
            VILLAGER_HAT = createLayerLocation("villager_hat"),

            SCARF = createLayerLocation("scarf"),
            CROSS_NECKLACE = createLayerLocation("cross_necklace"),
            PANIC_NECKLACE = createLayerLocation("panic_necklace"),
            PENDANT = createLayerLocation("pendant"),
            CHARM_OF_SINKING = createLayerLocation("charm_of_sinking"),

            CLOUD_IN_A_BOTTLE = createLayerLocation("cloud_in_a_bottle"),
            OBSIDIAN_SKULL = createLayerLocation("obsidian_skull"),
            ANTIDOTE_VESSEL = createLayerLocation("antidote_vessel"),
            UNIVERSAL_ATTRACTOR = createLayerLocation("universal_attractor"),
            CRYSTAL_HEART = createLayerLocation("crystal_heart"),
            HELIUM_FLAMINGO = createLayerLocation("helium_flamingo"),

            CLAWS = createLayerLocation("claws"),
            SLIM_CLAWS = createLayerLocation("slim_claws"),
            GLOVE = createLayerLocation("gloves"),
            SLIM_GLOVE = createLayerLocation("slim_gloves"),
            GOLDEN_HOOK = createLayerLocation("golden_hook"),
            SLIM_GOLDEN_HOOK = createLayerLocation("slim_golden_hook"),

            AQUA_DASHERS = createLayerLocation("aqua_dashers"),
            BUNNY_HOPPERS = createLayerLocation("bunny_hoppers"),
            KITTY_SLIPPERS = createLayerLocation("kitty_slippers"),
            RUNNING_SHOES = createLayerLocation("running_shoes"),
            STEADFAST_SPIKES = createLayerLocation("steadfast_spikes"),
            FLIPPERS = createLayerLocation("flippers"),

            WHOOPEE_CUSHION = createLayerLocation("whoopee_cushion");

    public static ModelLayerLocation claws(boolean smallArms) {
        return smallArms ? SLIM_CLAWS : CLAWS;
    }

    public static ModelLayerLocation glove(boolean smallArms) {
        return smallArms ? SLIM_GLOVE : GLOVE;
    }

    public static ModelLayerLocation goldenHook(boolean smallArms) {
        return smallArms ? SLIM_GOLDEN_HOOK : GOLDEN_HOOK;
    }

    public static ModelLayerLocation createLayerLocation(String name) {
        return new ModelLayerLocation(Artifacts.id(name), name);
    }

    private static Supplier<LayerDefinition> layer(MeshDefinition mesh, int textureWidth, int textureHeight) {
        return () -> LayerDefinition.create(mesh, textureWidth, textureHeight);
    }

    public static void register(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> registry) {
        registry.accept(DRINKING_HAT, layer(HeadModel.createDrinkingHat(), 64, 32));
        registry.accept(SNORKEL, layer(HeadModel.createSnorkel(), 64, 32));
        registry.accept(NIGHT_VISION_GOGGLES, layer(HeadModel.createNightVisionGoggles(), 32, 32));
        registry.accept(SUPERSTITIOUS_HAT, layer(HeadModel.createSuperstitiousHat(), 64, 32));
        registry.accept(VILLAGER_HAT, layer(HeadModel.createVillagerHat(), 32, 32));

        registry.accept(SCARF, layer(ScarfModel.createScarf(), 64, 32));
        registry.accept(CROSS_NECKLACE, layer(NecklaceModel.createCrossNecklace(), 64, 48));
        registry.accept(PANIC_NECKLACE, layer(NecklaceModel.createPanicNecklace(), 64, 48));
        registry.accept(PENDANT, layer(NecklaceModel.createPendant(), 64, 48));
        registry.accept(CHARM_OF_SINKING, layer(NecklaceModel.createCharmOfSinking(), 64, 48));

        registry.accept(CLOUD_IN_A_BOTTLE, layer(BeltModel.createCloudInABottle(), 32, 32));
        registry.accept(OBSIDIAN_SKULL, layer(BeltModel.createObsidianSkull(), 32, 32));
        registry.accept(ANTIDOTE_VESSEL, layer(BeltModel.createAntidoteVessel(), 32, 32));
        registry.accept(UNIVERSAL_ATTRACTOR, layer(BeltModel.createUniversalAttractor(), 32, 32));
        registry.accept(CRYSTAL_HEART, layer(BeltModel.createCrystalHeart(), 32, 32));
        registry.accept(HELIUM_FLAMINGO, layer(BeltModel.createHeliumFlamingo(), 64, 64));

        registry.accept(CLAWS, layer(ArmsModel.createClaws(false), 32, 16));
        registry.accept(SLIM_CLAWS, layer(ArmsModel.createClaws(true), 32, 16));
        registry.accept(GLOVE, layer(ArmsModel.createSleevedArms(false), 32, 32));
        registry.accept(SLIM_GLOVE, layer(ArmsModel.createSleevedArms(true), 32, 32));
        registry.accept(GOLDEN_HOOK, layer(ArmsModel.createGoldenHook(false), 64, 32));
        registry.accept(SLIM_GOLDEN_HOOK, layer(ArmsModel.createGoldenHook(true), 64, 32));

        registry.accept(AQUA_DASHERS, layer(LegsModel.createAquaDashers(), 32, 32));
        registry.accept(BUNNY_HOPPERS, layer(LegsModel.createBunnyHoppers(), 64, 32));
        registry.accept(KITTY_SLIPPERS, layer(LegsModel.createKittySlippers(), 64, 32));
        registry.accept(RUNNING_SHOES, layer(LegsModel.createRunningShoes(), 32, 32));
        registry.accept(STEADFAST_SPIKES, layer(LegsModel.createSteadfastSpikes(), 64, 32));
        registry.accept(FLIPPERS, layer(LegsModel.createFlippers(), 64, 64));

        registry.accept(WHOOPEE_CUSHION, layer(HeadModel.createWhoopeeCushion(), 32, 16));
    }
}
