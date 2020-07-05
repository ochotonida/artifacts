package artifacts.common.item;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
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
