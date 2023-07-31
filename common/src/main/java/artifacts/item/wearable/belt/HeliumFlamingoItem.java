package artifacts.item.wearable.belt;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModKeyMappings;
import artifacts.registry.ModSoundEvents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HeliumFlamingoItem extends WearableArtifactItem {

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
}
