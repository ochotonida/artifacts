package artifacts.common.item;

import artifacts.common.ModItems;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber
public class BaubleShield extends BaubleBase {

    public BaubleShield() {
        super("bauble_shield", BaubleType.CHARM);
        setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.translateToLocal("tooltip." + name + ".name"));
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onLivingKnockback(LivingKnockBackEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleShield) != -1) {
                event.setCanceled(true);
            }
        }
    }
}
