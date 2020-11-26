package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.AntidoteVesselModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

public class AntidoteVesselItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/antidote_vessel.png");

    @Override
    protected SoundEvent getEquipSound() {
        return SoundEvents.ITEM_BOTTLE_FILL;
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {
        Map<Effect, EffectInstance> effects = new HashMap<>();

        entity.getActivePotionMap().forEach((effect, instance) -> {
            if (!effect.isInstant() && !effect.isBeneficial() && instance.getDuration() > 80) {
                effects.put(effect, instance);
            }
        });

        effects.forEach((effect, instance) -> {
            entity.removeActivePotionEffect(effect);
            entity.addPotionEffect(new EffectInstance(effect, 80, instance.getAmplifier(), instance.isAmbient(), instance.doesShowParticles(), instance.isShowIcon()));
        });
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new AntidoteVesselModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
