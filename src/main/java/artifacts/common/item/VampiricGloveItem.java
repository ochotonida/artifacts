package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.GloveModel;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class VampiricGloveItem extends GloveItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/vampiric_glove_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/vampiric_glove_slim.png");

    public VampiricGloveItem() {
        addListener(EventPriority.LOWEST, LivingDamageEvent.class, this::onLivingDamage, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    public void onLivingDamage(LivingDamageEvent event) {
        if (DamageSourceHelper.isMeleeAttack(event.getSource())) {
            float damageDealt = Math.min(event.getAmount(), event.getEntityLiving().getHealth());
            if (damageDealt > 4) {
                // noinspection ConstantConditions
                DamageSourceHelper.getAttacker(event.getSource()).heal(Math.min(2, damageDealt / 4));
            }
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

    @Override
    @OnlyIn(Dist.CLIENT)
    protected GloveModel createModel(boolean smallArms) {
        return super.createModel(smallArms);
    }
}
