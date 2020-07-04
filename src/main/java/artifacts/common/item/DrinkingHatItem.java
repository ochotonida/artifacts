package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.DrinkingHatModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;

import javax.annotation.Nullable;

public class DrinkingHatItem extends CurioItem {

    private static final ResourceLocation DRINKING_HAT_TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/plastic_drinking_hat.png");
    private static final ResourceLocation NOVELTY_DRINKING_HAT_TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/novelty_drinking_hat.png");
    private final boolean isNoveltyHat;

    public DrinkingHatItem(String name, boolean isNoveltyHat) {
        super(new Properties(), name);
        this.isNoveltyHat = isNoveltyHat;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return isNoveltyHat ? Rarity.EPIC : Rarity.RARE;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            protected SoundEvent getEquipSound() {
                return SoundEvents.ITEM_BOTTLE_FILL;
            }

            @Override
            public boolean hasRender(String identifier, LivingEntity livingEntity) {
                return true;
            }

            @Override
            public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                if (!(model instanceof DrinkingHatModel)) {
                    model = new DrinkingHatModel();
                }
                DrinkingHatModel model = (DrinkingHatModel) this.model;
                model.setModelVisibilities(isNoveltyHat);
                Curio.RenderHelper.setBodyRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, partialTicks, netHeadYaw, headPitch, model);
                IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(isNoveltyHat ? NOVELTY_DRINKING_HAT_TEXTURE : DRINKING_HAT_TEXTURE), false, stack.hasEffect());
                model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
        });
    }

    @Mod.EventBusSubscriber
    @SuppressWarnings("unused")
    public static class Events {

        @SubscribeEvent
        public static void onItemUseStart(LivingEntityUseItemEvent.Start event) {
            if (CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof DrinkingHatItem, event.getEntityLiving()).isPresent()) {
                if (event.getItem().getUseAction() == UseAction.DRINK) {
                    event.setDuration(event.getDuration() / 4);
                }
            }
        }
    }
}
