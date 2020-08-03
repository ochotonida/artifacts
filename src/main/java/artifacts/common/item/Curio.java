package artifacts.common.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.CuriosCapability;
import top.theillusivec4.curios.api.capability.ICurio;

public abstract class Curio implements ICurio {

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
    @OnlyIn(Dist.CLIENT)
    public boolean hasRender(String identifier, LivingEntity livingEntity) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void doRender(String identifier, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        BipedModel<LivingEntity> model = getModel();
        Minecraft.getInstance().getTextureManager().bindTexture(getTexture());
        model.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        model.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        RenderHelper.followBodyRotations(entity, model);
        model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
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
