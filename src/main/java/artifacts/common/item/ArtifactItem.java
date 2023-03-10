package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.init.ModGameRules;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Consumer;

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
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltipList, TooltipFlag flags) {
        if (Artifacts.CONFIG.client.showTooltips && ModGameRules.isInitialized()) {
            Consumer<MutableComponent> tooltip = component -> tooltipList.add(component.withStyle(ChatFormatting.GRAY));
            addTooltip(tooltip);
        }
    }

    protected void addTooltip(Consumer<MutableComponent> tooltip) {
        if (isCosmetic()) {
            tooltip.accept(Component.translatable("%s.tooltip.cosmetic".formatted(Artifacts.MODID)).withStyle(ChatFormatting.ITALIC));
        } else {
            addEffectsTooltip(tooltip);
        }
    }

    protected void addEffectsTooltip(Consumer<MutableComponent> tooltip) {
        tooltip.accept(Component.translatable("%s.tooltip.item.%s".formatted(Artifacts.MODID, getTooltipItemName())));
    }

    protected MutableComponent tooltipLine(String lineId, Object... args) {
        return Component.translatable("%s.tooltip.item.%s.%s".formatted(Artifacts.MODID, getTooltipItemName(), lineId), args);
    }

    protected String getTooltipItemName() {
        // noinspection ConstantConditions
        return ForgeRegistries.ITEMS.getKey(this).getPath();
    }

    protected abstract boolean isCosmetic();
}
