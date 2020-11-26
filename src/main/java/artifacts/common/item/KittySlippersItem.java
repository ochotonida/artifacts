package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.KittySlippersModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class KittySlippersItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/kitty_slippers.png");

    public KittySlippersItem() {
        MinecraftForge.EVENT_BUS.addListener(this::onLivingDamage);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingSetAttackTarget);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingUpdate);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
    }

    public void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (CuriosApi.getCuriosHelper().findEquippedCurio(this, entity).isPresent()) {
            entity.world.playSound(
                    null,
                    entity.getPosX(),
                    entity.getPosY(),
                    entity.getPosZ(),
                    SoundEvents.ENTITY_CAT_HURT,
                    SoundCategory.NEUTRAL,
                    1,
                    (entity.getRNG().nextFloat() - entity.getRNG().nextFloat()) * 0.2F + 1
            );
        }
    }

    public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        if (event.getEntityLiving() instanceof CreeperEntity && event.getTarget() != null) {
            if (CuriosApi.getCuriosHelper().findEquippedCurio(this, event.getTarget()).isPresent()) {
                ((MobEntity) event.getEntityLiving()).setAttackTarget(null);
            }
        }
    }

    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof CreeperEntity && event.getEntityLiving().getRevengeTarget() != null) {
            if (CuriosApi.getCuriosHelper().findEquippedCurio(this, event.getEntityLiving().getRevengeTarget()).isPresent()) {
                event.getEntityLiving().setRevengeTarget(null);
            }
        }
    }

    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof CreeperEntity) {
            ((CreeperEntity) event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>((CreeperEntity) event.getEntity(), PlayerEntity.class, (entity) -> entity != null && CuriosApi.getCuriosHelper().findEquippedCurio(this, entity).isPresent(), 6, 1, 1.3, EntityPredicates.CAN_AI_TARGET::test));
        }
    }

    @Override
    protected SoundEvent getEquipSound() {
        return SoundEvents.ENTITY_CAT_AMBIENT;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new KittySlippersModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
