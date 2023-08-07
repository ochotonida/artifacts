package artifacts.fabric.mixin.compat.origins;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.BiFunction;

@Mixin(value = ConditionFactory.class, remap = false)
public interface ConditionFactoryAccessor<T> {

    @Accessor
    BiFunction<SerializableData.Instance, T, Boolean> getCondition();

    @Accessor
    void setCondition(BiFunction<SerializableData.Instance, T, Boolean> condition);
}
