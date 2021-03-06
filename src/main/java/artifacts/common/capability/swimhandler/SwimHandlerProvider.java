package artifacts.common.capability.swimhandler;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class SwimHandlerProvider implements ICapabilitySerializable<INBT> {

    private final ISwimHandler handler = new SwimHandler();
    private final LazyOptional<ISwimHandler> optionalHandler = LazyOptional.of(() -> handler);

    public void invalidate() {
        optionalHandler.invalidate();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction side) {
        return SwimHandlerCapability.INSTANCE.orEmpty(capability, optionalHandler);
    }

    @Override
    public INBT serializeNBT() {
        if (SwimHandlerCapability.INSTANCE == null) {
            return new ListNBT();
        }
        return SwimHandlerCapability.INSTANCE.writeNBT(handler, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        if (SwimHandlerCapability.INSTANCE != null) {
            SwimHandlerCapability.INSTANCE.readNBT(handler, null, nbt);
        }
    }
}
