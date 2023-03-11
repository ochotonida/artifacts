package artifacts.common.item.curio;

import artifacts.Artifacts;
import artifacts.common.init.ModGameRules;
import artifacts.common.item.ArtifactItem;
import artifacts.common.network.NetworkHandler;
import artifacts.common.network.ToggleArtifactPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class CurioItem extends ArtifactItem implements ICurioItem {

    protected CurioItem() {
        if (isToggleable()) {
            MinecraftForge.EVENT_BUS.register(new ToggleInputEventHandler());
        }
    }

    public boolean isEquippedBy(@Nullable LivingEntity entity) {
        return entity != null && CuriosApi.getCuriosHelper().findFirstCurio(entity, this).isPresent();
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
        addListener(priority, eventClass, listener, LivingEvent::getEntity);
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
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (prevStack.getItem() != this && !slotContext.entity().level.isClientSide()) {
            setActivated(stack, true);
        }
    }

    protected KeyMapping getToggleKey() {
        return null;
    }

    public boolean isToggleable() {
        return false;
    }

    public void toggleItem(ServerPlayer player) {
        if (isToggleable()) {
            for (SlotResult curio : CuriosApi.getCuriosHelper().findCurios(player, this)) {
                setActivated(curio.stack(), !isActivated(curio.stack()));
            }
        }
    }

    public static boolean isActivated(ItemStack stack) {
        return !stack.hasTag() || stack.getOrCreateTag().getBoolean("isActivated");
    }

    public static void setActivated(ItemStack stack, boolean active) {
        stack.getOrCreateTag().putBoolean("isActivated", active);
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        super.addEffectsTooltip(tooltip);
        if (isToggleable() && !getToggleKey().isUnbound()) {
            tooltip.add(Component.translatable("%s.tooltip.toggle_keymapping".formatted(Artifacts.MODID), getToggleKey().getTranslatedKeyMessage()));
        }
    }

    private class ToggleInputEventHandler {

        private boolean wasToggleKeyDown;

        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            boolean isToggleKeyDown = getToggleKey().isDown();

            if (isToggleKeyDown && !wasToggleKeyDown && ModGameRules.isInitialized() && !isCosmetic()) {
                NetworkHandler.INSTANCE.sendToServer(new ToggleArtifactPacket(CurioItem.this));
            }

            wasToggleKeyDown = isToggleKeyDown;
        }
    }
}
