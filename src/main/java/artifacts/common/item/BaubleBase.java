package artifacts.common.item;

import artifacts.Artifacts;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@MethodsReturnNonnullByDefault
public class BaubleBase extends Item implements IBauble {

    public final BaubleType type;

    public final String name;

    protected SoundEvent equipSound = SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;

    protected float equipPitch = 1;

    public BaubleBase(String name, BaubleType type) {
        super();
        setRegistryName(name);
        setUnlocalizedName(Artifacts.MODID + ":" + name);
        setCreativeTab(Artifacts.CREATIVE_TAB);
        setMaxStackSize(1);
        this.type = type;
        this.name = name;
    }

    public BaubleBase setEquipSound(SoundEvent sound, float pitch) {
        equipSound = sound;
        equipPitch = pitch;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.translateToLocal("tooltip." + Artifacts.MODID + ":" + name + ".name"));
    }

    public void registerModel() {
        Artifacts.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.RARE;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
        for (int i = 0; i < baubles.getSlots(); i++) {
            if (baubles.getStackInSlot(i).isEmpty() && baubles.isItemValidForSlot(i, player.getHeldItem(hand), player)) {
                baubles.setStackInSlot(i, player.getHeldItem(hand).copy());
                if (!player.capabilities.isCreativeMode){
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
                }
                onEquipped(player.getHeldItem(hand), player);
                return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
            }
        }
        return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(hand));
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        player.playSound(equipSound, .75F, 0.95F * equipPitch);
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        player.playSound(equipSound, .75F, 2 * equipPitch);
    }
}
