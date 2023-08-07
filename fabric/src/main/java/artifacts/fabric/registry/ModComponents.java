package artifacts.fabric.registry;

import artifacts.Artifacts;
import artifacts.fabric.component.SwimDataComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

public class ModComponents implements EntityComponentInitializer {

    public static final ComponentKey<SwimDataComponent> SWIM_DATA = ComponentRegistryV3.INSTANCE.getOrCreate(Artifacts.id("swim_data"), SwimDataComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(SWIM_DATA, provider -> new SwimDataComponent(), RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}
