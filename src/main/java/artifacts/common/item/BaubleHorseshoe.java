package artifacts.common.item;

import artifacts.common.ModItems;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber
public class BaubleHorseshoe extends BaubleBase {

    public BaubleHorseshoe() {
        super("bauble_horseshoe", BaubleType.CHARM);
        setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1);
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
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleHorseshoe) != -1) {
                if (event.getDistance() > 5) {
                    PotionEffect potioneffect = event.getEntityLiving().getActivePotionEffect(MobEffects.JUMP_BOOST);
                    float f = potioneffect == null ? 0.0F : (float)(potioneffect.getAmplifier() + 1);
                    int i = MathHelper.ceil((event.getDistance() - 3.0F - f) * event.getDamageMultiplier());
                    if (i > 0) {
                        event.getEntity().playSound(SoundEvents.ENTITY_GENERIC_SMALL_FALL, 1, 1);
                    }
                }
                event.setCanceled(true);
            }
        }
    }
}
