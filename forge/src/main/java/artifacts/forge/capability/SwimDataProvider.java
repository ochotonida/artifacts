package artifacts.forge.capability;

import artifacts.component.SwimData;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class SwimDataProvider implements ICapabilityProvider {

    private final SwimData instance = new SwimData();
    private final LazyOptional<SwimData> lazyOptional = LazyOptional.of(() -> instance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction side) {
        return SwimDataCapability.CAPABILITY.orEmpty(capability, lazyOptional);
    }
}
