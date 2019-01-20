package artifacts.common.item;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

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
}
