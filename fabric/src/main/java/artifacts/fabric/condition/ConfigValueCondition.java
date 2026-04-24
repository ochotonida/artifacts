package artifacts.fabric.condition;

import artifacts.Artifacts;
import artifacts.config.value.ConfigValue;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.Nullable;

public record ConfigValueCondition(ConfigValue<Boolean> value) implements ResourceCondition {

    public static final MapCodec<ConfigValueCondition> CODEC = Artifacts.CONFIG.general.slots.codec()
            .xmap(ConfigValueCondition::new, ConfigValueCondition::value).fieldOf("value");

    public static final ResourceConditionType<ConfigValueCondition> TYPE = ResourceConditionType.create(Artifacts.id("config_value"), CODEC);

    @Override
    public ResourceConditionType<?> getType() {
        return TYPE;
    }

    @Override
    public boolean test(@Nullable HolderLookup.Provider provider) {
        return value.get();
    }
}
