package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.RenderTypes;
import artifacts.client.render.model.NightVisionGogglesModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class NightVisionGogglesItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/night_vision_goggles.png");
    private static final ResourceLocation TEXTURE_GLOW = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/night_vision_goggles_glow.png");

    public NightVisionGogglesItem() {
        super(new Item.Properties(), "night_vision_goggles");
    }

    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            public void onCurioTick(String identifier, int index, LivingEntity livingEntity) {
                if (!livingEntity.world.isRemote && livingEntity.ticksExisted % 15 == 0) {
                    livingEntity.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 319, 0, true, false));
                }
            }

            @Override
            public boolean hasRender(String identifier, LivingEntity livingEntity) {
                return true;
            }

            @Override
            public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                if (model == null) {
                    model = new NightVisionGogglesModel();
                }
                NightVisionGogglesModel model = (NightVisionGogglesModel) this.model;
                Curio.RenderHelper.setBodyRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, partialTicks, netHeadYaw, headPitch, model);

                IVertexBuilder buffer = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(TEXTURE), false, stack.hasEffect());
                model.render(matrixStack, buffer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

                buffer = ItemRenderer.getBuffer(renderTypeBuffer, RenderTypes.unlit(TEXTURE_GLOW), false, stack.hasEffect());
                model.render(matrixStack, buffer, 0xF000F0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
        });
    }
}
