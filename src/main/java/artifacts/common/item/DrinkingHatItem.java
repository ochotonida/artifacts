package artifacts.common.item;

import artifacts.client.render.model.curio.head.DrinkingHatModel;
import artifacts.common.config.Config;
import artifacts.common.init.ModItems;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;

public class DrinkingHatItem extends CurioItem {

    private final ResourceLocation texture;

    public DrinkingHatItem(ResourceLocation texture) {
        this.texture = texture;
        addListener(LivingEntityUseItemEvent.Start.class, this::onItemUseStart);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        if (Config.CLIENT.showTooltips && !Config.SERVER.isCosmetic(this)) {
            if (this != ModItems.PLASTIC_DRINKING_HAT.get()) {
                tooltip.add(new TranslationTextComponent(ModItems.PLASTIC_DRINKING_HAT.get().getDescriptionId() + ".tooltip").withStyle(TextFormatting.GRAY));
            }
        }
        super.appendHoverText(stack, world, tooltip, flags);
    }

    public void onItemUseStart(LivingEntityUseItemEvent.Start event) {
        UseAction action = event.getItem().getUseAnimation();
        if (action == UseAction.DRINK || action == UseAction.EAT && Config.SERVER.drinkingHatConfig.enableFastEating) {
            event.setDuration((int) (event.getDuration() * Config.SERVER.drinkingHatConfig.drinkingDurationMultiplier));
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.BOTTLE_FILL, 1, 1);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new DrinkingHatModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return texture;
    }
}
