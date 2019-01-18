package artifacts.common.item;

import artifacts.Artifacts;
import baubles.api.BaubleType;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber
@MethodsReturnNonnullByDefault
public class BaubleWhoopieCushion extends BaubleBase {

    public static final SoundEvent FART = new SoundEvent(new ResourceLocation(Artifacts.MODID, "fart")).setRegistryName("fart");

    public BaubleWhoopieCushion() {
        super("bauble_whoopie_cushion", BaubleType.BELT);
        setMaxDamage(0);
        setEquipSound(FART, 1);
    }

    @Override
    public void registerModel() {
        super.registerModel();
        Artifacts.proxy.registerItemRenderer(this, 1, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.translateToLocal("tooltip." + name + ".name"));
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        if (stack.getMetadata() == 1 && !player.isSneaking()) {
            stack.setItemDamage(0);
        } else if (stack.getMetadata() == 0 && player.isSneaking()) {
            stack.setItemDamage(1);
            if (player.getRNG().nextInt(3) == 0) {
                player.playSound(FART, 1, 0.9F + player.getRNG().nextFloat() * 0.2F);
            }
        }
    }
}
