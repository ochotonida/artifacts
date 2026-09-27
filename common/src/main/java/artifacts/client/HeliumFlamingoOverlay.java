package artifacts.client;

import artifacts.Artifacts;
import artifacts.component.SwimData;
import artifacts.component.ability.SwimInAir;
import artifacts.mixin.accessors.client.HudAccessor;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class HeliumFlamingoOverlay {

    private static final int NUM_AIR_BUBBLES = 10;
    private static final int AIR_BUBBLE_SIZE = 9;
    private static final int AIR_BUBBLE_SEPARATION = 8;
    private static final int AIR_BUBBLE_POPPING_DURATION = 2;
    private static final int EMPTY_AIR_BUBBLE_DELAY_DURATION = 2;
    private static final float AIR_BUBBLE_POP_SOUND_VOLUME_BASE = 0.5F;
    private static final float AIR_BUBBLE_POP_SOUND_VOLUME_INCREMENT = 0.1F;
    private static final float AIR_BUBBLE_POP_SOUND_PITCH_BASE = 1.0F;
    private static final float AIR_BUBBLE_POP_SOUND_PITCH_INCREMENT = 0.1F;
    private static final int NUM_AIR_BUBBLE_POPPED_BEFORE_SOUND_VOLUME_INCREASE = 3;
    private static final int NUM_AIR_BUBBLE_POPPED_BEFORE_SOUND_PITCH_INCREASE = 5;

    private static final Identifier AIR_SPRITE = Artifacts.id("hud/flamingo");
    private static final Identifier AIR_POPPING_SPRITE = Artifacts.id("hud/flamingo_bursting");
    private static final Identifier AIR_EMPTY_SPRITE = Artifacts.id("hud/flamingo_empty");

    private int lastBubblePopSoundPlayed = 0;

    // Largely identical to Gui::renderAirBubbles
    public boolean renderOverlay(GuiGraphicsExtractor graphics, Player player, int height) {
        SwimData swimData = PlatformServices.getPlatformHelper().getSwimData(player);
        if (!ModDataComponents.SWIM_IN_AIR.on(player).includeItemsOnCooldown().findAny() || swimData == null) {
            return false;
        }

        HudAccessor hud = (HudAccessor) Minecraft.getInstance().gui.hud;
        boolean isLosingCharge = swimData.shouldDepleteSwimFlyingCharge(player);

        // duration in ticks to fully charge/deplete
        int maxProgress = isLosingCharge
                ? SwimInAir.getMaxFlightDuration(player)
                : SwimInAir.getRechargeDuration(player);

        // current charging/depletion level, in ticks
        int progress = (int) Math.floor(swimData.getSwimFlyingCharge() * maxProgress);

        if (!isLosingCharge && progress >= maxProgress) {
            // fully charged and not depleting
            return false;
        }

        int hotbarEdge = graphics.guiWidth() / 2 + 91;
        // Set height from bottom of screen, add config offset
        height = graphics.guiHeight() - height - Artifacts.CONFIG.client.heliumFlamingoOverlayOffset.get();

        // the number of full bubbles (rendered right to left, index starting at 1)
        int fullBubble = getCurrentAirSupplyBubble(progress, maxProgress, -AIR_BUBBLE_POPPING_DURATION);
        // the location of the currently popping bubble sprite
        // only rendered when poppingBubble > fullBubble,
        // i.e. for AIR_BUBBLE_POPPING_DURATION ticks after a full bubble disappears
        int poppingBubble = getCurrentAirSupplyBubble(progress, maxProgress, 0);
        // the location of the rightmost empty bubble sprite
        // nothing is rendered for EMPTY_AIR_BUBBLE_DELAY_DURATION ticks after a bubble pops
        int emptyBubble = getCurrentAirSupplyBubble(progress, maxProgress, EMPTY_AIR_BUBBLE_DELAY_DURATION);

        if (!isLosingCharge) {
            // reset last bubble popped when recharging
            lastBubblePopSoundPlayed = 0;
        }

        for (int currentBubble = 1; currentBubble <= NUM_AIR_BUBBLES; ++currentBubble) {
            Identifier bubbleSprite = null;
            if (currentBubble <= fullBubble) {
                bubbleSprite = AIR_SPRITE;
            } else if (currentBubble == poppingBubble && isLosingCharge) {
                bubbleSprite = AIR_POPPING_SPRITE;
                playAirBubblePoppedSound(currentBubble, player, NUM_AIR_BUBBLES - emptyBubble);
            } else if (!isLosingCharge || currentBubble > emptyBubble) {
                bubbleSprite = AIR_EMPTY_SPRITE;
            }

            if (bubbleSprite != null) {
                int x = hotbarEdge - (currentBubble - 1) * AIR_BUBBLE_SEPARATION - AIR_BUBBLE_SIZE;
                // randomly offset the vertical height by 1 pixel every other tick
                // frequency increases as air depletes, starting at 6 bubbles left and maxing out at 2 bubbles left
                int y = height;
                if (isLosingCharge
                        && hud.getTickCount() % 2 == currentBubble % 2
                        && fullBubble <= 6
                        && hud.getRandom().nextInt(Math.max(1, (int) Math.pow(2, fullBubble - 2))) == 0
                        && hud.getRandom().nextBoolean()
                ) {
                    y += 1;
                }
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, bubbleSprite, x, y, AIR_BUBBLE_SIZE, AIR_BUBBLE_SIZE);
            }
        }

        return true;
    }

    private static int getCurrentAirSupplyBubble(int currentAirSupply, float maxAirSupply, int offset) {
        return Mth.ceil(((currentAirSupply + offset) * NUM_AIR_BUBBLES) / maxAirSupply);
    }

    private void playAirBubblePoppedSound(int currentBubble, Player player, int emptyBubbles) {
        if (lastBubblePopSoundPlayed != currentBubble) {
            float volume = AIR_BUBBLE_POP_SOUND_VOLUME_BASE
                    + AIR_BUBBLE_POP_SOUND_VOLUME_INCREMENT
                    * Math.max(0, emptyBubbles - NUM_AIR_BUBBLE_POPPED_BEFORE_SOUND_VOLUME_INCREASE + 1);
            float pitch = AIR_BUBBLE_POP_SOUND_PITCH_BASE
                    + AIR_BUBBLE_POP_SOUND_PITCH_INCREMENT
                    * Math.max(0, emptyBubbles - NUM_AIR_BUBBLE_POPPED_BEFORE_SOUND_PITCH_INCREASE + 1);

            player.playSound(SoundEvents.BUBBLE_POP, volume, pitch);
            lastBubblePopSoundPlayed = currentBubble;
        }
    }
}
