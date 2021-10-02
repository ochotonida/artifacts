package artifacts.common.capability.killtracker;

import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class EntityKillTrackerProvider implements ICapabilitySerializable<Tag> {

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
    public Tag serializeNBT() {
        if (EntityKillTrackerCapability.INSTANCE == null) {
            return new ListTag();
        }
        return EntityKillTrackerCapability.INSTANCE.writeNBT(tracker, null);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        if (EntityKillTrackerCapability.INSTANCE != null) {
            EntityKillTrackerCapability.INSTANCE.readNBT(tracker, null, nbt);
        }
    }
}
