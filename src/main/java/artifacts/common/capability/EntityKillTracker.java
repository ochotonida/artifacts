package artifacts.common.capability;

import net.minecraft.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class EntityKillTracker implements IEntityKillTracker {

    private final List<EntityType<?>> entityTypes = new ArrayList<>();

    public void clear() {
        entityTypes.clear();
    }

    public void addEntityTypes(EntityType<?> type) {
        entityTypes.add(type);
        if (entityTypes.size() > MAX_SIZE) {
            entityTypes.remove(0);
        }
    }

    public List<EntityType<?>> getEntityTypes() {
        return entityTypes;
    }
}
