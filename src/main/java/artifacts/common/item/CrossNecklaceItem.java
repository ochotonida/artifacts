package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.CrossNecklaceModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CrossNecklaceItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/cross_necklace.png");

    private static boolean canApplyBonus(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("CanApplyBonus");
    }

    private static void setCanApplyBonus(ItemStack stack, boolean canApplyBonus) {
        stack.getOrCreateTag().putBoolean("CanApplyBonus", canApplyBonus);
    }

    @Override
    protected SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (entity.hurtResistantTime <= 10) {
            setCanApplyBonus(stack, true);
        } else {
            if (canApplyBonus(stack)) {
                entity.hurtResistantTime += 20;
                setCanApplyBonus(stack, false);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new CrossNecklaceModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
