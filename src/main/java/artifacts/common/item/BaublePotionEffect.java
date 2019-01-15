package artifacts.common.item;

import baubles.api.BaubleType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BaublePotionEffect extends BaubleBase {

    public final Potion effect;

    public final int effectAmplifier;

    public BaublePotionEffect(String name, BaubleType type, Potion effect, int effectamplifier) {
        super(name, type);
        this.effect = effect;
        this.effectAmplifier = effectamplifier;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if (player.ticksExisted % 39 == 0) {
            player.addPotionEffect(new PotionEffect(effect,40, effectAmplifier,true,false));
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocal(effect.getName()).trim() + " " + I18n.translateToLocal("potion.potency." + effectAmplifier).trim());
    }
}
