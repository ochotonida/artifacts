package artifacts.common.capability.killtracker;

import com.google.common.collect.EvictingQueue;
import net.minecraft.entity.EntityType;

import java.util.Collection;

public class EntityKillTracker implements IEntityKillTracker {

    private static final int MAX_SIZE = 25;

    private final EvictingQueue<EntityType<?>> entityTypes = EvictingQueue.create(MAX_SIZE);

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
