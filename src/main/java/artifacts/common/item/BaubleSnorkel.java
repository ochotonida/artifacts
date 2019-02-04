package artifacts.common.item;

import baubles.api.BaubleType;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;

public class BaubleSnorkel extends BaublePotionEffect {


    public BaubleSnorkel() {
        super("snorkel", BaubleType.HEAD, MobEffects.WATER_BREATHING, 0);
        setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1);
    }
}
