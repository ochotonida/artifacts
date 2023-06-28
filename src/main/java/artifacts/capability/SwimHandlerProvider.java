package artifacts.capability;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class SwimHandlerProvider implements ICapabilityProvider {

    private final SwimHandler handler = new SwimHandler();
    private final LazyOptional<SwimHandler> optionalHandler = LazyOptional.of(() -> handler);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction side) {
        return SwimHandler.CAPABILITY.orEmpty(capability, optionalHandler);
    }
}
