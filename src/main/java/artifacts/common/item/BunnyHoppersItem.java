package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.feet.BunnyHoppersModel;
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
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class BunnyHoppersItem extends HurtSoundModifyingItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/bunny_hoppers.png");

    public BunnyHoppersItem() {
        super(SoundEvents.RABBIT_HURT);
        addListener(EventPriority.HIGH, LivingFallEvent.class, this::onLivingFall);
        addListener(LivingEvent.LivingJumpEvent.class, this::onLivingJump);
    }

    public void onLivingFall(LivingFallEvent event) {
        if (ModConfig.server.bunnyHoppers.shouldCancelFallDamage.get()) {
            event.setDamageMultiplier(0);
        }
    }

    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        damageEquippedStacks(event.getEntityLiving());
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        int jumpBoostLevel = ModConfig.server.bunnyHoppers.jumpBoostLevel.get() - 1;
        if (!ModConfig.server.isCosmetic(this) && !livingEntity.level.isClientSide && livingEntity.tickCount % 15 == 0 && jumpBoostLevel >= 0) {
            livingEntity.addEffect(new EffectInstance(Effects.JUMP, 39, jumpBoostLevel, true, false));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new BunnyHoppersModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
