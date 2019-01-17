package artifacts.common.item;

import artifacts.ModItems;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber
public class BaubleObsidianSkull extends BaubleBase {

    public BaubleObsidianSkull() {
        super("bauble_obsidian_skull", BaubleType.CHARM);
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
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.baubleObsidianSkull) != -1 && !((EntityPlayer) event.getEntity()).getCooldownTracker().hasCooldown(ModItems.baubleObsidianSkull)) {
                if (event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.LAVA) {
                    event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE,400, 0,true,true));
                    ((EntityPlayer) event.getEntity()).getCooldownTracker().setCooldown(ModItems.baubleObsidianSkull, 1600);
                }
            }
        }
    }
}
