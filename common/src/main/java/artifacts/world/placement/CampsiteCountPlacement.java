package artifacts.world.placement;

import artifacts.Artifacts;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.RepeatingPlacement;

public record CampsiteCountPlacement() implements RepeatingPlacement {

    private static final CampsiteCountPlacement INSTANCE = new CampsiteCountPlacement();
    public static final MapCodec<CampsiteCountPlacement> CODEC = MapCodec.unit(() -> INSTANCE);

    public static CampsiteCountPlacement campsiteCount() {
        return INSTANCE;
    }

    @Override
    public MapCodec<? extends RepeatingPlacement> codec() {
        return CODEC;
    }

    @Override
    public int count(RandomSource p_226333_, BlockPos p_226334_) {
        return Artifacts.CONFIG.general.campsite.count.get();
    }
}
