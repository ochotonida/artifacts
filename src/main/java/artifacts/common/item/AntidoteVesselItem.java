package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.belt.AntidoteVesselModel;
import artifacts.common.config.Config;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.HashMap;
import java.util.Map;

public class AntidoteVesselItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/antidote_vessel.png");

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.BOTTLE_FILL, 1, 1);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (!Config.SERVER.isCosmetic(this)) {
            Map<Effect, EffectInstance> effects = new HashMap<>();

            entity.getActiveEffectsMap().forEach((effect, instance) -> {
                if (!effect.isInstantenous() && !effect.isBeneficial() && instance.getDuration() > 80) {
                    effects.put(effect, instance);
                }
            });

            effects.forEach((effect, instance) -> {
                entity.removeEffectNoUpdate(effect);
                entity.addEffect(new EffectInstance(effect, 80, instance.getAmplifier(), instance.isAmbient(), instance.isVisible(), instance.showIcon()));
            });
        }
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
