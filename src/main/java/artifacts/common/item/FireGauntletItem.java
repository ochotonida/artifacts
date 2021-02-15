package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.RenderTypes;
import artifacts.client.render.model.curio.GloveModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class FireGauntletItem extends GloveItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/fire_gauntlet_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/fire_gauntlet_slim.png");
    private static final ResourceLocation TEXTURE_DEFAULT_GLOW = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/fire_gauntlet_default_glow.png");
    private static final ResourceLocation TEXTURE_SLIM_GLOW = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/fire_gauntlet_slim_glow.png");

    public FireGauntletItem() {
        MinecraftForge.EVENT_BUS.addListener(this::onLivingHurt);
    }

    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource() instanceof EntityDamageSource && !(event.getSource() instanceof IndirectEntityDamageSource) && !((EntityDamageSource) event.getSource()).getIsThornsDamage() && event.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            if (isEquippedBy(attacker) && !event.getEntity().isImmuneToFire()) {
                event.getEntity().setFire(8);
            }
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 1);
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        super.render(identifier, index, matrixStack, buffer, light, entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
        boolean smallArms = hasSmallArms(entity);
        GloveModel model = getModel(smallArms);
        IVertexBuilder builder = ItemRenderer.getBuffer(buffer, RenderTypes.unlit(getGlowTexture(smallArms)), false, false);
        model.renderHand(index % 2 == 0, matrixStack, builder, 0xF000F0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArm(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, AbstractClientPlayerEntity player, HandSide side, boolean hasGlint) {
        if (!player.isSpectator()) {
            super.renderArm(matrixStack, buffer, combinedLight, player, side, hasGlint);

            boolean smallArms = hasSmallArms(player);
            GloveModel model = getModel(smallArms);

            ModelRenderer arm = side == HandSide.LEFT ? model.bipedLeftArm : model.bipedRightArm;
            ModelRenderer armWear = side == HandSide.LEFT ? model.bipedLeftArmwear : model.bipedRightArmwear;

            IVertexBuilder builder = ItemRenderer.getBuffer(buffer, RenderTypes.unlit(getGlowTexture(smallArms)), false, false);
            arm.render(matrixStack, builder, 0xF000F0, OverlayTexture.NO_OVERLAY);
            armWear.render(matrixStack, builder, 0xF000F0, OverlayTexture.NO_OVERLAY);
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE_DEFAULT;
    }

    @Override
    protected ResourceLocation getSlimTexture() {
        return TEXTURE_SLIM;
    }

    protected ResourceLocation getGlowTexture(boolean smallArms) {
        return smallArms ? TEXTURE_SLIM_GLOW : TEXTURE_DEFAULT_GLOW;
    }
}
