package artifacts.client.jei;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.init.ModItems;
import artifacts.common.item.ArtifactItem;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
                if (ModConfig.server.isCosmetic(item)) {
                    registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM_STACK, Component.translatable("artifacts.cosmetic.jei"));
                } else if (item != ModItems.NOVELTY_DRINKING_HAT.get()) {
                    List<Component> textComponents = new ArrayList<>();
                    item.appendHoverText(new ItemStack(item), null, textComponents, TooltipFlag.Default.NORMAL);
                    textComponents.forEach(component -> ((MutableComponent) component).setStyle(Style.EMPTY));
                    registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM_STACK, textComponents.toArray(new Component[]{}));
                }
            }
        }
        registration.addIngredientInfo(new ItemStack(ModItems.NOVELTY_DRINKING_HAT.get()), VanillaTypes.ITEM_STACK, Component.translatable("item.artifacts.plastic_drinking_hat.tooltip"));
    }
}
