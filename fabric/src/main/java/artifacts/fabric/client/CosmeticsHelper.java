package artifacts.fabric.client;

import net.minecraft.world.item.ItemStack;

public class CosmeticsHelper {

    public static boolean isCosmeticsDisabled(ItemStack stack) {
        return stack.hasTag() && stack.getOrCreateTag().getBoolean("CosmeticsDisabled");
    }

    public static void toggleCosmetics(ItemStack stack) {
        stack.getOrCreateTag().putBoolean("CosmeticsDisabled", !isCosmeticsDisabled(stack));
    }
}
