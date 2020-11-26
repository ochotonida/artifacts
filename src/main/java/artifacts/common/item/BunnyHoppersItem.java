package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.BunnyHoppersModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.CuriosApi;

public class BunnyHoppersItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/bunny_hoppers.png");

    public BunnyHoppersItem() {
        MinecraftForge.EVENT_BUS.addListener(this::onLivingDamage);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, this::onLivingFall);
    }

    public void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (CuriosApi.getCuriosHelper().findEquippedCurio(this, entity).isPresent()) {
            event.getEntity().world.playSound(
                    null,
                    entity.getPosX(),
                    entity.getPosY(),
                    entity.getPosZ(),
                    SoundEvents.ENTITY_RABBIT_HURT,
                    SoundCategory.NEUTRAL,
                    1,
                    (entity.getRNG().nextFloat() - entity.getRNG().nextFloat()) * 0.2F + 1
            );
        }
    }

    public void onLivingFall(LivingFallEvent event) {
        if (CuriosApi.getCuriosHelper().findEquippedCurio(this, event.getEntityLiving()).isPresent()) {
            event.setDamageMultiplier(0);
        }
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
