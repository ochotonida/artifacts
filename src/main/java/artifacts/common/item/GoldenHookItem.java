package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.GloveModel;
import artifacts.client.render.model.curio.GoldenHookModel;
import artifacts.common.capability.EntityKillTrackerCapability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;

public class GoldenHookItem extends GloveItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/golden_hook_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/golden_hook_slim.png");

    public GoldenHookItem() {
        MinecraftForge.EVENT_BUS.addListener(this::onLivingExperienceDrop);
    }

    private void onLivingExperienceDrop(LivingExperienceDropEvent event) {
        if (event.getAttackingPlayer() != null
                && isEquippedBy(event.getAttackingPlayer())
                && !(event.getEntityLiving() instanceof PlayerEntity)) {
            // actual value is never 0 as livingDeath gets called first (doesn't really matter though)
            double killRatio = event.getAttackingPlayer()
                    .getCapability(EntityKillTrackerCapability.INSTANCE)
                    .map(tracker -> tracker.getKillRatio(event.getEntityLiving().getType()))
                    .orElse(0D);
            // bonus decreases linearly in relation to the ratio kills of the same type in the list of tracked kills
            // no bonus if more than a third of the tracked kills are of the same type
            // maximum bonus is 3 * original XP
            double multiplier = 3 * Math.max(0, 3 * ((1 - killRatio) - 2 / 3D));
            int experienceBonus = (int) (multiplier * Math.min(10, event.getOriginalExperience()));
            event.setDroppedExperience(event.getDroppedExperience() + experienceBonus);
        }
        event.getAttackingPlayer().getCapability(EntityKillTrackerCapability.INSTANCE).ifPresent(tracker -> System.out.println(tracker.getEntityTypes()));
        System.out.println("event.getDroppedExperience() for " + event.getEntityLiving().getType() + " = " + event.getDroppedExperience());
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
        return new GoldenHookModel(smallArms);
    }
}
