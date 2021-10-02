package artifacts.client.render.curio;

import artifacts.client.render.curio.model.*;
import artifacts.client.render.curio.renderer.*;
import artifacts.common.init.ModItems;
import net.minecraft.client.renderer.RenderType;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class CurioRenderers {

    public static void register() {
        // head
        CuriosRendererRegistry.register(ModItems.PLASTIC_DRINKING_HAT.get(), () -> new CurioRenderer("drinking_hat/plastic_drinking_hat", HeadModel.drinkingHat()));
        CuriosRendererRegistry.register(ModItems.NOVELTY_DRINKING_HAT.get(), () -> new CurioRenderer("drinking_hat/novelty_drinking_hat", HeadModel.drinkingHat()));
        CuriosRendererRegistry.register(ModItems.SNORKEL.get(), () -> new CurioRenderer("snorkel", HeadModel.snorkel()));
        CuriosRendererRegistry.register(ModItems.NIGHT_VISION_GOGGLES.get(), () -> new GlowingCurioRenderer("night_vision_goggles", HeadModel.nightVisionGoggles()));
        CuriosRendererRegistry.register(ModItems.SUPERSTITIOUS_HAT.get(), () -> new CurioRenderer("superstitious_hat", HeadModel.superstitiousHat()));
        CuriosRendererRegistry.register(ModItems.VILLAGER_HAT.get(), () -> new CurioRenderer("villager_hat", HeadModel.villagerHat()));

        // necklace
        CuriosRendererRegistry.register(ModItems.LUCKY_SCARF.get(), () -> new CurioRenderer("scarf/lucky_scarf", ScarfModel.scarf(RenderType::entityCutoutNoCull)));
        CuriosRendererRegistry.register(ModItems.SCARF_OF_INVISIBILITY.get(), () -> new CurioRenderer("scarf/scarf_of_invisibility", ScarfModel.scarf(RenderType::entityTranslucent)));
        CuriosRendererRegistry.register(ModItems.CROSS_NECKLACE.get(), () -> new CurioRenderer("cross_necklace", NecklaceModel.crossNecklace()));
        CuriosRendererRegistry.register(ModItems.PANIC_NECKLACE.get(), () -> new CurioRenderer("panic_necklace", NecklaceModel.panicNecklace()));
        CuriosRendererRegistry.register(ModItems.SHOCK_PENDANT.get(), () -> new CurioRenderer("pendant/shock_pendant", NecklaceModel.pendant()));
        CuriosRendererRegistry.register(ModItems.FLAME_PENDANT.get(), () -> new CurioRenderer("pendant/flame_pendant", NecklaceModel.pendant()));
        CuriosRendererRegistry.register(ModItems.THORN_PENDANT.get(), () -> new CurioRenderer("pendant/thorn_pendant", NecklaceModel.pendant()));
        CuriosRendererRegistry.register(ModItems.CHARM_OF_SINKING.get(), () -> new CurioRenderer("charm_of_sinking", NecklaceModel.charmOfSinking()));

        // belt
        CuriosRendererRegistry.register(ModItems.CLOUD_IN_A_BOTTLE.get(), () -> new BeltCurioRenderer("cloud_in_a_bottle", BeltModel.cloudInABottle()));
        CuriosRendererRegistry.register(ModItems.OBSIDIAN_SKULL.get(), () -> new BeltCurioRenderer("obsidian_skull", BeltModel.obsidianSkull()));
        CuriosRendererRegistry.register(ModItems.ANTIDOTE_VESSEL.get(), () -> new BeltCurioRenderer("antidote_vessel", BeltModel.antidoteVessel()));
        CuriosRendererRegistry.register(ModItems.UNIVERSAL_ATTRACTOR.get(), () -> new BeltCurioRenderer("universal_attractor", BeltModel.universalAttractor()));
        CuriosRendererRegistry.register(ModItems.CRYSTAL_HEART.get(), () -> new BeltCurioRenderer("crystal_heart", BeltModel.crystalHeart()));
        CuriosRendererRegistry.register(ModItems.HELIUM_FLAMINGO.get(), () -> new CurioRenderer("helium_flamingo", BeltModel.heliumFlamingo()));

        // hands
        CuriosRendererRegistry.register(ModItems.DIGGING_CLAWS.get(), () -> new GloveCurioRenderer("claws/digging_claws", "claws/digging_claws", HandsModel::claws));
        CuriosRendererRegistry.register(ModItems.FERAL_CLAWS.get(), () -> new GloveCurioRenderer("claws/feral_claws", "claws/feral_claws", HandsModel::claws));
        CuriosRendererRegistry.register(ModItems.POWER_GLOVE.get(), () -> new GloveCurioRenderer("power_glove"));
        CuriosRendererRegistry.register(ModItems.FIRE_GAUNTLET.get(), () -> new GlowingGloveCurioRenderer("fire_gauntlet"));
        CuriosRendererRegistry.register(ModItems.POCKET_PISTON.get(), () -> new GloveCurioRenderer("pocket_piston"));
        CuriosRendererRegistry.register(ModItems.VAMPIRIC_GLOVE.get(), () -> new GloveCurioRenderer("vampiric_glove"));
        CuriosRendererRegistry.register(ModItems.GOLDEN_HOOK.get(), () -> new GloveCurioRenderer("golden_hook", HandsModel::goldenHook));

        // feet
        CuriosRendererRegistry.register(ModItems.AQUA_DASHERS.get(), () -> new CurioRenderer("aqua_dashers", LegsModel.aquaDashers(1.25F)));
        CuriosRendererRegistry.register(ModItems.BUNNY_HOPPERS.get(), () -> new CurioRenderer("bunny_hoppers", LegsModel.bunnyHoppers()));
        CuriosRendererRegistry.register(ModItems.KITTY_SLIPPERS.get(), () -> new CurioRenderer("kitty_slippers", LegsModel.kittySlippers()));
        CuriosRendererRegistry.register(ModItems.RUNNING_SHOES.get(), () -> new CurioRenderer("running_shoes", LegsModel.shoes(0.5F)));
        CuriosRendererRegistry.register(ModItems.STEADFAST_SPIKES.get(), () -> new CurioRenderer("steadfast_spikes", LegsModel.steadfastSpikes()));
        CuriosRendererRegistry.register(ModItems.FLIPPERS.get(), () -> new CurioRenderer("flippers", LegsModel.flippers()));

        // curio
        CuriosRendererRegistry.register(ModItems.WHOOPEE_CUSHION.get(), () -> new CurioRenderer("whoopee_cushion", HeadModel.whoopeeCushion()));
    }
}
