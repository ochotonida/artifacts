package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.necklace.PanicNecklaceModel;
import artifacts.common.config.ModConfig;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class PanicNecklaceItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/panic_necklace.png");

    public PanicNecklaceItem() {
        addListener(LivingHurtEvent.class, this::onLivingHurt);
    }

    public void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().level.isClientSide && event.getAmount() >= 1) {
            int duration = ModConfig.server.panicNecklace.speedDuration.get();
            int level = ModConfig.server.panicNecklace.speedLevel.get() - 1;

            if (duration > 0 && level >= 0) {
                event.getEntityLiving().addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, duration, level, false, false));
            }
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND, 1, 1);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new PanicNecklaceModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
