package artifacts.fabric.registry;

import artifacts.Artifacts;
import artifacts.registry.Register;
import artifacts.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;

public class FabricRegister<R> extends Register<R> {

    public FabricRegister(ResourceKey<Registry<R>> registry) {
        super(registry);
    }

    @Override
    protected <T extends R> void bind(RegistryHolder<R, T> holder) {
        holder.bind(Registry.registerForHolder(getRegistry(getRegistry()), Artifacts.key(getRegistry(), holder.getKey().identifier().getPath()), holder.getFactory().get()));
    }

    @SuppressWarnings("unchecked")
    private static <R> Registry<R> getRegistry(ResourceKey<Registry<R>> key) {
        return (Registry<R>) BuiltInRegistries.REGISTRY.getValue(key.identifier());
    }
}
