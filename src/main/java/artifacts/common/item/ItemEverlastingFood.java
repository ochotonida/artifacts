package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.ModConfig;
import artifacts.common.loot.functions.GenerateEverlastingFish;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.food.IEdible;
import squeek.applecore.api.food.ItemFoodProxy;

import javax.annotation.ParametersAreNonnullByDefault;

@Mod.EventBusSubscriber
@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Optional.Interface(iface = "squeek.applecore.jerApi.food.IEdible", modid = "applecore")
public class ItemEverlastingFood extends Item implements IEdible {

    public final String name;
    private final int amount;
    private final float saturation;
    private PotionEffect potionEffect;
    private float potionProbability;

    public ItemEverlastingFood(String name, int amount, float saturation) {
        setRegistryName(name);
        setUnlocalizedName(Artifacts.MODID + "." + name);
        setCreativeTab(CreativeTabs.FOOD);
        setMaxStackSize(1);
        this.name = name;
        this.amount = amount;
        this.saturation = saturation;
    }

    @Override
    @Optional.Method(modid = "applecore")
    public FoodValues getFoodValues(ItemStack itemStack) {
        return new FoodValues(amount, saturation);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.EAT;
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote && potionEffect != null && worldIn.rand.nextFloat() < potionProbability) {
            player.addPotionEffect(new PotionEffect(potionEffect));
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    public void registerModel() {
        Artifacts.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.RARE;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            if (Loader.isModLoaded("applecore")) {
                onEatenCompatibility(stack, player);
            } else {
                player.getFoodStats().addStats(amount, saturation);
            }

            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
            onFoodEaten(stack, world, player);
            // noinspection ConstantConditions
            player.addStat(StatList.getObjectUseStats(this));
        }

        return stack;
    }

    @Optional.Method(modid = "applecore")
    public void onEatenCompatibility(ItemStack stack, EntityPlayer player) {
        new ItemFoodProxy(this).onEaten(stack, player);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.canEat(false)) {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        } else {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
    }

    public ItemEverlastingFood setPotionEffect(PotionEffect effect, float probability) {
        this.potionEffect = effect;
        this.potionProbability = probability;
        return this;
    }

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (!ModConfig.enableEverlastingFood) {
            return;
        }

        if (event.getName().toString().equals("minecraft:gameplay/fishing/fish")) {
            LootFunction[] functions = new LootFunction[1];
            functions[0] = new GenerateEverlastingFish(new LootCondition[]{new RandomChance(0.05F)});
            event.getTable().getPool("main").addEntry(new LootEntryItem(Items.FISH, 1, 0, functions, new LootCondition[0], "everlasting_fish"));
        }

        ResourceLocation location;
        switch (event.getName().toString()) {
            case "minecraft:entities/cow":
            case "minecraft:entities/mushroom_cow":
                location = new ResourceLocation(Artifacts.MODID, "everlasting_food/beef");
                break;
            case "minecraft:entities/pig":
                location = new ResourceLocation(Artifacts.MODID, "everlasting_food/porkchop");
                break;
            case "minecraft:entities/chicken":
                location = new ResourceLocation(Artifacts.MODID, "everlasting_food/chicken");
                break;
            case "minecraft:entities/sheep":
                location = new ResourceLocation(Artifacts.MODID, "everlasting_food/mutton");
                break;
            case "minecraft:entities/rabbit":
                location = new ResourceLocation(Artifacts.MODID, "everlasting_food/rabbit");
                break;
            case "minecraft:entities/spider":
            case "minecraft:entities/cave_spider":
                location = new ResourceLocation(Artifacts.MODID, "everlasting_food/spider_eye");
                break;
            case "minecraft:entities/zombie":
            case "minecraft:entities/husk":
            case "minecraft:entities/zombie_villager":
            case "minecraft:entities/zombie_pigman":
            case "minecraft:entities/zombie_horse":
                location = new ResourceLocation(Artifacts.MODID, "everlasting_food/rotten_flesh");
                break;
            default:
                return;
        }

        LootEntry entry = new LootEntryTable(location, 1, 0, new LootCondition[0], "entry#0");
        event.getTable().addPool(new LootPool(new LootEntry[]{entry}, new LootCondition[]{}, new RandomValueRange(1), new RandomValueRange(0), "everlasting_food"));
    }
}
