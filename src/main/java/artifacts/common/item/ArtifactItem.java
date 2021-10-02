package artifacts.common.item;

import artifacts.common.config.ModConfig;
import artifacts.common.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class ArtifactItem extends Item {

    public ArtifactItem(Properties properties) {
        super(properties.stacksTo(1).tab(ModItems.CREATIVE_TAB).rarity(Rarity.RARE).fireResistant());
    }

    public ArtifactItem() {
        this(new Properties());
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        if (ModConfig.server != null && ModConfig.server.items.containsKey(this)) {
            return ModConfig.server.items.get(this).durability.get();
        }
        return 0;
    }

    @Override
    public boolean canBeDepleted() {
        return getMaxDamage(ItemStack.EMPTY) > 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        if (ModConfig.server != null && ModConfig.server.isCosmetic(this)) {
            tooltip.add(new TranslatableComponent("artifacts.cosmetic.tooltip").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        } else if (ModConfig.client.showTooltips.get()) {
            tooltip.add(new TranslatableComponent(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
        }
    }
}
