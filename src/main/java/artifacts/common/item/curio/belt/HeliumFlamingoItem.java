package artifacts.common.item.curio.belt;

import artifacts.common.capability.SwimHandler;
import artifacts.common.config.ModConfig;
import artifacts.common.init.ModSoundEvents;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.List;

public class HeliumFlamingoItem extends CurioItem {

    public HeliumFlamingoItem() {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(ModSoundEvents.POP.get(), 1, 0.7F);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        if (ModConfig.server != null && ModConfig.server.isCosmetic(this)) {
            super.appendHoverText(stack, world, tooltip, flags);
        } else if (ModConfig.client.showTooltips.get()) {
            tooltip.add(new TranslatableComponent(getDescriptionId() + ".tooltip.0").withStyle(ChatFormatting.GRAY));
            tooltip.add(new TranslatableComponent(getDescriptionId() + ".tooltip.1", Minecraft.getInstance().options.keySprint.getTranslatedKeyMessage()).withStyle(ChatFormatting.GRAY));
        }
    }

    private void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        event.player.getCapability(SwimHandler.CAPABILITY).ifPresent(
                handler -> {
                    int maxFlightTime = ModConfig.server.heliumFlamingo.maxFlightTime.get();
                    int rechargeTime = ModConfig.server.heliumFlamingo.rechargeTime.get();

                    if (handler.isSwimming()) {
                        if (!isEquippedBy(event.player)
                                || handler.getSwimTime() > maxFlightTime
                                || event.player.isInWater() && !event.player.isSwimming() && !handler.isSinking()
                                || (!event.player.isInWater() || handler.isSinking()) && event.player.isOnGround()) {
                            handler.setSwimming(false);
                            if (!event.player.isOnGround() && !event.player.isInWater()) {
                                event.player.playSound(ModSoundEvents.POP.get(), 0.5F, 0.75F);
                            }
                        }

                        if (isEquippedBy(event.player) && !event.player.isEyeInFluid(FluidTags.WATER)) {
                            if (event.player.tickCount % 20 == 0) {
                                damageEquippedStacks(event.player);
                            }
                            if (!event.player.getAbilities().invulnerable && maxFlightTime > 0) {
                                handler.setSwimTime(handler.getSwimTime() + 1);
                            }
                        }
                    } else if (handler.getSwimTime() < 0) {
                        handler.setSwimTime(
                                handler.getSwimTime() < -rechargeTime
                                        ? -rechargeTime
                                        : handler.getSwimTime() + 1
                        );
                    }
                }
        );
    }

    private class ClientEventHandler {

        // TODO private final ResourceLocation location = new ResourceLocation(Artifacts.MODID, "textures/gui/icons.png");

        private boolean wasSprintKeyDown;
        private boolean wasSprintingOnGround;
        private boolean hasTouchedGround;

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public void onInputUpdate(InputUpdateEvent event) {
            if (ModConfig.server.isCosmetic(HeliumFlamingoItem.this)) {
                return;
            }

            Player player = event.getPlayer();
            boolean isSprintKeyDown = Minecraft.getInstance().options.keySprint.isDown();

            player.getCapability(SwimHandler.CAPABILITY).ifPresent(
                    handler -> {
                        if (!handler.isSwimming()) {
                            if (player.isOnGround()) {
                                hasTouchedGround = true;
                            } else if (!handler.isSwimming()
                                    && handler.getSwimTime() >= 0
                                    && isEquippedBy(player)
                                    && (player.isSwimming()
                                    || isSprintKeyDown
                                    && !wasSprintKeyDown
                                    && !wasSprintingOnGround
                                    && hasTouchedGround
                                    && !player.isOnGround()
                                    && (!player.isInWater() || handler.isSinking())
                                    && !player.isFallFlying()
                                    && !player.getAbilities().flying
                                    && !player.isPassenger())) {
                                handler.setSwimming(true);
                                handler.syncSwimming();
                                hasTouchedGround = false;
                            }
                        } else if (player.getAbilities().flying) {
                            handler.setSwimming(false);
                            handler.syncSwimming();
                            hasTouchedGround = true;
                        }
                    }
            );

            wasSprintKeyDown = isSprintKeyDown;
            if (!isSprintKeyDown) {
                wasSprintingOnGround = false;
            } else if (player.isOnGround()) {
                wasSprintingOnGround = true;
            }
        }

        /* TODO
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent(priority = EventPriority.LOW)
        public void render(RenderGameOverlayEvent.Post event) {
            Minecraft minecraft = Minecraft.getInstance();

            if (ModConfig.server.isCosmetic(HeliumFlamingoItem.this) || !(minecraft.getCameraEntity() instanceof LivingEntity)) {
                return;
            }

            LivingEntity player = (LivingEntity) minecraft.getCameraEntity();

            if (event.getType() != RenderGameOverlayEvent.ElementType.AIR || event.isCanceled() || !isEquippedBy(player)) {
                return;
            }

            player.getCapability(SwimHandlerCapability.INSTANCE).ifPresent(
                    handler -> {
                        int left = minecraft.getWindow().getGuiScaledWidth() / 2 + 91;
                        int top = minecraft.getWindow().getGuiScaledHeight() - ForgeIngameGui.right_height;

                        int swimTime = Math.abs(handler.getSwimTime());
                        int maxProgressTime;

                        if (swimTime == 0) {
                            return;
                        } else if (handler.getSwimTime() > 0) {
                            maxProgressTime = ModConfig.server.heliumFlamingo.maxFlightTime.get();
                        } else {
                            maxProgressTime = ModConfig.server.heliumFlamingo.rechargeTime.get();
                        }

                        float progress = 1 - swimTime / (float) maxProgressTime;

                        Minecraft.getInstance().getTextureManager().bind(location);
                        RenderSystem.enableBlend();

                        int full = Mth.ceil((progress - 2D / maxProgressTime) * 10);
                        int partial = Mth.ceil(progress * 10) - full;

                        for (int i = 0; i < full + partial; ++i) {
                            ForgeIngameGui.blit(event.getMatrixStack(), left - i * 8 - 9, top, -90, (i < full ? 0 : 9), 0, 9, 9, 16, 32);
                        }
                        ForgeIngameGui.right_height += 10;

                        RenderSystem.disableBlend();
                    }
            );
        } */
    }
}
