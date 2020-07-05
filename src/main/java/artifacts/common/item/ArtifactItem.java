package artifacts.common.item;

import artifacts.Artifacts;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class ArtifactItem extends Item {

    private final String name;

    public ArtifactItem(Properties properties, String name) {
        super(properties.maxStackSize(1).group(Artifacts.CREATIVE_TAB));
        setRegistryName(new ResourceLocation(Artifacts.MODID, name));
        this.name = name;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        tooltip.add(new TranslationTextComponent("tooltip.artifacts." + name).applyTextStyle(TextFormatting.GRAY));
    }
}
