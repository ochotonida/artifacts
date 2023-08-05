package artifacts.item.wearable.belt;

import artifacts.component.SwimData;
import artifacts.item.wearable.WearableArtifactItem;
import artifacts.item.wearable.necklace.CharmOfSinkingItem;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import artifacts.registry.ModKeyMappings;
import artifacts.registry.ModSoundEvents;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HeliumFlamingoItem extends WearableArtifactItem {

    public HeliumFlamingoItem() {
        TickEvent.PLAYER_PRE.register(this::onHeliumFlamingoTick);
    }

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() <= 0;
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        tooltip.add(tooltipLine("swimming"));
        tooltip.add(tooltipLine("keymapping", ModKeyMappings.getHeliumFlamingoKey().getTranslatedKeyMessage()));
    }

    @NotNull
    @Override
    public SoundEvent getEquipSound() {
        return ModSoundEvents.POP.get(); // TODO pitch 0.7
    }

    private void onHeliumFlamingoTick(Player player) {
        SwimData swimData = PlatformServices.platformHelper.getSwimData(player);
        if (swimData == null) {
            return;
        }
        int maxFlightTime = Math.max(1, ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() * 20);
        int rechargeTime = Math.max(20, ModGameRules.HELIUM_FLAMINGO_RECHARGE_DURATION.get() * 20);

        if (swimData.isSwimming()) {
            if (!ModItems.HELIUM_FLAMINGO.get().isEquippedBy(player)
                    || swimData.getSwimTime() > maxFlightTime
                    || player.isInWater() && !player.isSwimming() && !CharmOfSinkingItem.shouldSink(player)
                    || (!player.isInWater() || CharmOfSinkingItem.shouldSink(player)) && player.onGround()) {
                swimData.setSwimming(false);
                if (!player.onGround() && !player.isInWater()) {
                    player.playSound(ModSoundEvents.POP.get(), 0.5F, 0.75F);
                }
            }

            if (ModItems.HELIUM_FLAMINGO.get().isEquippedBy(player) && !PlatformServices.platformHelper.isEyeInWater(player)) {
                if (!player.getAbilities().invulnerable) {
                    swimData.setSwimTime(swimData.getSwimTime() + 1);
                }
            }
        } else if (swimData.getSwimTime() < 0) {
            swimData.setSwimTime(
                    swimData.getSwimTime() < -rechargeTime
                            ? -rechargeTime
                            : swimData.getSwimTime() + 1
            );
        }
    }
}
