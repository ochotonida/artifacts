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
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
public class ItemEverlastingFood extends ItemFood {

    public final String name;

    public ItemEverlastingFood(String name, int amount, float saturation) {
        super(amount, saturation, false);
        setRegistryName(name);
        setUnlocalizedName(Artifacts.MODID + "." + name);
        setCreativeTab(CreativeTabs.FOOD);
        setMaxStackSize(1);
        this.name = name;
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
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            entityplayer.getFoodStats().addStats(this, stack);
            world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
            this.onFoodEaten(stack, world, entityplayer);
            // noinspection ConstantConditions
            entityplayer.addStat(StatList.getObjectUseStats(this));
        }

        return stack;
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
