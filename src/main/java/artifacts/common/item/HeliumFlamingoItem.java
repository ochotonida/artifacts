package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.HeliumFlamingoModel;
import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;

public class HeliumFlamingoItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/helium_flamingo.png");

    public HeliumFlamingoItem() {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(this::onInputUpdate);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ITEM_PICKUP, 1, 0.7F);
    }

    private boolean wasSprintKeyDown;
    private boolean wasSprintingOnGround;

    public void onInputUpdate(InputUpdateEvent event) {
        // TODO prevent swimming while still in air
        // TODO maybe not swim cancelling

        PlayerEntity player = event.getPlayer();
        boolean isSprintKeyDown = Minecraft.getInstance().options.keySprint.isDown();

        player.getCapability(SwimHandlerCapability.INSTANCE).ifPresent(
                handler -> {
                    if (!handler.isSwimming()
                            && player.getAirSupply() > 0
                            && isEquippedBy(player)
                            && (player.isSwimming()
                            || isSprintKeyDown
                            && !wasSprintKeyDown
                            && !wasSprintingOnGround
                            && !player.isOnGround()
                            && !player.isInWater()
                            && !player.isFallFlying()
                            && !player.isPassenger())) {
                        handler.setSwimming(true);
                        handler.syncSwimming();
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

    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        event.player.getCapability(SwimHandlerCapability.INSTANCE).ifPresent(
                handler -> {
                    if (handler.isSwimming()) {
                        if (!isEquippedBy(event.player)
                                || event.player.getAirSupply() <= 0
                                || event.player.isInWater() && !event.player.isSwimming()
                                || !event.player.isInWater() && event.player.isOnGround()) {
                            handler.setSwimming(false);
                        }

                        if (isEquippedBy(event.player) && !event.player.isEyeInFluid(FluidTags.WATER) && !event.player.abilities.invulnerable) {
                            // compensate for bonus air
                            int airSupply = event.player.getAirSupply() - 4;
                            event.player.setAirSupply(airSupply - 2);
                        }
                    }
                }
        );
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new HeliumFlamingoModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    public class ClientEventHandler {

        private final ResourceLocation location = new ResourceLocation(Artifacts.MODID, "textures/gui/icons.png");

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent(priority = EventPriority.LOW)
        public void render(RenderGameOverlayEvent.Pre event) {
            Minecraft minecraft = Minecraft.getInstance();
            PlayerEntity player = (PlayerEntity) minecraft.getCameraEntity();
            if (event.getType() == RenderGameOverlayEvent.ElementType.AIR && !event.isCanceled() && isEquippedBy(player)) {
                event.setCanceled(true);
                Minecraft.getInstance().getTextureManager().bind(location);
                RenderSystem.enableBlend();
                int left = minecraft.getWindow().getGuiScaledWidth() / 2 + 91;
                int top = minecraft.getWindow().getGuiScaledHeight() - ForgeIngameGui.right_height;
                // noinspection ConstantConditions
                int air = player.getAirSupply();
                if (player.isEyeInFluid(FluidTags.WATER) || air < 300) {
                    int full = MathHelper.ceil((air - 2) * 10 / 300D);
                    int partial = MathHelper.ceil(air * 10 / 300D) - full;

                    for (int i = 0; i < full + partial; ++i) {
                        ForgeIngameGui.blit(event.getMatrixStack(), left - i * 8 - 9, top, -90, (i < full ? 0 : 9), 0, 9, 9, 16, 32);
                    }
                    ForgeIngameGui.right_height += 10;
                }
                RenderSystem.disableBlend();
            }
        }
    }
}
