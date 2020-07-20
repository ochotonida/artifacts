package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.AntidoteVesselModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class AntidoteVesselItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/antidote_vessel.png");

    public AntidoteVesselItem() {
        super(new Properties(), "antidote_vessel");
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            protected SoundEvent getEquipSound() {
                return SoundEvents.ITEM_BOTTLE_FILL;
            }

            @Override
            public void curioTick(String identifier, int index, LivingEntity entity) {
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
            protected AntidoteVesselModel getModel() {
                if (model == null) {
                    model = new AntidoteVesselModel();
                }
                return (AntidoteVesselModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }
}
