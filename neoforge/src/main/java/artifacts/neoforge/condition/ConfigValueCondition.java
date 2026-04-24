package artifacts.neoforge.condition;

import artifacts.Artifacts;
import artifacts.config.value.ConfigValue;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;

public record ConfigValueCondition(ConfigValue<Boolean> value) implements ICondition {

    public static final MapCodec<ConfigValueCondition> CODEC = Artifacts.CONFIG.general.slots.codec()
            .xmap(ConfigValueCondition::new, ConfigValueCondition::value).fieldOf("value");

    public MapCodec<ConfigValueCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(IContext context) {
        return value.get();
    }
}
