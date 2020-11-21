package artifacts.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

public abstract class Curio implements ICurio {

    private final Item curioItem;

    public Curio(Item item) {
        curioItem = item;
    }

    public static ICapabilityProvider createProvider(ICurio curio) {
        return new Curio.Provider(curio);
    }

    protected SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }

    @Override
    public void playRightClickEquipSound(LivingEntity entity) {
        entity.world.playSound(null, new BlockPos(entity.getPositionVec()), getEquipSound(), SoundCategory.NEUTRAL, 1, 1);
    }

    public boolean canRightClickEquip() {
        return true;
    }

    @Override
    public boolean canEquip(String identifier, LivingEntity entity) {
        return !CuriosApi.getCuriosHelper().findEquippedCurio(curioItem, entity).isPresent();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean canRender(String identifier, int index, LivingEntity livingEntity) {
        return true;
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        BipedModel<LivingEntity> model = getModel();
        model.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        RenderHelper.followBodyRotations(entity, model);
        IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(getTexture()), false, false);
        model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    @OnlyIn(Dist.CLIENT)
    protected abstract BipedModel<LivingEntity> getModel();

    @OnlyIn(Dist.CLIENT)
    protected abstract ResourceLocation getTexture();

    protected static class Provider implements ICapabilityProvider {

        private final LazyOptional<ICurio> capability;

        Provider(ICurio curio) {
            capability = LazyOptional.of(() -> curio);
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return CuriosCapability.ITEM.orEmpty(cap, capability);
        }
    }
}
