package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.RenderTypes;
import artifacts.client.render.model.curio.PendantModel;
import artifacts.common.init.Items;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurio;

import javax.annotation.Nullable;

public class PendantItem extends ArtifactItem {

    private final ResourceLocation texture;

    public PendantItem(String name) {
        super(new Properties(), name);
        texture = new ResourceLocation(Artifacts.MODID, String.format("textures/entity/curio/%s.png", name));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            protected SoundEvent getEquipSound() {
                return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
            }

            @Override
            public boolean hasRender(String identifier, LivingEntity livingEntity) {
                return true;
            }

            @Override
            public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                if (!(model instanceof PendantModel)) {
                    model = new PendantModel();
                }
                PendantModel model = (PendantModel) this.model;
                ICurio.RenderHelper.translateIfSneaking(matrixStack, entity);
                ICurio.RenderHelper.rotateIfSneaking(matrixStack, entity);
                IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, RenderTypes.translucent(texture), false, stack.hasEffect());
                model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
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
