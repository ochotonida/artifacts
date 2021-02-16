package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.BunnyHoppersModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class BunnyHoppersItem extends HurtSoundModifyingItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/bunny_hoppers.png");

    public BunnyHoppersItem() {
        super(SoundEvents.ENTITY_RABBIT_HURT);
        addListener(EventPriority.HIGHEST, LivingFallEvent.class, this::onLivingFall);
    }

    public void onLivingFall(LivingFallEvent event) {
        event.setDamageMultiplier(0);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (!livingEntity.world.isRemote && livingEntity.ticksExisted % 15 == 0) {
            livingEntity.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 39, 1, true, false));
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
