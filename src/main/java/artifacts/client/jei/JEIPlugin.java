package artifacts.client.jei;

import artifacts.Artifacts;
import artifacts.common.config.Config;
import artifacts.common.init.ModItems;
import artifacts.common.item.ArtifactItem;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
@SuppressWarnings("unused")
public class JEIPlugin implements IModPlugin {

    private static final ResourceLocation ID = new ResourceLocation(Artifacts.MODID, "main");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item instanceof ArtifactItem) {
                if (Config.isCosmetic(item)) {
                    registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM, "artifacts.cosmetic.jei");
                } else if (item != ModItems.NOVELTY_DRINKING_HAT.get()) {
                    List<ITextComponent> textComponents = new ArrayList<>();
                    item.appendHoverText(new ItemStack(item), null, textComponents, ITooltipFlag.TooltipFlags.NORMAL);
                    registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM, textComponents.stream().map(ITextComponent::getString).toArray(String[]::new));
                }
            }
        }
        registration.addIngredientInfo(new ItemStack(ModItems.NOVELTY_DRINKING_HAT.get()), VanillaTypes.ITEM, "item.artifacts.plastic_drinking_hat.tooltip");
    }
}
