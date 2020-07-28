package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.RenderTypes;
import artifacts.client.render.model.curio.GloveModel;
import artifacts.common.init.Items;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;

public class FireGauntletItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/fire_gauntlet_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/fire_gauntlet_slim.png");
    private static final ResourceLocation TEXTURE_DEFAULT_GLOW = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/fire_gauntlet_default_glow.png");
    private static final ResourceLocation TEXTURE_SLIM_GLOW = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/fire_gauntlet_slim_glow.png");

    public FireGauntletItem() {
        super(new Properties(), "fire_gauntlet");
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new GloveCurio(this) {

            @Override
            protected SoundEvent getEquipSound() {
                return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                boolean smallArms = hasSmallArms(entity);
                GloveModel model = getModel(smallArms);
                model.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                model.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
                RenderHelper.followBodyRotations(entity, model);
                IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(getTexture(smallArms)), false, false);
                model.renderHand(index == 0, matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, RenderTypes.unlit(getGlowTexture(smallArms)), false, false);
                model.renderHand(index == 0, matrixStack, vertexBuilder, 0xF000F0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE_DEFAULT;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getSlimTexture() {
                return TEXTURE_SLIM;
            }

            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getGlowTexture(boolean smallArms) {
                return smallArms ? TEXTURE_SLIM_GLOW : TEXTURE_DEFAULT_GLOW;
            }
        });
    }

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    @SuppressWarnings("unused")
    public static class Events {

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (event.getSource() instanceof EntityDamageSource && !(event.getSource() instanceof IndirectEntityDamageSource) && !((EntityDamageSource) event.getSource()).getIsThornsDamage()) {
                if (event.getSource().getTrueSource() instanceof LivingEntity) {
                    LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
                    if (CuriosAPI.getCurioEquipped(Items.FIRE_GAUNTLET, attacker).isPresent()) {
                        if (!event.getEntity().isImmuneToFire()) {
                            event.getEntity().setFire(8);
                        }
                    }
                }
            }
        }
    }
}
