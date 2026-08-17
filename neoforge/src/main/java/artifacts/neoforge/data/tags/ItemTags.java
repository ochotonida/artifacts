package artifacts.neoforge.data.tags;

import artifacts.Artifacts;
import artifacts.integration.ModCompat;
import artifacts.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ItemTags extends ItemTagsProvider {

    public static final TagKey<Item>
            ARTIFACTS = createTag("artifacts"),
            HEAD = createTag("slot/head"),
            FACE = createTag("slot/face"),
            NECKLACE = createTag("slot/necklace"),
            HANDS = createTag("slot/hands"),
            BELT = createTag("slot/belt"),
            FEET = createTag("slot/feet"),
            ALL = createTag("slot/all");

    public static final TagKey<Item> ORIGINS_MEAT = TagKey.create(Registries.ITEM, ModCompat.ORIGINS.id("meat"));
    public static final TagKey<Item> ORIGINS_SHIELDS = TagKey.create(Registries.ITEM, ModCompat.ORIGINS.id("shields"));

    public static final TagKey<Item> PASSTHROUGH_DEATH_WHEN_HELD = TagKey.create(Registries.ITEM, ModCompat.HARDCORE_REVIVAL.id("passthrough_death_when_held"));

    private static TagKey<Item> createTag(String name) {
        return TagKey.create(Registries.ITEM, Artifacts.id(name));
    }

    public ItemTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, blockTags, Artifacts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ARTIFACTS).add(BuiltInRegistries.ITEM.stream()
                .filter(item -> BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(Artifacts.MOD_ID))
                .filter(item -> item != ModItems.MIMIC_SPAWN_EGG.value()).toList().toArray(new Item[]{})
        );
        tag(HEAD).add(
                ModItems.PLASTIC_DRINKING_HAT.value(),
                ModItems.NOVELTY_DRINKING_HAT.value(),
                ModItems.VILLAGER_HAT.value(),
                ModItems.SUPERSTITIOUS_HAT.value(),
                ModItems.COWBOY_HAT.value(),
                ModItems.ANGLERS_HAT.value()
        );
        tag(FACE).add(
                ModItems.SNORKEL.value(),
                ModItems.NIGHT_VISION_GOGGLES.value()
        );
        tag(NECKLACE).add(
                ModItems.LUCKY_SCARF.value(),
                ModItems.SCARF_OF_INVISIBILITY.value(),
                ModItems.CROSS_NECKLACE.value(),
                ModItems.PANIC_NECKLACE.value(),
                ModItems.SHOCK_PENDANT.value(),
                ModItems.FLAME_PENDANT.value(),
                ModItems.THORN_PENDANT.value(),
                ModItems.CHARM_OF_SINKING.value(),
                ModItems.CHARM_OF_SHRINKING.value()
        );
        tag(HANDS).add(
                ModItems.DIGGING_CLAWS.value(),
                ModItems.FERAL_CLAWS.value(),
                ModItems.POWER_GLOVE.value(),
                ModItems.FIRE_GAUNTLET.value(),
                ModItems.POCKET_PISTON.value(),
                ModItems.VAMPIRIC_GLOVE.value(),
                ModItems.GOLDEN_HOOK.value(),
                ModItems.ONION_RING.value(),
                ModItems.PICKAXE_HEATER.value(),
                ModItems.WITHERED_BRACELET.value()
        );
        tag(BELT).add(
                ModItems.CLOUD_IN_A_BOTTLE.value(),
                ModItems.OBSIDIAN_SKULL.value(),
                ModItems.ANTIDOTE_VESSEL.value(),
                ModItems.UNIVERSAL_ATTRACTOR.value(),
                ModItems.CRYSTAL_HEART.value(),
                ModItems.HELIUM_FLAMINGO.value(),
                ModItems.CHORUS_TOTEM.value(),
                ModItems.WARP_DRIVE.value()
        );
        tag(FEET).add(
                ModItems.AQUA_DASHERS.value(),
                ModItems.BUNNY_HOPPERS.value(),
                ModItems.KITTY_SLIPPERS.value(),
                ModItems.RUNNING_SHOES.value(),
                ModItems.SNOWSHOES.value(),
                ModItems.STEADFAST_SPIKES.value(),
                ModItems.FLIPPERS.value(),
                ModItems.ROOTED_BOOTS.value(),
                ModItems.STRIDER_SHOES.value()
        );
        tag(ALL).add(
                ModItems.WHOOPEE_CUSHION.value()
        );

        tag(ORIGINS_MEAT).add(
                ModItems.EVERLASTING_BEEF.value(),
                ModItems.ETERNAL_STEAK.value()
        );
        tag(ORIGINS_SHIELDS).add(
                ModItems.UMBRELLA.value()
        );

        tag(net.minecraft.tags.ItemTags.PIGLIN_LOVED).add(
                ModItems.GOLDEN_HOOK.value(),
                ModItems.CROSS_NECKLACE.value(),
                ModItems.ANTIDOTE_VESSEL.value(),
                ModItems.UNIVERSAL_ATTRACTOR.value()
        );

        tag(PASSTHROUGH_DEATH_WHEN_HELD).add(
                ModItems.CHORUS_TOTEM.value()
        );
    }
}
