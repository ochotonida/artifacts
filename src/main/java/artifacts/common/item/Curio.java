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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.CuriosCapability;
import top.theillusivec4.curios.api.capability.ICurio;

abstract class Curio implements ICurio {

    private final Item curioItem;
    private final SoundEvent equipSound = getEquipSound();

    public Curio(Item item) {
        curioItem = item;
    }

    public static ICapabilityProvider createProvider(ICurio curio) {
        return new Curio.Provider(curio);
    }

    protected SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    }

    @Override
    public void playEquipSound(LivingEntity entity) {
        entity.world.playSound(null, entity.getPosition(), equipSound, SoundCategory.NEUTRAL, 1, 1);
    }

    public boolean canRightClickEquip() {
        return true;
    }

    @Override
    public boolean canEquip(String identifier, LivingEntity entity) {
        return !CuriosAPI.getCurioEquipped(curioItem, entity).isPresent();
    }

    @Override
    public boolean hasRender(String identifier, LivingEntity livingEntity) {
        return true;
    }

    @Override
    public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        BipedModel<LivingEntity> model = getModel();
        Curio.RenderHelper.setBodyRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, partialTicks, netHeadYaw, headPitch, model);
        IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(getTexture()), false, false);
        model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    protected abstract BipedModel<LivingEntity> getModel();

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

    public static final class RenderHelper {
        private RenderHelper() {
        }

        public static void setBodyRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float partialTicks, float netHeadYaw, float headPitch, BipedModel<LivingEntity> model) {
            ICurio.RenderHelper.followBodyRotations(entity, model);
            model.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        }
    }
}
