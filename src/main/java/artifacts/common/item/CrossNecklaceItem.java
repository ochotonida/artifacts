package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.necklace.CrossNecklaceModel;
import artifacts.common.config.ModConfig;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CrossNecklaceItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/cross_necklace.png");

    private boolean canApplyBonus(ItemStack stack) {
        return !ModConfig.server.isCosmetic(this) && stack.getOrCreateTag().getBoolean("CanApplyBonus");
    }

    private static void setCanApplyBonus(ItemStack stack, boolean canApplyBonus) {
        stack.getOrCreateTag().putBoolean("CanApplyBonus", canApplyBonus);
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND, 1, 1);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (entity.invulnerableTime <= 10) {
            setCanApplyBonus(stack, true);
        } else {
            if (canApplyBonus(stack)) {
                entity.invulnerableTime += ModConfig.server.crossNecklace.invincibilityBonus.get();
                setCanApplyBonus(stack, false);
                damageStack(identifier, index, entity, stack);
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
