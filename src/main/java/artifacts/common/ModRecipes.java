package artifacts.common;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void initRecipes() {
        GameRegistry.addSmelting(ModItems.EVERLASTING_PORKCHOP, new ItemStack(ModItems.EVERLASTING_COOKED_PORKCHOP), 0.35F);
        GameRegistry.addSmelting(ModItems.EVERLASTING_BEEF, new ItemStack(ModItems.EVERLASTING_COOKED_BEEF), 0.35F);
        GameRegistry.addSmelting(ModItems.EVERLASTING_CHICKEN, new ItemStack(ModItems.EVERLASTING_COOKED_CHICKEN), 0.35F);
        GameRegistry.addSmelting(ModItems.EVERLASTING_RABBIT, new ItemStack(ModItems.EVERLASTING_COOKED_RABBIT), 0.35F);
        GameRegistry.addSmelting(ModItems.EVERLASTING_MUTTON, new ItemStack(ModItems.EVERLASTING_COOKED_MUTTON), 0.35F);
        GameRegistry.addSmelting(ModItems.EVERLASTING_COD, new ItemStack(ModItems.EVERLASTING_COOKED_COD), 0.35F);
        GameRegistry.addSmelting(ModItems.EVERLASTING_SALMON, new ItemStack(ModItems.EVERLASTING_COOKED_SALMON), 0.35F);
    }
}
