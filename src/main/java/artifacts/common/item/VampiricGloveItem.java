package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class VampiricGloveItem extends GloveItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/vampiric_glove_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/vampiric_glove_slim.png");

    public VampiricGloveItem() {
        addListener(EventPriority.LOWEST, LivingDamageEvent.class, this::onLivingDamage, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    private void onLivingDamage(LivingDamageEvent event, LivingEntity wearer) {
        if (DamageSourceHelper.isMeleeAttack(event.getSource())) {
            int maxHealthAbsorbed = ModConfig.server.vampiricGlove.maxHealthAbsorbed.get();
            float absorptionRatio = (float) (double) ModConfig.server.vampiricGlove.absorptionRatio.get();

            float damageDealt = Math.min(event.getAmount(), event.getEntityLiving().getHealth());
            float damageAbsorbed = Math.min(maxHealthAbsorbed, absorptionRatio * damageDealt);
            if (damageAbsorbed >= 1) {
                wearer.heal(damageAbsorbed);
            }

            damageEquippedStacks(wearer);
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE_DEFAULT;
    }

    @Override
    protected ResourceLocation getSlimTexture() {
        return TEXTURE_SLIM;
    }
}
