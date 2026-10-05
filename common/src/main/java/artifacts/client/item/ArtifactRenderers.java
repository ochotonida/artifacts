package artifacts.client.item;

import artifacts.client.item.model.*;
import artifacts.client.item.renderer.*;
import artifacts.equipment.client.EquipmentRenderingManager;
import artifacts.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ArtifactRenderers {

    public static void register() {
        // head
        register(ModItems.PLASTIC_DRINKING_HAT.value(), () -> SimpleArtifactRenderer.create("plastic_drinking_hat", new HumanoidModel<>(bakeLayer(ArtifactLayers.DRINKING_HAT), RenderTypes::armorCutoutNoCull)));
        register(ModItems.NOVELTY_DRINKING_HAT.value(), () -> SimpleArtifactRenderer.create("novelty_drinking_hat", new HumanoidModel<>(bakeLayer(ArtifactLayers.DRINKING_HAT), RenderTypes::armorCutoutNoCull)));
        register(ModItems.SNORKEL.value(), () -> SimpleArtifactRenderer.create("snorkel", new HumanoidModel<>(bakeLayer(ArtifactLayers.SNORKEL), RenderTypes::entityTranslucent)));
        register(ModItems.NIGHT_VISION_GOGGLES.value(), () -> SimpleArtifactRenderer.createGlowing("night_vision_goggles", new HumanoidModel<>(bakeLayer(ArtifactLayers.NIGHT_VISION_GOGGLES), RenderTypes::armorCutoutNoCull)));
        register(ModItems.SUPERSTITIOUS_HAT.value(), () -> SimpleArtifactRenderer.create("superstitious_hat", new HumanoidModel<>(bakeLayer(ArtifactLayers.SUPERSTITIOUS_HAT), RenderTypes::armorCutoutNoCull)));
        register(ModItems.VILLAGER_HAT.value(), () -> SimpleArtifactRenderer.create("villager_hat", new HumanoidModel<>(bakeLayer(ArtifactLayers.BRIMMED_HAT), RenderTypes::armorCutoutNoCull)));
        register(ModItems.COWBOY_HAT.value(), () -> SimpleArtifactRenderer.create("cowboy_hat", new HumanoidModel<>(bakeLayer(ArtifactLayers.COWBOY_HAT), RenderTypes::armorCutoutNoCull)));
        register(ModItems.ANGLERS_HAT.value(), () -> SimpleArtifactRenderer.create("anglers_hat", new HumanoidModel<>(bakeLayer(ArtifactLayers.ANGLERS_HAT), RenderTypes::armorCutoutNoCull)));

        // necklace
        register(ModItems.LUCKY_SCARF.value(), () -> SimpleArtifactRenderer.create("lucky_scarf", new ScarfModel(bakeLayer(ArtifactLayers.SCARF))));
        register(ModItems.SCARF_OF_INVISIBILITY.value(), () -> SimpleArtifactRenderer.create("scarf_of_invisibility",  new ScarfModel(bakeLayer(ArtifactLayers.SCARF), RenderTypes::entityTranslucent))); // TODO check if glint still works on these
        register(ModItems.CROSS_NECKLACE.value(), () -> SimpleArtifactRenderer.create("cross_necklace", new HumanoidModel<>(bakeLayer(ArtifactLayers.CROSS_NECKLACE), RenderTypes::entityTranslucent)));
        register(ModItems.PANIC_NECKLACE.value(), () -> SimpleArtifactRenderer.create("panic_necklace", new HumanoidModel<>(bakeLayer(ArtifactLayers.PANIC_NECKLACE), RenderTypes::entityTranslucent)));
        register(ModItems.SHOCK_PENDANT.value(), () -> SimpleArtifactRenderer.create("shock_pendant", new HumanoidModel<>(bakeLayer(ArtifactLayers.PENDANT), RenderTypes::entityTranslucent)));
        register(ModItems.FLAME_PENDANT.value(), () -> SimpleArtifactRenderer.create("flame_pendant", new HumanoidModel<>(bakeLayer(ArtifactLayers.PENDANT), RenderTypes::entityTranslucent)));
        register(ModItems.THORN_PENDANT.value(), () -> SimpleArtifactRenderer.create("thorn_pendant", new HumanoidModel<>(bakeLayer(ArtifactLayers.PENDANT), RenderTypes::entityTranslucent)));
        register(ModItems.CHARM_OF_SINKING.value(), () -> SimpleArtifactRenderer.create("charm_of_sinking", new HumanoidModel<>(bakeLayer(ArtifactLayers.CHARM_OF_SINKING), RenderTypes::entityTranslucent)));
        register(ModItems.CHARM_OF_SHRINKING.value(), () -> SimpleArtifactRenderer.create("charm_of_shrinking", new HumanoidModel<>(bakeLayer(ArtifactLayers.CHARM_OF_SHRINKING), RenderTypes::entityTranslucent)));

        // belt
        register(ModItems.CLOUD_IN_A_BOTTLE.value(), () -> BeltArtifactRenderer.create("cloud_in_a_bottle", CloudInABottleModel.create(ArtifactRenderers.bakeLayer(ArtifactLayers.CLOUD_IN_A_BOTTLE), RenderTypes::entityTranslucent, CharmPose.CLOUD_IN_A_BOTTLE)));
        register(ModItems.OBSIDIAN_SKULL.value(), () -> BeltArtifactRenderer.create("obsidian_skull", BeltModel.create(ArtifactRenderers.bakeLayer(ArtifactLayers.OBSIDIAN_SKULL), CharmPose.OBSIDIAN_SKULL)));
        register(ModItems.ANTIDOTE_VESSEL.value(), () -> BeltArtifactRenderer.create("antidote_vessel", BeltModel.create(ArtifactRenderers.bakeLayer(ArtifactLayers.ANTIDOTE_VESSEL), CharmPose.ANTIDOTE_VESSEL)));
        register(ModItems.UNIVERSAL_ATTRACTOR.value(), () -> BeltArtifactRenderer.create("universal_attractor", BeltModel.create(ArtifactRenderers.bakeLayer(ArtifactLayers.UNIVERSAL_ATTRACTOR), CharmPose.UNIVERSAL_ATTRACTOR)));
        register(ModItems.CRYSTAL_HEART.value(), () -> BeltArtifactRenderer.create("crystal_heart", BeltModel.create(ArtifactRenderers.bakeLayer(ArtifactLayers.CRYSTAL_HEART), RenderTypes::entityTranslucent, CharmPose.CRYSTAL_HEART)));
        register(ModItems.HELIUM_FLAMINGO.value(), () -> SimpleArtifactRenderer.create("helium_flamingo", new HumanoidModel<>(ArtifactRenderers.bakeLayer(ArtifactLayers.HELIUM_FLAMINGO), RenderTypes::armorCutoutNoCull)));
        register(ModItems.CHORUS_TOTEM.value(), () -> BeltArtifactRenderer.create("chorus_totem", BeltModel.create(ArtifactRenderers.bakeLayer(ArtifactLayers.CHORUS_TOTEM), CharmPose.CHORUS_TOTEM)));
        register(ModItems.WARP_DRIVE.value(), () -> new WarpDriveRenderer("warp_drive", BeltModel.create(ArtifactRenderers.bakeLayer(ArtifactLayers.WARP_DRIVE), CharmPose.WARP_DRIVE)));

        // hands
        register(ModItems.DIGGING_CLAWS.value(), () -> GloveArtifactRenderer.create("digging_claws", "digging_claws", ArmsModelSet.bake(ArtifactLayers.CLAWS)));
        register(ModItems.FERAL_CLAWS.value(), () -> GloveArtifactRenderer.create("feral_claws", "feral_claws", ArmsModelSet.bake(ArtifactLayers.CLAWS)));
        register(ModItems.POWER_GLOVE.value(), () -> GloveArtifactRenderer.create("power_glove", ArmsModelSet.bake(ArtifactLayers.GLOVE)));
        register(ModItems.FIRE_GAUNTLET.value(), () -> GloveArtifactRenderer.createGlowing("fire_gauntlet", ArmsModelSet.bake(ArtifactLayers.GLOVE)));
        register(ModItems.POCKET_PISTON.value(), () -> GloveArtifactRenderer.create("pocket_piston", ArmsModelSet.bake(ArtifactLayers.POCKET_PISTON, PocketPistonModel::new)));
        register(ModItems.VAMPIRIC_GLOVE.value(), () -> GloveArtifactRenderer.create("vampiric_glove", ArmsModelSet.bake(ArtifactLayers.GLOVE)));
        register(ModItems.GOLDEN_HOOK.value(), () -> GloveArtifactRenderer.create("golden_hook", ArmsModelSet.bake(ArtifactLayers.GOLDEN_HOOK)));
        register(ModItems.ONION_RING.value(), () -> GloveArtifactRenderer.create("onion_ring", ArmsModelSet.bake(ArtifactLayers.ONION_RING)));
        register(ModItems.PICKAXE_HEATER.value(), () -> GloveArtifactRenderer.createGlowing("pickaxe_heater", ArmsModelSet.bake(ArtifactLayers.PICKAXE_HEATER)));
        register(ModItems.WITHERED_BRACELET.value(), () -> GloveArtifactRenderer.create("withered_bracelet", ArmsModelSet.bake(ArtifactLayers.WITHERED_BRACELET)));

        // feet
        register(ModItems.AQUA_DASHERS.value(), () -> new BootArtifactRenderer("aqua_dashers", hasArmor -> new HumanoidModel<>(bakeLayer(hasArmor ? ArtifactLayers.AQUA_DASHERS_LARGE : ArtifactLayers.AQUA_DASHERS_SMALL), RenderTypes::armorCutoutNoCull)));
        register(ModItems.BUNNY_HOPPERS.value(), () -> SimpleArtifactRenderer.create("bunny_hoppers", new HumanoidModel<>(bakeLayer(ArtifactLayers.BUNNY_HOPPERS), RenderTypes::armorCutoutNoCull)));
        register(ModItems.KITTY_SLIPPERS.value(), () -> SimpleArtifactRenderer.create("kitty_slippers", new HumanoidModel<>(bakeLayer(ArtifactLayers.KITTY_SLIPPERS), RenderTypes::armorCutoutNoCull)));
        register(ModItems.RUNNING_SHOES.value(), () -> new BootArtifactRenderer("running_shoes", hasArmor -> new HumanoidModel<>(bakeLayer(hasArmor ? ArtifactLayers.BOOTS_LARGE : ArtifactLayers.BOOTS_SMALL), RenderTypes::armorCutoutNoCull)));
        register(ModItems.SNOWSHOES.value(), () -> SimpleArtifactRenderer.create("snowshoes", new HumanoidModel<>(bakeLayer(ArtifactLayers.SNOWSHOES), RenderTypes::armorCutoutNoCull)));
        register(ModItems.STEADFAST_SPIKES.value(), () -> SimpleArtifactRenderer.create("steadfast_spikes", new HumanoidModel<>(bakeLayer(ArtifactLayers.STEADFAST_SPIKES), RenderTypes::armorCutoutNoCull)));
        register(ModItems.FLIPPERS.value(), () -> SimpleArtifactRenderer.create("flippers", new HumanoidModel<>(bakeLayer(ArtifactLayers.FLIPPERS), RenderTypes::armorCutoutNoCull)));
        register(ModItems.ROOTED_BOOTS.value(), () -> new BootArtifactRenderer("rooted_boots", hasArmor -> new HumanoidModel<>(bakeLayer(hasArmor ? ArtifactLayers.BOOTS_LARGE : ArtifactLayers.BOOTS_SMALL), RenderTypes::armorCutoutNoCull)));
        register(ModItems.STRIDER_SHOES.value(), () -> new BootArtifactRenderer("strider_shoes", hasArmor -> new HumanoidModel<>(bakeLayer(hasArmor ? ArtifactLayers.BOOTS_LARGE : ArtifactLayers.BOOTS_SMALL), RenderTypes::armorCutoutNoCull)));

        // curio
        register(ModItems.WHOOPEE_CUSHION.value(), () -> SimpleArtifactRenderer.create("whoopee_cushion", new HumanoidModel<>(bakeLayer(ArtifactLayers.WHOOPEE_CUSHION), RenderTypes::armorCutoutNoCull)));
    }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }

    public static void register(Item item, Supplier<ArtifactRenderer> rendererFactory) {
        EquipmentRenderingManager.registerArtifactRenderer(item, rendererFactory);
    }
}
