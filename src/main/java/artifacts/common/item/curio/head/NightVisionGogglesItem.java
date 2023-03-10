package artifacts.common.item.curio.head;

import artifacts.common.init.ModGameRules;
import artifacts.common.init.ModItems;
import artifacts.common.init.ModKeyMappings;
import artifacts.common.item.curio.MobEffectItem;
import artifacts.common.network.NetworkHandler;
import artifacts.common.network.ToggleNightVisionGogglesPacket;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;
import java.util.Optional;

public class NightVisionGogglesItem extends MobEffectItem {

    public NightVisionGogglesItem() {
        super(MobEffects.NIGHT_VISION, 320, ModGameRules.NIGHT_VISION_GOGGLES_ENABLED);
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        tooltip.add(tooltipLine("night_vision"));
        if (!ModKeyMappings.TOGGLE_NIGHT_VISION_GOGGLES.isUnbound()) {
            tooltip.add(tooltipLine("keymapping", ModKeyMappings.TOGGLE_NIGHT_VISION_GOGGLES.getTranslatedKeyMessage()));
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        if (prevStack.getItem() != this && !slotContext.entity().level.isClientSide()) {
            setActive(stack, true);
        }
    }

    public static void toggle(ServerPlayer player) {
        for (SlotResult curio : CuriosApi.getCuriosHelper().findCurios(player, ModItems.NIGHT_VISION_GOGGLES.get())) {
            setActive(curio.stack(), !isToggledOn(curio.stack()));
        }
    }

    public boolean isActive(LivingEntity entity) {
        if (!ModGameRules.NIGHT_VISION_GOGGLES_ENABLED.get()) {
            return false;
        }
        Optional<SlotResult> curio = CuriosApi.getCuriosHelper().findFirstCurio(entity, this);
        return curio.isPresent() && isToggledOn(curio.get().stack());
    }

    private static boolean isToggledOn(ItemStack stack) {
        return !stack.hasTag() || stack.getOrCreateTag().getBoolean("isActive");
    }

    public static void setActive(ItemStack stack, boolean active) {
        stack.getOrCreateTag().putBoolean("isActive", active);
    }

    private static class ClientEventHandler {

        private boolean wasToggleKeyDown;

        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            boolean isToggleKeyDown = ModKeyMappings.TOGGLE_NIGHT_VISION_GOGGLES.isDown();

            if (isToggleKeyDown && !wasToggleKeyDown && ModGameRules.NIGHT_VISION_GOGGLES_ENABLED.get()) {
                NetworkHandler.INSTANCE.sendToServer(new ToggleNightVisionGogglesPacket());
            }

            wasToggleKeyDown = isToggleKeyDown;
        }
    }
}
