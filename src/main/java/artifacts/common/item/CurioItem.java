package artifacts.common.item;

import artifacts.Artifacts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.CuriosCapability;
import top.theillusivec4.curios.api.capability.ICurio;

import java.util.List;

public abstract class CurioItem extends Item {

    private final String name;

    public CurioItem(Properties properties, String name) {
        super(properties.maxStackSize(1).group(Artifacts.CREATIVE_TAB));
        setRegistryName(new ResourceLocation(Artifacts.MODID, name));
        this.name = name;
    }

    public static ICapabilityProvider createProvider(ICurio curio) {
        return new Provider(curio);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        if (!Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("tooltip.artifacts.shiftinfo").applyTextStyle(TextFormatting.GRAY));
        } else {
            tooltip.add(new TranslationTextComponent("tooltip.artifacts." + name).applyTextStyle(TextFormatting.GRAY));
        }
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

    protected abstract class Curio implements ICurio {

        private final SoundEvent equipSound = getEquipSound();
        private final float equipPitch = getEquipPitch();

        protected SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
        }

        protected float getEquipPitch() {
            return 1;
        }

        @Override
        public void playEquipSound(LivingEntity entity) {
            entity.world.playSound(null, entity.getPosition(), equipSound, SoundCategory.NEUTRAL, 1, equipPitch);
        }

        public boolean canRightClickEquip() {
            return true;
        }

        @Override
        public boolean canEquip(String identifier, LivingEntity entity) {
            return !CuriosAPI.getCurioEquipped(CurioItem.this, entity).isPresent();
        }
    }
}
