package artifacts.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class CurioItem extends ArtifactItem implements ICurioItem {

    private Object model;

    public CurioItem() {
        super(new Properties());
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    public boolean canRightClickEquip(ItemStack stack) {
        return true;
    }

    protected SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }

    @Override
    public void playRightClickEquipSound(LivingEntity entity, ItemStack stack) {
        entity.world.playSound(null, new BlockPos(entity.getPositionVec()), getEquipSound(), SoundCategory.NEUTRAL, 1, 1);
    }

    @Override
    public boolean canEquip(String identifier, LivingEntity entity, ItemStack stack) {
        return !CuriosApi.getCuriosHelper().findEquippedCurio(this, entity).isPresent();
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        return true;
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        BipedModel<LivingEntity> model = getModel();
        model.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        ICurio.RenderHelper.followBodyRotations(entity, model);
        IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(getTexture()), false, false);
        model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    @OnlyIn(Dist.CLIENT)
    protected final BipedModel<LivingEntity> getModel() {
        if (model == null) {
            model = createModel();
        }

        //noinspection unchecked
        return (BipedModel<LivingEntity>) model;
    }

    @OnlyIn(Dist.CLIENT)
    protected abstract BipedModel<LivingEntity> createModel();

    protected abstract ResourceLocation getTexture();
}
