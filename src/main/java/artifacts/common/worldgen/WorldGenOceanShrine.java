package artifacts.common.worldgen;

import artifacts.Artifacts;
import artifacts.common.ModConfig;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class WorldGenOceanShrine implements IWorldGenerator {

    private static final ResourceLocation STRUCTURE = new ResourceLocation(Artifacts.MODID, "ocean_shrine");

    private final List<Integer> whitelistedDimensions;

    public WorldGenOceanShrine(List<Integer> whitelistedDimensions) {
        this.whitelistedDimensions = whitelistedDimensions;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.getWorldType() == WorldType.FLAT || !whitelistedDimensions.contains(world.provider.getDimension())) {
            return;
        }

        for (double chestChance = ModConfig.general.underwaterShrineChance; chestChance > 0; chestChance--) {
            if (random.nextDouble() <= chestChance) {
                BlockPos pos = getUnderwaterBlock(world, new BlockPos(chunkX * 16 + 8 + random.nextInt(16), 255, chunkZ * 16 + 8 + random.nextInt(16)));

                if (pos == null) {
                    return;
                }

                PlacementSettings settings = new PlacementSettings().setRotation(Rotation.values()[random.nextInt(4)]);
                Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), STRUCTURE);

                template.addBlocksToWorld(world, pos.add(-3, -1, -3), settings);
            }
        }
    }

    @Nullable
    private BlockPos getUnderwaterBlock(World world, BlockPos pos) {
        int depth = 0;
        while (pos.getY() >= 1) {
            if (world.getBlockState(pos).getMaterial().isLiquid()) {
                depth++;

                if (world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP)) {
                    if (depth >= 20) {
                        return pos;
                    }
                }
            } else if (depth > 0 || !world.isAirBlock(pos)) {
                return null;
            }

            pos = pos.down();
        }
        return null;
    }
}
