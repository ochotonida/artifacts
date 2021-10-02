package artifacts.common.capability.killtracker;

import artifacts.common.config.ModConfig;
import com.google.common.collect.EvictingQueue;
import net.minecraft.world.entity.EntityType;

import java.util.Collection;

public class EntityKillTracker implements IEntityKillTracker {

    private final EvictingQueue<EntityType<?>> entityTypes = EvictingQueue.create(ModConfig.server.goldenHook.trackedEntities.get());

    @Override
    public int getMaxSize() {
        return entityTypes.size() + entityTypes.remainingCapacity();
    }

    @Override
    public void clear() {
        entityTypes.clear();
    }

    @Override
    public void addEntityType(EntityType<?> type) {
        entityTypes.add(type);
    }

    @Override
    public Collection<EntityType<?>> getEntityTypes() {
        return entityTypes;
    }
}
