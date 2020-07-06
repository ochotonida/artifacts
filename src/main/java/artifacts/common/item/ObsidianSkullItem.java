package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.ObsidianSkullModel;
import artifacts.common.init.Items;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;

import javax.annotation.Nullable;

public class ObsidianSkullItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/obsidian_skull.png");

    public ObsidianSkullItem() {
        super(new Properties(), "obsidian_skull");
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            public boolean hasRender(String identifier, LivingEntity livingEntity) {
                return true;
            }

            @Override
            public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                if (!(model instanceof ObsidianSkullModel)) {
                    model = new ObsidianSkullModel();
                }
                ObsidianSkullModel model = (ObsidianSkullModel) this.model;
                RenderHelper.setBodyRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, partialTicks, netHeadYaw, headPitch, model);
                //ICurio.RenderHelper.translateIfSneaking(matrixStack, entity);
                IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(TEXTURE), false, stack.hasEffect());
                model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
        });
    }

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    @SuppressWarnings("unused")
    public static class Events {

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (!event.getEntity().world.isRemote && event.getAmount() >= 1 && (event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.LAVA) && event.getEntity() instanceof PlayerEntity) {
                if (CuriosAPI.getCurioEquipped(Items.OBSIDIAN_SKULL, event.getEntityLiving()).isPresent() && !((PlayerEntity) event.getEntity()).getCooldownTracker().hasCooldown(Items.OBSIDIAN_SKULL)) {
                    event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 600, 0, false, true));
                    ((PlayerEntity) event.getEntity()).getCooldownTracker().setCooldown(Items.OBSIDIAN_SKULL, 2400);
                }
            }
        }
    }
}
