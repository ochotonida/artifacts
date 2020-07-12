package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.PendantModel;
import artifacts.common.init.Items;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;

public class PendantItem extends ArtifactItem {

    private final ResourceLocation texture;

    public PendantItem(String name) {
        super(new Properties(), name);
        texture = new ResourceLocation(Artifacts.MODID, String.format("textures/entity/curio/%s.png", name));
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            protected SoundEvent getEquipSound() {
                return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected PendantModel getModel() {
                if (model == null) {
                    model = new PendantModel();
                }
                return (PendantModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return texture;
            }
        });
    }

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    @SuppressWarnings("unused")
    public static class Events {

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (!event.getEntity().world.isRemote && event.getAmount() >= 1) {
                if (event.getSource() == DamageSource.LIGHTNING_BOLT && CuriosAPI.getCurioEquipped(Items.SHOCK_PENDANT, event.getEntityLiving()).isPresent()) {
                    event.setCanceled(true);
                } else if (event.getSource().getTrueSource() instanceof LivingEntity) {
                    if (CuriosAPI.getCurioEquipped(Items.SHOCK_PENDANT, event.getEntityLiving()).isPresent()) {
                        LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
                        if (attacker.world.canSeeSky(attacker.getPosition()) && event.getEntityLiving().getRNG().nextFloat() < 0.20F) {
                            ((ServerWorld) attacker.world).addLightningBolt(new LightningBoltEntity(attacker.world, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), false));
                        }
                    }
                    if (CuriosAPI.getCurioEquipped(Items.FLAME_PENDANT, event.getEntityLiving()).isPresent()) {
                        LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
                        if (!attacker.isImmuneToFire() && attacker.attackable() && event.getEntityLiving().getRNG().nextFloat() < 0.30F) {
                            attacker.setFire(4);
                            attacker.attackEntityFrom(new EntityDamageSource("onFire", event.getEntity()).setFireDamage(), 2);
                        }
                    }
                    if (CuriosAPI.getCurioEquipped(Items.THORN_PENDANT, event.getEntityLiving()).isPresent()) {
                        LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
                        if (attacker.attackable() && random.nextFloat() < 0.45F) {
                            attacker.attackEntityFrom(DamageSource.causeThornsDamage(event.getEntity()), 1 + event.getEntityLiving().getRNG().nextInt(4));
                        }
                    }
                }
            }
        }
    }
}
