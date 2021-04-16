package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.RenderTypes;
import artifacts.client.render.model.curio.hands.HandsModel;
import artifacts.common.util.DamageSourceHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class FireGauntletItem extends GloveItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/fire_gauntlet_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/fire_gauntlet_slim.png");
    private static final ResourceLocation TEXTURE_DEFAULT_GLOW = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/fire_gauntlet_default_glow.png");
    private static final ResourceLocation TEXTURE_SLIM_GLOW = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/fire_gauntlet_slim_glow.png");

    public FireGauntletItem() {
        addListener(LivingHurtEvent.class, this::onLivingHurt, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    public void onLivingHurt(LivingHurtEvent event) {
        if (DamageSourceHelper.isMeleeAttack(event.getSource()) && !event.getEntity().fireImmune()) {
            event.getEntity().setSecondsOnFire(8);
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_IRON, 1, 1);
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        super.render(identifier, index, matrixStack, buffer, light, entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
        boolean smallArms = hasSmallArms(entity);
        HandsModel model = getModel(smallArms);
        IVertexBuilder builder = ItemRenderer.getFoilBuffer(buffer, RenderTypes.unlit(getGlowTexture(smallArms)), false, false);
        model.renderHand(index % 2 == 0, matrixStack, builder, 0xF000F0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArm(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, AbstractClientPlayerEntity player, HandSide side, boolean hasFoil) {
        if (!player.isSpectator()) {
            super.renderArm(matrixStack, buffer, combinedLight, player, side, hasFoil);

            boolean smallArms = hasSmallArms(player);
            HandsModel model = getModel(smallArms);

            ModelRenderer arm = side == HandSide.LEFT ? model.leftArm : model.rightArm;

            IVertexBuilder builder = ItemRenderer.getFoilBuffer(buffer, RenderTypes.unlit(getGlowTexture(smallArms)), false, false);
            arm.render(matrixStack, builder, 0xF000F0, OverlayTexture.NO_OVERLAY);
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
