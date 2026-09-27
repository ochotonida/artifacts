package artifacts.neoforge.data;

import artifacts.registry.ModGameEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.VibrationFrequency;

import java.util.concurrent.CompletableFuture;

public class DataMaps extends DataMapProvider {

    public DataMaps(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        Builder<VibrationFrequency, GameEvent> builder = builder(NeoForgeDataMaps.VIBRATION_FREQUENCIES);
        ModGameEvents.VIBRATION_FREQUENCIES.forEach((holder, frequency) ->
                builder.add(holder.getKey(), new VibrationFrequency(frequency), false)
        );
    }
}
