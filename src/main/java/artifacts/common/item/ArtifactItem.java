package artifacts.common.item;

import artifacts.common.config.ModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class ArtifactItem extends Item {

    public ArtifactItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE).fireResistant());
    }

    public ArtifactItem() {
        this(new Properties());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment != Enchantments.MENDING && super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        if (ModConfig.server != null && ModConfig.server.isCosmetic(this)) {
            tooltip.add(Component.translatable("artifacts.cosmetic.tooltip").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        } else if (ModConfig.client.showTooltips.get()) {
            tooltip.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
        }
    }
}
