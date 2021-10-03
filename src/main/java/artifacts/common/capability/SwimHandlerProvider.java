package artifacts.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class SwimHandlerProvider implements ICapabilitySerializable<CompoundTag> {

    private final SwimHandler handler = new SwimHandler();
    private final LazyOptional<SwimHandler> optionalHandler = LazyOptional.of(() -> handler);

    public void invalidate() {
        optionalHandler.invalidate();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction side) {
        return SwimHandler.CAPABILITY.orEmpty(capability, optionalHandler);
    }

    @Override
    public CompoundTag serializeNBT() {
        return handler.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        handler.deserializeNBT(nbt);
    }
}
