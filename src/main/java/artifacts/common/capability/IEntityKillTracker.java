package artifacts.common.capability;

import net.minecraft.entity.EntityType;

import java.util.List;

public interface IEntityKillTracker {

    int MAX_SIZE = 25;

    void clear();

    void addEntityTypes(EntityType<?> type);

    List<EntityType<?>> getEntityTypes();

    default double getKillRatio(EntityType<?> type) {
        return getEntityTypes().stream().filter(type::equals).count() / (double) MAX_SIZE;
    }
}
