package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.init.ModCompat;
import baubles.api.BaubleType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BaubleTinyShirt extends AttributeModifierBauble {

    public BaubleTinyShirt() {
        super("tiny_shirt", BaubleType.BODY, AttributeModifierBauble.ExtendedAttributeModifier.SHRINKING);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (!ModCompat.isArtemisLibLoaded) {
            tooltip.add(TextFormatting.DARK_RED + I18n.translateToLocal("tooltip." + Artifacts.MODID + ".artemislibinfo.name"));
        }
    }
}
