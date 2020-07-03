package artifacts.common.item;

import artifacts.Artifacts;
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
import top.theillusivec4.curios.api.capability.ICurio;

import javax.annotation.Nullable;

public class NightVisionGogglesItem extends CurioItem {

    private static final ResourceLocation NIGHT_VISION_GOGGLES_TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/night_vision_goggles.png");

    public NightVisionGogglesItem() {
        super(new Item.Properties(), "night_vision_goggles");
    }

    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return createProvider(new CurioItem.Curio() {
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
                ICurio.RenderHelper.followHeadRotations(entity, model.goggles, model.eyeLeftOverlay, model.eyeRightOverlay);
                IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(NIGHT_VISION_GOGGLES_TEXTURE), false, stack.hasEffect());
                model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
        });
    }
}
