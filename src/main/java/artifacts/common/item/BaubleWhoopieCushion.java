package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.ModSoundEvents;
import baubles.api.BaubleType;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

@MethodsReturnNonnullByDefault
public class BaubleWhoopieCushion extends BaubleBase {

    public BaubleWhoopieCushion() {
        super("whoopie_cushion", BaubleType.BELT);
        setMaxDamage(0);
        setEquipSound(ModSoundEvents.FART, 1);
    }

    @Override
    public void registerModel() {
        super.registerModel();
        Artifacts.proxy.registerItemRenderer(this, 1, name);
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        if (stack.getMetadata() == 1 && !player.isSneaking()) {
            stack.setItemDamage(0);
        } else if (stack.getMetadata() == 0 && player.isSneaking()) {
            stack.setItemDamage(1);
            if (player.getRNG().nextInt(3) == 0) {
                player.playSound(ModSoundEvents.FART, 1, 0.9F + player.getRNG().nextFloat() * 0.2F);
            }
        }
    }
}
