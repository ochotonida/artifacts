package artifacts.common.item;

import artifacts.client.render.curio.CurioRenderers;
import artifacts.common.config.ModConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CurioItem extends ArtifactItem implements ICurioItem {

    public boolean isEquippedBy(@Nullable LivingEntity entity) {
        return !ModConfig.server.isCosmetic(this) && entity != null && CuriosApi.getCuriosHelper().findEquippedCurio(this, entity).isPresent();
    }

    protected <T extends Event, S extends LivingEntity> void addListener(EventPriority priority, Class<T> eventClass, BiConsumer<T, S> listener, Function<T, S> wearerSupplier) {
        MinecraftForge.EVENT_BUS.addListener(priority, true, eventClass, event -> {
            S wearer = wearerSupplier.apply(event);
            if (isEquippedBy(wearer)) {
                listener.accept(event, wearer);
            }
        });
    }

    protected <T extends Event, S extends LivingEntity> void addListener(Class<T> eventClass, BiConsumer<T, S> listener, Function<T, S> wearerSupplier) {
        addListener(EventPriority.NORMAL, eventClass, listener, wearerSupplier);
    }

    protected <T extends LivingEvent> void addListener(EventPriority priority, Class<T> eventClass, BiConsumer<T, LivingEntity> listener) {
        addListener(priority, eventClass, listener, LivingEvent::getEntityLiving);
    }

    protected <T extends LivingEvent> void addListener(Class<T> eventClass, BiConsumer<T, LivingEntity> listener) {
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
        return CurioRenderers.getRenderer(this) != null;
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ticks, float headYaw, float headPitch, ItemStack stack) {
        CurioRenderers.getRenderer(this).render(identifier, index, matrixStack, buffer, light, entity, limbSwing, limbSwingAmount, partialTicks, ticks, headYaw, headPitch, stack);
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
}
