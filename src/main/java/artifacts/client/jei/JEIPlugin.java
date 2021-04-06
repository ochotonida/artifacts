package artifacts.client.jei;

import artifacts.Artifacts;
import artifacts.common.init.ModItems;
import artifacts.common.item.ArtifactItem;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

@JeiPlugin
@SuppressWarnings("unused")
public class JEIPlugin implements IModPlugin {

    private static final ResourceLocation ID = new ResourceLocation(Artifacts.MODID, "main");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void registerRecipes(IRecipeRegistration registration) {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item instanceof ArtifactItem && item != ModItems.NOVELTY_DRINKING_HAT.get()) {
                registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM, String.format("item.artifacts.%s.tooltip", item.getRegistryName().getPath()));
            }
        }
        registration.addIngredientInfo(new ItemStack(ModItems.NOVELTY_DRINKING_HAT.get()), VanillaTypes.ITEM, String.format("item.artifacts.%s.tooltip", ModItems.PLASTIC_DRINKING_HAT.get().getRegistryName().getPath()));
    }
}
