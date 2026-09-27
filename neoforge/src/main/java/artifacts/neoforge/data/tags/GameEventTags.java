package artifacts.neoforge.data.tags;

import artifacts.Artifacts;
import artifacts.registry.ModGameEvents;
import artifacts.registry.RegistryHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.GameEventTagsProvider;

import java.util.concurrent.CompletableFuture;

public class GameEventTags extends GameEventTagsProvider {

    public GameEventTags(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(arg, completableFuture, Artifacts.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        this.tag(net.minecraft.tags.GameEventTags.VIBRATIONS).addAll(
                ModGameEvents.GAME_EVENTS.getEntries().stream().map(RegistryHolder::getKey).toList()
        );
        this.tag(net.minecraft.tags.GameEventTags.WARDEN_CAN_LISTEN).addAll(
                ModGameEvents.GAME_EVENTS.getEntries().stream().map(RegistryHolder::getKey).toList()
        );
    }
}
