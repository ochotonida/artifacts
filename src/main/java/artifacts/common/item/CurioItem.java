package artifacts.common.item;

import artifacts.common.config.ModConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class CurioItem extends ArtifactItem implements ICurioItem {

    private Object model;

    public boolean isEquippedBy(@Nullable LivingEntity entity) {
        return !ModConfig.server.isCosmetic(this) && entity != null && CuriosApi.getCuriosHelper().findEquippedCurio(this, entity).isPresent();
    }

    protected <T extends Event> void addListener(EventPriority priority, Class<T> eventClass, Consumer<T> listener, Function<T, LivingEntity> livingEntitySupplier) {
        MinecraftForge.EVENT_BUS.addListener(priority, true, eventClass, event -> {
            if (isEquippedBy(livingEntitySupplier.apply(event))) {
                listener.accept(event);
            }
        });
    }

    protected <T extends Event> void addListener(Class<T> eventClass, Consumer<T> listener, Function<T, LivingEntity> livingEntitySupplier) {
        addListener(EventPriority.NORMAL, eventClass, listener, livingEntitySupplier);
    }

    protected <T extends LivingEvent> void addListener(EventPriority priority, Class<T> eventClass, Consumer<T> listener) {
        addListener(priority, eventClass, listener, LivingEvent::getEntityLiving);
    }

    protected <T extends LivingEvent> void addListener(Class<T> eventClass, Consumer<T> listener) {
        addListener(EventPriority.NORMAL, eventClass, listener);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        return true;
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        BipedModel<LivingEntity> model = getModel();
        model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
        ICurio.RenderHelper.followBodyRotations(entity, model);
        IVertexBuilder vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, model.renderType(getTexture()), false, stack.hasFoil());
        model.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    protected void damageStack(String identifier, int index, LivingEntity entity, ItemStack stack) {
        damageStack(identifier, index, entity, stack, 1);
    }

    protected void damageStack(String identifier, int index, LivingEntity entity, ItemStack stack, int damage) {
        stack.hurtAndBreak(damage, entity, damager ->
                CuriosApi.getCuriosHelper().onBrokenCurio(identifier, index, damager)
        );
    }

    protected void damageEquippedStacks(LivingEntity entity, int damage) {
        CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(curiosHandler ->
                curiosHandler.getCurios().forEach((identifier, stacksHandler) -> {
                    IDynamicStackHandler stacks = stacksHandler.getStacks();
                    for (int slot = 0; slot < stacks.getSlots(); slot++) {
                        ItemStack stack = stacks.getStackInSlot(slot);
                        if (!stack.isEmpty() && stack.getItem() == this) {
                            damageStack(identifier, slot, entity, stack, damage);
                        }
                    }
                })
        );
    }

    public void damageEquippedStacks(LivingEntity entity) {
        damageEquippedStacks(entity, 1);
    }

    @OnlyIn(Dist.CLIENT)
    protected final BipedModel<LivingEntity> getModel() {
        if (model == null) {
            model = createModel();
        }

        //noinspection unchecked
        return (BipedModel<LivingEntity>) model;
    }

    @OnlyIn(Dist.CLIENT)
    protected abstract BipedModel<LivingEntity> createModel();

    protected abstract ResourceLocation getTexture();
}
