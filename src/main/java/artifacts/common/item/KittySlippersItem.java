package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.KittySlippersModel;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import top.theillusivec4.curios.api.CuriosAPI;

public class KittySlippersItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/kitty_slippers.png");

    public KittySlippersItem() {
        super(new Properties(), "kitty_slippers");
        MinecraftForge.EVENT_BUS.addListener(this::onLivingDamage);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingSetAttackTarget);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingUpdate);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
    }

    public void onLivingDamage(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity && CuriosAPI.getCurioEquipped(this, event.getEntityLiving()).isPresent()) {
            event.getEntity().world.playSound(null, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, SoundEvents.ENTITY_CAT_HURT, SoundCategory.PLAYERS, 1, (event.getEntityLiving().getRNG().nextFloat() - event.getEntityLiving().getRNG().nextFloat()) * 0.2F + 1.0F);
        }
    }

    public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        if (event.getEntityLiving() instanceof CreeperEntity && event.getTarget() != null) {
            if (CuriosAPI.getCurioEquipped(this, event.getTarget()).isPresent()) {
                ((MobEntity) event.getEntityLiving()).setAttackTarget(null);
            }
        }
    }

    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof CreeperEntity && event.getEntityLiving().getRevengeTarget() != null) {
            if (CuriosAPI.getCurioEquipped(this, event.getEntityLiving().getRevengeTarget()).isPresent()) {
                event.getEntityLiving().setRevengeTarget(null);
            }
        }
    }

    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof CreeperEntity) {
            ((CreeperEntity) event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>((CreeperEntity) event.getEntity(), PlayerEntity.class, (entity) -> entity != null && CuriosAPI.getCurioEquipped(this, entity).isPresent(), 6, 1, 1.3, EntityPredicates.CAN_AI_TARGET::test));
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            @OnlyIn(Dist.CLIENT)
            protected KittySlippersModel getModel() {
                if (model == null) {
                    model = new KittySlippersModel();
                }
                return (KittySlippersModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }
}
