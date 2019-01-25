package artifacts.common.item;

import artifacts.Artifacts;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

@MethodsReturnNonnullByDefault
public class ItemEverlastingFood extends ItemFood {

    public final String name;

    public ItemEverlastingFood(String name, int amount, float saturation) {
        super(amount, saturation, false);
        setRegistryName(name);
        setUnlocalizedName(Artifacts.MODID + ":" + name);
        setCreativeTab(Artifacts.CREATIVE_TAB);
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
}
