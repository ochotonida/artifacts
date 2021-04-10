package artifacts.common.capability.swimhandler;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class SwimHandlerProvider implements ICapabilitySerializable<INBT> {

    private final ISwimHandler tracker = new SwimHandler();
    private final LazyOptional<ISwimHandler> optionalTracker = LazyOptional.of(() -> tracker);

    public void invalidate() {
        optionalTracker.invalidate();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction side) {
        return SwimHandlerCapability.INSTANCE.orEmpty(capability, optionalTracker);
    }

    @Override
    public INBT serializeNBT() {
        if (SwimHandlerCapability.INSTANCE == null) {
            return new ListNBT();
        }
        return SwimHandlerCapability.INSTANCE.writeNBT(tracker, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        if (SwimHandlerCapability.INSTANCE != null) {
            SwimHandlerCapability.INSTANCE.readNBT(tracker, null, nbt);
        }
    }
}
