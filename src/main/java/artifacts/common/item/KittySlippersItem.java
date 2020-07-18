package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.KittySlippersModel;
import artifacts.common.init.Items;
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
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;

public class KittySlippersItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/kitty_slippers.png");

    public KittySlippersItem() {
        super(new Properties(), "kitty_slippers");
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            protected KittySlippersModel getModel() {
                if (model == null) {
                    model = new KittySlippersModel();
                }
                return (KittySlippersModel) model;
            }

            @Override
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    @SuppressWarnings("unused")
    public static class Events {

        @SubscribeEvent
        public static void onLivingDamage(LivingDamageEvent event) {
            if (event.getEntityLiving() instanceof PlayerEntity && CuriosAPI.getCurioEquipped(Items.KITTY_SLIPPERS, event.getEntityLiving()).isPresent()) {
                event.getEntity().world.playSound(null, event.getEntityLiving().getPosX(), event.getEntityLiving().getPosY(), event.getEntityLiving().getPosZ(), SoundEvents.ENTITY_CAT_HURT, SoundCategory.PLAYERS, 1, (event.getEntityLiving().getRNG().nextFloat() - event.getEntityLiving().getRNG().nextFloat()) * 0.2F + 1.0F);
            }
        }

        @SubscribeEvent
        public static void onSetAttackTarget(LivingSetAttackTargetEvent event) {
            if (event.getEntityLiving() instanceof CreeperEntity && event.getTarget() != null) {
                if (CuriosAPI.getCurioEquipped(Items.KITTY_SLIPPERS, event.getTarget()).isPresent()) {
                    ((MobEntity) event.getEntityLiving()).setAttackTarget(null);
                }
            }
        }

        @SubscribeEvent
        public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
            if (event.getEntityLiving() instanceof CreeperEntity && event.getEntityLiving().getRevengeTarget() != null) {
                if (CuriosAPI.getCurioEquipped(Items.KITTY_SLIPPERS, event.getEntityLiving().getRevengeTarget()).isPresent()) {
                    event.getEntityLiving().setRevengeTarget(null);
                }
            }
        }

        @SubscribeEvent
        public static void onJoinWorld(EntityJoinWorldEvent event) {
            if (event.getEntity() instanceof CreeperEntity) {
                ((CreeperEntity) event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>((CreeperEntity) event.getEntity(), PlayerEntity.class, (entity) -> entity != null && CuriosAPI.getCurioEquipped(Items.KITTY_SLIPPERS, entity).isPresent(), 6, 1, 1.3, EntityPredicates.CAN_AI_TARGET::test));
            }
        }
    }
}
