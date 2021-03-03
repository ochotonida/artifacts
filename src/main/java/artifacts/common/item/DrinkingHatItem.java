package artifacts.common.item;

import artifacts.client.render.model.curio.DrinkingHatModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class DrinkingHatItem extends CurioItem {

    private final ResourceLocation texture;

    public DrinkingHatItem(ResourceLocation texture) {
        this.texture = texture;
        addListener(LivingEntityUseItemEvent.Start.class, this::onItemUseStart);
    }

    public void onItemUseStart(LivingEntityUseItemEvent.Start event) {
        if (event.getItem().getUseAction() == UseAction.DRINK) {
            event.setDuration(event.getDuration() / 4);
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1, 1);
    }

    protected SoundEvent getEquipSound() {
        return SoundEvents.ITEM_BOTTLE_FILL;
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
