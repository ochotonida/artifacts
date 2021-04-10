package artifacts.common.capability.killtracker;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class EntityKillTrackerProvider implements ICapabilitySerializable<INBT> {

    private final IEntityKillTracker tracker = new EntityKillTracker();
    private final LazyOptional<IEntityKillTracker> optionalTracker = LazyOptional.of(() -> tracker);

    public void invalidate() {
        optionalTracker.invalidate();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction side) {
        return EntityKillTrackerCapability.INSTANCE.orEmpty(capability, optionalTracker);
    }

    @Override
    public INBT serializeNBT() {
        if (EntityKillTrackerCapability.INSTANCE == null) {
            return new ListNBT();
        }
        return EntityKillTrackerCapability.INSTANCE.writeNBT(tracker, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        if (EntityKillTrackerCapability.INSTANCE != null) {
            EntityKillTrackerCapability.INSTANCE.readNBT(tracker, null, nbt);
        }
    }
}
