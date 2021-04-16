package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.belt.HeliumFlamingoModel;
import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import artifacts.common.config.Config;
import artifacts.common.init.ModSoundEvents;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
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
import java.util.List;

public class HeliumFlamingoItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/helium_flamingo.png");

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
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        if (Config.isCosmetic(this)) {
            super.appendHoverText(stack, world, tooltip, flags);
        } else if (Config.showTooltips) {
            tooltip.add(new TranslationTextComponent(getDescriptionId() + ".tooltip.0").withStyle(TextFormatting.GRAY));
            tooltip.add(new TranslationTextComponent(getDescriptionId() + ".tooltip.1", Minecraft.getInstance().options.keySprint.getTranslatedKeyMessage()).withStyle(TextFormatting.GRAY));
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
                                || event.player.isInWater() && !event.player.isSwimming() && !handler.isSinking()
                                || (!event.player.isInWater() || handler.isSinking()) && event.player.isOnGround()) {
                            handler.setSwimming(false);
                            if (!event.player.isOnGround() && !event.player.isInWater()) {
                                event.player.playSound(ModSoundEvents.POP.get(), 0.5F, 0.75F);
                            }
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

        private boolean wasSprintKeyDown;
        private boolean wasSprintingOnGround;
        private boolean hasTouchedGround;

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public void onInputUpdate(InputUpdateEvent event) {
            if (Config.isCosmetic(HeliumFlamingoItem.this)) {
                return;
            }

            PlayerEntity player = event.getPlayer();
            boolean isSprintKeyDown = Minecraft.getInstance().options.keySprint.isDown();

            player.getCapability(SwimHandlerCapability.INSTANCE).ifPresent(
                    handler -> {
                        if (!handler.isSwimming()) {
                            if (player.isOnGround()) {
                                hasTouchedGround = true;
                            } else if (!handler.isSwimming()
                                    && player.getAirSupply() > 0
                                    && isEquippedBy(player)
                                    && (player.isSwimming()
                                    || isSprintKeyDown
                                    && !wasSprintKeyDown
                                    && !wasSprintingOnGround
                                    && hasTouchedGround
                                    && !player.isOnGround()
                                    && (!player.isInWater() || handler.isSinking())
                                    && !player.isFallFlying()
                                    && !player.abilities.flying
                                    && !player.isPassenger())) {
                                handler.setSwimming(true);
                                handler.syncSwimming();
                                hasTouchedGround = false;
                            }
                        } else if (player.abilities.flying) {
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

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent(priority = EventPriority.LOW)
        public void render(RenderGameOverlayEvent.Pre event) {
            if (Config.isCosmetic(HeliumFlamingoItem.this)) {
                return;
            }

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
