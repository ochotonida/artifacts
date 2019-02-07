package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.ModConfig;
import artifacts.common.ModItems;
import artifacts.common.loot.functions.GenerateEverlastingFish;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
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
@Optional.Interface(iface = "squeek.applecore.api.food.IEdible", modid = "applecore")
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
        if (ModConfig.everlastingFishWeight > 0 && event.getName().toString().equals("minecraft:gameplay/fishing/fish")) {
            LootFunction[] functions = new LootFunction[1];
            functions[0] = new GenerateEverlastingFish(new LootCondition[0]);
            event.getTable().getPool("main").addEntry(new LootEntryItem(Items.FISH, ModConfig.everlastingFishWeight, 0, functions, new LootCondition[0], "everlasting_fish"));
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
            return;
        }

        if (event.getEntityLiving().getRNG().nextDouble() < ModConfig.everlastingFoodChance && !event.getEntityLiving().isChild()) {
            Item item;

            if (event.getEntity() instanceof EntityPig) {
                if (event.getEntityLiving().isBurning()) {
                    item = ModItems.EVERLASTING_COOKED_PORKCHOP;
                } else {
                    item = ModItems.EVERLASTING_PORKCHOP;
                }
            } else if (event.getEntity() instanceof EntityCow) {
                if (event.getEntityLiving().isBurning()) {
                    item = ModItems.EVERLASTING_COOKED_BEEF;
                } else {
                    item = ModItems.EVERLASTING_BEEF;
                }
            } else if (event.getEntity() instanceof EntitySheep) {
                if (event.getEntityLiving().isBurning()) {
                    item = ModItems.EVERLASTING_COOKED_MUTTON;
                } else {
                    item = ModItems.EVERLASTING_MUTTON;
                }
            } else if (event.getEntity() instanceof EntityChicken) {
                if (event.getEntityLiving().isBurning()) {
                    item = ModItems.EVERLASTING_COOKED_CHICKEN;
                } else {
                    item = ModItems.EVERLASTING_CHICKEN;
                }
            } else if (event.getEntity() instanceof EntityRabbit) {
                if (event.getEntityLiving().isBurning()) {
                    item = ModItems.EVERLASTING_COOKED_RABBIT;
                } else {
                    item = ModItems.EVERLASTING_RABBIT;
                }
            } else if (event.getEntity() instanceof EntityZombie) {
                item = ModItems.EVERLASTING_ROTTEN_FLESH;
            } else if (event.getEntity() instanceof EntitySpider) {
                item = ModItems.EVERLASTING_SPIDER_EYE;
            } else {
                return;
            }

            event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, new ItemStack(item)));
        }
    }
}
