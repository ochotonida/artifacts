package artifacts.client.render.curio;

import artifacts.client.render.curio.model.WhoopeeCushionModel;
import artifacts.client.render.curio.model.belt.*;
import artifacts.client.render.curio.model.feet.*;
import artifacts.client.render.curio.model.hands.ClawsModel;
import artifacts.client.render.curio.model.hands.GoldenHookModel;
import artifacts.client.render.curio.model.head.*;
import artifacts.client.render.curio.model.necklace.*;
import artifacts.client.render.curio.renderer.*;
import artifacts.common.init.ModItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CurioRenderers {

    private static final Map<Item, CurioRenderer> renderers = new HashMap<>();

    public static CurioRenderer getRenderer(Item curio) {
        return renderers.get(curio);
    }

    public static GloveCurioRenderer getGloveRenderer(ItemStack stack) {
        if (!stack.isEmpty()) {
            CurioRenderer renderer = getRenderer(stack.getItem());
            if (renderer instanceof GloveCurioRenderer) {
                return ((GloveCurioRenderer) renderer);
            }
        }
        return null;
    }

    public static void setupCurioRenderers() {
        // head
        renderers.put(ModItems.PLASTIC_DRINKING_HAT.get(), new SimpleCurioRenderer("drinking_hat/plastic_drinking_hat", new DrinkingHatModel()));
        renderers.put(ModItems.NOVELTY_DRINKING_HAT.get(), new SimpleCurioRenderer("drinking_hat/novelty_drinking_hat", new DrinkingHatModel()));
        renderers.put(ModItems.SNORKEL.get(), new SimpleCurioRenderer("snorkel", new SnorkelModel()));
        renderers.put(ModItems.NIGHT_VISION_GOGGLES.get(), new GlowingCurioRenderer("night_vision_goggles", new NightVisionGogglesModel()));
        renderers.put(ModItems.VILLAGER_HAT.get(), new SimpleCurioRenderer("villager_hat", new VillagerHatModel()));
        renderers.put(ModItems.SUPERSTITIOUS_HAT.get(), new SimpleCurioRenderer("superstitious_hat", new SuperstitiousHatModel()));

        // necklace
        renderers.put(ModItems.LUCKY_SCARF.get(), new SimpleCurioRenderer("scarf/lucky_scarf", new ScarfModel()));
        renderers.put(ModItems.SCARF_OF_INVISIBILITY.get(), new SimpleCurioRenderer("scarf/scarf_of_invisibility", new ScarfModel(RenderType::entityTranslucent)));
        renderers.put(ModItems.CROSS_NECKLACE.get(), new SimpleCurioRenderer("cross_necklace", new CrossNecklaceModel()));
        renderers.put(ModItems.PANIC_NECKLACE.get(), new SimpleCurioRenderer("panic_necklace", new PanicNecklaceModel()));
        renderers.put(ModItems.SHOCK_PENDANT.get(), new SimpleCurioRenderer("pendant/shock_pendant", new PendantModel()));
        renderers.put(ModItems.FLAME_PENDANT.get(), new SimpleCurioRenderer("pendant/flame_pendant", new PendantModel()));
        renderers.put(ModItems.THORN_PENDANT.get(), new SimpleCurioRenderer("pendant/thorn_pendant", new PendantModel()));
        renderers.put(ModItems.CHARM_OF_SINKING.get(), new SimpleCurioRenderer("charm_of_sinking", new CharmOfSinkingModel()));

        // belt
        renderers.put(ModItems.CLOUD_IN_A_BOTTLE.get(), new BeltCurioRenderer("cloud_in_a_bottle", new CloudInABottleModel()));
        renderers.put(ModItems.OBSIDIAN_SKULL.get(), new BeltCurioRenderer("obsidian_skull", new ObsidianSkullModel()));
        renderers.put(ModItems.ANTIDOTE_VESSEL.get(), new BeltCurioRenderer("antidote_vessel", new AntidoteVesselModel()));
        renderers.put(ModItems.UNIVERSAL_ATTRACTOR.get(), new BeltCurioRenderer("universal_attractor", new UniversalAttractorModel()));
        renderers.put(ModItems.CRYSTAL_HEART.get(), new BeltCurioRenderer("crystal_heart", new CrystalHeartModel()));
        renderers.put(ModItems.HELIUM_FLAMINGO.get(), new SimpleCurioRenderer("helium_flamingo", new HeliumFlamingoModel()));

        // hands
        renderers.put(ModItems.DIGGING_CLAWS.get(), new GloveCurioRenderer("claws/digging_claws", "claws/digging_claws", ClawsModel::new));
        renderers.put(ModItems.FERAL_CLAWS.get(), new GloveCurioRenderer("claws/feral_claws", "claws/feral_claws", ClawsModel::new));
        renderers.put(ModItems.POWER_GLOVE.get(), new GloveCurioRenderer("power_glove"));
        renderers.put(ModItems.FIRE_GAUNTLET.get(), new GlowingGloveCurioRenderer("fire_gauntlet"));
        renderers.put(ModItems.POCKET_PISTON.get(), new GloveCurioRenderer("pocket_piston"));
        renderers.put(ModItems.VAMPIRIC_GLOVE.get(), new GloveCurioRenderer("vampiric_glove"));
        renderers.put(ModItems.GOLDEN_HOOK.get(), new GloveCurioRenderer("golden_hook", GoldenHookModel::new));

        // feet
        renderers.put(ModItems.AQUA_DASHERS.get(), new SimpleCurioRenderer("aqua_dashers", new AquaDashersModel(1.25F)));
        renderers.put(ModItems.BUNNY_HOPPERS.get(), new SimpleCurioRenderer("bunny_hoppers", new BunnyHoppersModel()));
        renderers.put(ModItems.KITTY_SLIPPERS.get(), new SimpleCurioRenderer("kitty_slippers", new KittySlippersModel()));
        renderers.put(ModItems.RUNNING_SHOES.get(), new SimpleCurioRenderer("running_shoes", new ShoesModel(0.5F)));
        renderers.put(ModItems.STEADFAST_SPIKES.get(), new SimpleCurioRenderer("steadfast_spikes", new SteadfastSpikesModel()));
        renderers.put(ModItems.FLIPPERS.get(), new SimpleCurioRenderer("flippers", new FlippersModel()));

        // curio
        renderers.put(ModItems.WHOOPEE_CUSHION.get(), new SimpleCurioRenderer("whoopee_cushion", new WhoopeeCushionModel()));
    }
}
