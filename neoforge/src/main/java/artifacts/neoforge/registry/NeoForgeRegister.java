package artifacts.neoforge.registry;

import artifacts.Artifacts;
import artifacts.neoforge.ArtifactsNeoForge;
import artifacts.registry.Register;
import artifacts.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRegister<R> extends Register<R> {

    private final DeferredRegister<R> register;

    public NeoForgeRegister(ResourceKey<Registry<R>> registry) {
        super(registry);
        this.register = DeferredRegister.create(registry, Artifacts.MOD_ID);
    }

    @Override
    public void register() {
        super.register();
        ArtifactsNeoForge.addDeferredRegister(register);
    }

    @Override
    protected <T extends R> void bind(RegistryHolder<R, T> holder) {
        holder.bind(register.register(holder.getKey().identifier().getPath(), holder.getFactory()));
    }
}
