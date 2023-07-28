package artifacts.forge.event;

import artifacts.item.wearable.WearableArtifactItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ArtifactEventHandler {

    public static <T extends Event, S extends LivingEntity> void addListener(WearableArtifactItem item, EventPriority priority, Class<T> eventClass, BiConsumer<T, S> listener, Function<T, S> wearerSupplier) {
        MinecraftForge.EVENT_BUS.addListener(priority, true, eventClass, event -> {
            S wearer = wearerSupplier.apply(event);
            if (item.isEquippedBy(wearer)) {
                listener.accept(event, wearer);
            }
        });
    }

    public static <T extends Event, S extends LivingEntity> void addListener(WearableArtifactItem item, Class<T> eventClass, BiConsumer<T, S> listener, Function<T, S> wearerSupplier) {
        addListener(item, EventPriority.NORMAL, eventClass, listener, wearerSupplier);
    }

    public static <T extends LivingEvent> void addListener(WearableArtifactItem item, EventPriority priority, Class<T> eventClass, BiConsumer<T, LivingEntity> listener) {
        addListener(item, priority, eventClass, listener, LivingEvent::getEntity);
    }

    public static <T extends LivingEvent> void addListener(WearableArtifactItem item, Class<T> eventClass, BiConsumer<T, LivingEntity> listener) {
        addListener(item, EventPriority.NORMAL, eventClass, listener);
    }
}
