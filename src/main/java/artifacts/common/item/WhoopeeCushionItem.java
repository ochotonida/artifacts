package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.WhoopeeCushionModel;
import artifacts.common.init.ModSoundEvents;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WhoopeeCushionItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/whoopee_cushion.png");

    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (!entity.world.isRemote()) {
            CompoundNBT tag = stack.getOrCreateTag();
            if (tag.getBoolean("HasFarted") && !entity.isSneaking()) {
                tag.putBoolean("HasFarted", false);
            } else if (!tag.getBoolean("HasFarted") && entity.isSneaking()) {
                tag.putBoolean("HasFarted", true);
                if (entity.getRNG().nextInt(8) == 0) {
                    entity.world.playSound(null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), ModSoundEvents.FART.get(), SoundCategory.PLAYERS, 1, 0.9F + entity.getRNG().nextFloat() * 0.2F);
                }
            }
        }
    }

    @Override
    protected SoundEvent getEquipSound() {
        return ModSoundEvents.FART.get();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new WhoopeeCushionModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
