package artifacts.neoforge.data.tags;

import artifacts.Artifacts;
import artifacts.integration.ModCompat;
import artifacts.registry.ModItems;
import artifacts.registry.RegistryHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ItemTags extends ItemTagsProvider {

    public static final TagKey<Item> ARTIFACTS = createTag("artifacts");
    public static final TagKey<Item> EQUIPPABLE = createTag("equippable");
    // TODO: consider renaming to `equippable/slot_name`
    public static final TagKey<Item> HEAD_EQUIPPABLE = createTag("slot/head");
    public static final TagKey<Item> FACE_EQUIPPABLE = createTag("slot/face");
    public static final TagKey<Item> NECKLACE_EQUIPPABLE = createTag("slot/necklace");
    public static final TagKey<Item> HANDS_EQUIPPABLE = createTag("slot/hands");
    public static final TagKey<Item> BELT_EQUIPPABLE = createTag("slot/belt");
    public static final TagKey<Item> FEET_EQUIPPABLE = createTag("slot/feet");
    public static final TagKey<Item> ALL_EQUIPPABLE = createTag("slot/all");

    // Probably not needed anymore, but kept for compatibility with origins-legacy
    public static final TagKey<Item> ORIGINS_MEAT = TagKey.create(Registries.ITEM, ModCompat.ORIGINS.id("meat"));
    public static final TagKey<Item> ORIGINS_SHIELDS = TagKey.create(Registries.ITEM, ModCompat.ORIGINS.id("shields"));

    private static TagKey<Item> createTag(String name) {
        return TagKey.create(Registries.ITEM, Artifacts.id(name));
    }

    public ItemTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider, Artifacts.MOD_ID);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider provider) {
        tag(ARTIFACTS).addAll(ModItems.ITEMS.getEntries().stream()
                .filter(holder -> !holder.is(ModItems.MIMIC_SPAWN_EGG.getKey()))
                .map(RegistryHolder::getKey)
        );
        addSlotTags();
        addRepairMaterialTags();
        addOriginsTags();

        tag(net.minecraft.tags.ItemTags.EQUIPPABLE_ENCHANTABLE).addTag(EQUIPPABLE);
        tag(net.minecraft.tags.ItemTags.VANISHING_ENCHANTABLE).addTag(EQUIPPABLE);

        tag(net.minecraft.tags.ItemTags.PIGLIN_LOVED).add(
                ModItems.GOLDEN_HOOK.getKey(),
                ModItems.CROSS_NECKLACE.getKey(),
                ModItems.ANTIDOTE_VESSEL.getKey(),
                ModItems.UNIVERSAL_ATTRACTOR.getKey()
        );

        tag(net.minecraft.tags.ItemTags.SPEARS).add(
                ModItems.UMBRELLA.getKey()
        );
    }

    @SuppressWarnings("unchecked")
    private void addSlotTags() {
        tag(EQUIPPABLE).addTags(
                HEAD_EQUIPPABLE,
                FACE_EQUIPPABLE,
                NECKLACE_EQUIPPABLE,
                HANDS_EQUIPPABLE,
                BELT_EQUIPPABLE,
                FEET_EQUIPPABLE,
                ALL_EQUIPPABLE
        );
        tag(HEAD_EQUIPPABLE).add(
                ModItems.PLASTIC_DRINKING_HAT.getKey(),
                ModItems.NOVELTY_DRINKING_HAT.getKey(),
                ModItems.VILLAGER_HAT.getKey(),
                ModItems.SUPERSTITIOUS_HAT.getKey(),
                ModItems.COWBOY_HAT.getKey(),
                ModItems.ANGLERS_HAT.getKey()
        );
        tag(FACE_EQUIPPABLE).add(
                ModItems.SNORKEL.getKey(),
                ModItems.NIGHT_VISION_GOGGLES.getKey()
        );
        tag(NECKLACE_EQUIPPABLE).add(
                ModItems.LUCKY_SCARF.getKey(),
                ModItems.SCARF_OF_INVISIBILITY.getKey(),
                ModItems.CROSS_NECKLACE.getKey(),
                ModItems.PANIC_NECKLACE.getKey(),
                ModItems.SHOCK_PENDANT.getKey(),
                ModItems.FLAME_PENDANT.getKey(),
                ModItems.THORN_PENDANT.getKey(),
                ModItems.CHARM_OF_SINKING.getKey(),
                ModItems.CHARM_OF_SHRINKING.getKey()
        );
        tag(HANDS_EQUIPPABLE).add(
                ModItems.DIGGING_CLAWS.getKey(),
                ModItems.FERAL_CLAWS.getKey(),
                ModItems.POWER_GLOVE.getKey(),
                ModItems.FIRE_GAUNTLET.getKey(),
                ModItems.POCKET_PISTON.getKey(),
                ModItems.VAMPIRIC_GLOVE.getKey(),
                ModItems.GOLDEN_HOOK.getKey(),
                ModItems.ONION_RING.getKey(),
                ModItems.PICKAXE_HEATER.getKey(),
                ModItems.WITHERED_BRACELET.getKey()
        );
        tag(BELT_EQUIPPABLE).add(
                ModItems.CLOUD_IN_A_BOTTLE.getKey(),
                ModItems.OBSIDIAN_SKULL.getKey(),
                ModItems.ANTIDOTE_VESSEL.getKey(),
                ModItems.UNIVERSAL_ATTRACTOR.getKey(),
                ModItems.CRYSTAL_HEART.getKey(),
                ModItems.HELIUM_FLAMINGO.getKey(),
                ModItems.CHORUS_TOTEM.getKey(),
                ModItems.WARP_DRIVE.getKey()
        );
        tag(FEET_EQUIPPABLE).add(
                ModItems.AQUA_DASHERS.getKey(),
                ModItems.BUNNY_HOPPERS.getKey(),
                ModItems.KITTY_SLIPPERS.getKey(),
                ModItems.RUNNING_SHOES.getKey(),
                ModItems.SNOWSHOES.getKey(),
                ModItems.STEADFAST_SPIKES.getKey(),
                ModItems.FLIPPERS.getKey(),
                ModItems.ROOTED_BOOTS.getKey(),
                ModItems.STRIDER_SHOES.getKey()
        );
        tag(ALL_EQUIPPABLE).add(
                ModItems.WHOOPEE_CUSHION.getKey()
        );
    }

    private void addRepairMaterialTags() {
        repairMaterials(ModItems.ANGLERS_HAT).addTag(Tags.Items.LEATHERS);
        repairMaterials(ModItems.ANTIDOTE_VESSEL).add(ItemIds.GOLD_INGOT);
        repairMaterials(ModItems.AQUA_DASHERS).addTag(Tags.Items.LEATHERS);
        repairMaterials(ModItems.BUNNY_HOPPERS).add(ItemIds.GOLDEN_CARROT);
        repairMaterials(ModItems.CHARM_OF_SHRINKING).add(ItemIds.DIAMOND);
        repairMaterials(ModItems.CHARM_OF_SINKING).add(ItemIds.DIAMOND);
        repairMaterials(ModItems.CLOUD_IN_A_BOTTLE).add(ItemIds.PHANTOM_MEMBRANE);
        repairMaterials(ModItems.COWBOY_HAT).addTag(Tags.Items.LEATHERS);
        repairMaterials(ModItems.CROSS_NECKLACE).add(ItemIds.GOLD_INGOT);
        repairMaterials(ModItems.CRYSTAL_HEART).add(ItemIds.DIAMOND);
        repairMaterials(ModItems.DIGGING_CLAWS).add(ItemIds.IRON_INGOT);
        repairMaterials(ModItems.ETERNAL_STEAK).add(ItemIds.COOKED_BEEF);
        repairMaterials(ModItems.EVERLASTING_BEEF).add(ItemIds.BEEF);
        repairMaterials(ModItems.FERAL_CLAWS).add(ItemIds.EMERALD);
        repairMaterials(ModItems.FIRE_GAUNTLET).add(ItemIds.FIRE_CHARGE);
        repairMaterials(ModItems.FLAME_PENDANT).add(ItemIds.DIAMOND);
        repairMaterials(ModItems.FLIPPERS).add(ItemIds.DRIED_KELP);
        repairMaterials(ModItems.GOLDEN_HOOK).add(ItemIds.GOLD_INGOT);
        repairMaterials(ModItems.HELIUM_FLAMINGO).add(BlockItemIds.RESIN_CLUMP.item());
        repairMaterials(ModItems.KITTY_SLIPPERS).addTag(net.minecraft.tags.ItemTags.CAT_FOOD);
        repairMaterials(ModItems.LUCKY_SCARF).addTag(net.minecraft.tags.ItemTags.WOOL);
        repairMaterials(ModItems.NIGHT_VISION_GOGGLES).add(ItemIds.GOLDEN_CARROT);
        repairMaterials(ModItems.NOVELTY_DRINKING_HAT).add(BlockItemIds.RESIN_CLUMP.item());
        repairMaterials(ModItems.OBSIDIAN_SKULL).add(BlockItemIds.OBSIDIAN.item());
        repairMaterials(ModItems.ONION_RING).add(ItemIds.GOLD_INGOT);
        repairMaterials(ModItems.PANIC_NECKLACE).add(ItemIds.DIAMOND);
        repairMaterials(ModItems.PICKAXE_HEATER).add(ItemIds.FIRE_CHARGE);
        repairMaterials(ModItems.PLASTIC_DRINKING_HAT).add(BlockItemIds.RESIN_CLUMP.item());
        repairMaterials(ModItems.POCKET_PISTON).add(BlockItemIds.PISTON.item());
        repairMaterials(ModItems.POWER_GLOVE).add(BlockItemIds.RESIN_CLUMP.item());
        repairMaterials(ModItems.ROOTED_BOOTS).addTag(Tags.Items.LEATHERS);
        repairMaterials(ModItems.RUNNING_SHOES).addTag(Tags.Items.LEATHERS);
        repairMaterials(ModItems.SCARF_OF_INVISIBILITY).addTag(net.minecraft.tags.ItemTags.WOOL);
        repairMaterials(ModItems.SHOCK_PENDANT).add(ItemIds.DIAMOND);
        repairMaterials(ModItems.SNORKEL).addTag(Tags.Items.GLASS_BLOCKS_COLORLESS);
        repairMaterials(ModItems.SNOWSHOES).add(ItemIds.STICK);
        repairMaterials(ModItems.STEADFAST_SPIKES).addTag(Tags.Items.LEATHERS);
        repairMaterials(ModItems.STRIDER_SHOES).add(BlockItemIds.BASALT.item());
        repairMaterials(ModItems.SUPERSTITIOUS_HAT).addTag(Tags.Items.LEATHERS);
        repairMaterials(ModItems.THORN_PENDANT).add(ItemIds.DIAMOND);
        repairMaterials(ModItems.UMBRELLA).addTag(net.minecraft.tags.ItemTags.WOODEN_TOOL_MATERIALS);
        repairMaterials(ModItems.UNIVERSAL_ATTRACTOR).add(ItemIds.GOLD_INGOT);
        repairMaterials(ModItems.VAMPIRIC_GLOVE).addTag(Tags.Items.LEATHERS);
        repairMaterials(ModItems.VILLAGER_HAT).add(BlockItemIds.HAY_BLOCK.item());
        repairMaterials(ModItems.WARP_DRIVE).add(ItemIds.ENDER_PEARL);
        repairMaterials(ModItems.WHOOPEE_CUSHION).addTag(Tags.Items.LEATHERS);
        repairMaterials(ModItems.WITHERED_BRACELET).add(ItemIds.BONE);
    }

    @SuppressWarnings("unchecked")
    private void addOriginsTags() {
        tag(ORIGINS_MEAT).add(
                ModItems.EVERLASTING_BEEF.getKey(),
                ModItems.ETERNAL_STEAK.getKey()
        );
        tag(ORIGINS_SHIELDS).add(
                ModItems.UMBRELLA.getKey()
        );
    }

    private TagAppender<Item> repairMaterials(RegistryHolder<Item, Item> holder) {
        return tag(TagKey.create(Registries.ITEM, holder.getKey().identifier().withPrefix("repairs_")));
    }
}
