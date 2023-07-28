package artifacts.forge.client.item;

import artifacts.forge.client.item.model.*;
import artifacts.forge.client.item.renderer.*;
import artifacts.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class ArtifactRenderers {

    public static void register() {
        // head
        CuriosRendererRegistry.register(ModItems.PLASTIC_DRINKING_HAT.get(), () -> new ArtifactRenderer("drinking_hat/plastic_drinking_hat", new HeadModel(bakeLayer(ArtifactLayers.DRINKING_HAT))));
        CuriosRendererRegistry.register(ModItems.NOVELTY_DRINKING_HAT.get(), () -> new ArtifactRenderer("drinking_hat/novelty_drinking_hat", new HeadModel(bakeLayer(ArtifactLayers.DRINKING_HAT))));
        CuriosRendererRegistry.register(ModItems.SNORKEL.get(), () -> new ArtifactRenderer("snorkel", new HeadModel(bakeLayer(ArtifactLayers.SNORKEL), RenderType::entityTranslucent)));
        CuriosRendererRegistry.register(ModItems.NIGHT_VISION_GOGGLES.get(), () -> new GlowingArtifactRenderer("night_vision_goggles", new HeadModel(bakeLayer(ArtifactLayers.NIGHT_VISION_GOGGLES))));
        CuriosRendererRegistry.register(ModItems.SUPERSTITIOUS_HAT.get(), () -> new ArtifactRenderer("superstitious_hat", new HeadModel(bakeLayer(ArtifactLayers.SUPERSTITIOUS_HAT), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.VILLAGER_HAT.get(), () -> new ArtifactRenderer("villager_hat", new HeadModel(bakeLayer(ArtifactLayers.VILLAGER_HAT))));

        // necklace
        CuriosRendererRegistry.register(ModItems.LUCKY_SCARF.get(), () -> new ArtifactRenderer("scarf/lucky_scarf", new ScarfModel(bakeLayer(ArtifactLayers.SCARF), RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.SCARF_OF_INVISIBILITY.get(), () -> new ArtifactRenderer("scarf/scarf_of_invisibility",  new ScarfModel(bakeLayer(ArtifactLayers.SCARF), RenderType::entityTranslucent)));
        CuriosRendererRegistry.register(ModItems.CROSS_NECKLACE.get(), () -> new ArtifactRenderer("cross_necklace", new NecklaceModel(bakeLayer(ArtifactLayers.CROSS_NECKLACE))));
        CuriosRendererRegistry.register(ModItems.PANIC_NECKLACE.get(), () -> new ArtifactRenderer("panic_necklace", new NecklaceModel(bakeLayer(ArtifactLayers.PANIC_NECKLACE))));
        CuriosRendererRegistry.register(ModItems.SHOCK_PENDANT.get(), () -> new ArtifactRenderer("pendant/shock_pendant", new NecklaceModel(bakeLayer(ArtifactLayers.PENDANT))));
        CuriosRendererRegistry.register(ModItems.FLAME_PENDANT.get(), () -> new ArtifactRenderer("pendant/flame_pendant", new NecklaceModel(bakeLayer(ArtifactLayers.PENDANT))));
        CuriosRendererRegistry.register(ModItems.THORN_PENDANT.get(), () -> new ArtifactRenderer("pendant/thorn_pendant", new NecklaceModel(bakeLayer(ArtifactLayers.PENDANT))));
        CuriosRendererRegistry.register(ModItems.CHARM_OF_SINKING.get(), () -> new ArtifactRenderer("charm_of_sinking", new NecklaceModel(bakeLayer(ArtifactLayers.CHARM_OF_SINKING))));

        // belt
        CuriosRendererRegistry.register(ModItems.CLOUD_IN_A_BOTTLE.get(), () -> new BeltArtifactRenderer("cloud_in_a_bottle", BeltModel.createCloudInABottleModel()));
        CuriosRendererRegistry.register(ModItems.OBSIDIAN_SKULL.get(), () -> new BeltArtifactRenderer("obsidian_skull", BeltModel.createObsidianSkullModel()));
        CuriosRendererRegistry.register(ModItems.ANTIDOTE_VESSEL.get(), () -> new BeltArtifactRenderer("antidote_vessel", BeltModel.createAntidoteVesselModel()));
        CuriosRendererRegistry.register(ModItems.UNIVERSAL_ATTRACTOR.get(), () -> new BeltArtifactRenderer("universal_attractor", BeltModel.createUniversalAttractorModel()));
        CuriosRendererRegistry.register(ModItems.CRYSTAL_HEART.get(), () -> new BeltArtifactRenderer("crystal_heart", BeltModel.createCrystalHeartModel()));
        CuriosRendererRegistry.register(ModItems.HELIUM_FLAMINGO.get(), () -> new ArtifactRenderer("helium_flamingo", BeltModel.createHeliumFlamingoModel()));

        // hands
        CuriosRendererRegistry.register(ModItems.DIGGING_CLAWS.get(), () -> new GloveArtifactRenderer("claws/digging_claws", "claws/digging_claws", ArmsModel.createClawsModel(false), ArmsModel.createClawsModel(true)));
        CuriosRendererRegistry.register(ModItems.FERAL_CLAWS.get(), () -> new GloveArtifactRenderer("claws/feral_claws", "claws/feral_claws", ArmsModel.createClawsModel(false), ArmsModel.createClawsModel(true)));
        CuriosRendererRegistry.register(ModItems.POWER_GLOVE.get(), () -> new GloveArtifactRenderer("power_glove", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        CuriosRendererRegistry.register(ModItems.FIRE_GAUNTLET.get(), () -> new GlowingGloveArtifactRenderer("fire_gauntlet", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        CuriosRendererRegistry.register(ModItems.POCKET_PISTON.get(), () -> new GloveArtifactRenderer("pocket_piston", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        CuriosRendererRegistry.register(ModItems.VAMPIRIC_GLOVE.get(), () -> new GloveArtifactRenderer("vampiric_glove", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        CuriosRendererRegistry.register(ModItems.GOLDEN_HOOK.get(), () -> new GloveArtifactRenderer("golden_hook/golden_hook_default", "golden_hook/golden_hook_slim", ArmsModel.createGoldenHookModel(false), ArmsModel.createGoldenHookModel(true)));

        // feet
        CuriosRendererRegistry.register(ModItems.AQUA_DASHERS.get(), () -> new ArtifactRenderer("aqua_dashers", new LegsModel(bakeLayer(ArtifactLayers.AQUA_DASHERS))));
        CuriosRendererRegistry.register(ModItems.BUNNY_HOPPERS.get(), () -> new ArtifactRenderer("bunny_hoppers", new LegsModel(bakeLayer(ArtifactLayers.BUNNY_HOPPERS))));
        CuriosRendererRegistry.register(ModItems.KITTY_SLIPPERS.get(), () -> new ArtifactRenderer("kitty_slippers", new LegsModel(bakeLayer(ArtifactLayers.KITTY_SLIPPERS))));
        CuriosRendererRegistry.register(ModItems.RUNNING_SHOES.get(), () -> new ArtifactRenderer("running_shoes", new LegsModel(bakeLayer(ArtifactLayers.RUNNING_SHOES))));
        CuriosRendererRegistry.register(ModItems.STEADFAST_SPIKES.get(), () -> new ArtifactRenderer("steadfast_spikes", new LegsModel(bakeLayer(ArtifactLayers.STEADFAST_SPIKES))));
        CuriosRendererRegistry.register(ModItems.FLIPPERS.get(), () -> new ArtifactRenderer("flippers", new LegsModel(bakeLayer(ArtifactLayers.FLIPPERS))));

        // curio
        CuriosRendererRegistry.register(ModItems.WHOOPEE_CUSHION.get(), () -> new ArtifactRenderer("whoopee_cushion", new HeadModel(bakeLayer(ArtifactLayers.WHOOPEE_CUSHION))));
    }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }
}
