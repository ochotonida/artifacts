package artifacts.registry;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class RegistryHolder<R, V extends R> implements Supplier<V> {

    private final ResourceKey<R> key;
    private final Supplier<V> factory;
    private Holder<R> holder;

    public RegistryHolder(ResourceKey<R> key, Supplier<V> factory) {
        this.key = key;
        this.factory = factory;
    }

    public Supplier<V> getFactory() {
        return factory;
    }

    public void bind(Holder<R> holder) {
        if (this.holder != null) {
            throw new IllegalStateException();
        }
        this.holder = holder;
    }

    public Holder<R> holder() {
        return holder;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get() {
        return (V) value();
    }

    public ResourceKey<R> getKey() {
        return key;
    }

    public R value() {
        return holder.value();
    }

    public boolean isBound() {
        return holder != null && holder.isBound();
    }

    public boolean is(ResourceKey<R> resourceKey) {
        return resourceKey.equals(key);
    }
}
