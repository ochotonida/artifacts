package artifacts.fabric.component;

import artifacts.component.SwimData;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.CompoundTag;

public class SwimDataComponent extends SwimData implements ComponentV3 {

    @Override
    public void readFromNbt(CompoundTag tag) {
        shouldSwim = tag.getBoolean("ShouldSwim");
        hasTouchedWater = tag.getBoolean("HasTouchedWater");
        swimTime = tag.getInt("SwimTime");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putBoolean("ShouldSwim", shouldSwim);
        tag.putBoolean("HasTouchedWater", hasTouchedWater);
        tag.putInt("SwimTime", swimTime);
    }
}
