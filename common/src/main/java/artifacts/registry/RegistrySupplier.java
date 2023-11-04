package artifacts.registry;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class RegistrySupplier<T> implements Supplier<T> {

    private final dev.architectury.registry.registries.RegistrySupplier<T> supplier;

    private RegistrySupplier(dev.architectury.registry.registries.RegistrySupplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> RegistrySupplier<T> of(dev.architectury.registry.registries.RegistrySupplier<T> supplier) {
        return new RegistrySupplier<>(supplier);
    }

    @Override
    public T get() {
        return supplier.get();
    }

    public ResourceLocation getId() {
        return supplier.getId();
    }

    public dev.architectury.registry.registries.RegistrySupplier<T> getRegistrySupplier() {
        return supplier;
    }
}
