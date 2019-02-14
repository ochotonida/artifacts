package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.entity.EntityHallowStar;
import artifacts.common.entity.EntityMimic;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

    public static void init() {
        EntityRegistry.registerModEntity(new ResourceLocation(Artifacts.MODID, "mimic"), EntityMimic.class, Artifacts.MODID + ".mimic", 0, Artifacts.instance, 64, 3, true, 0xb27725, 0x261701);
        EntityRegistry.registerModEntity(new ResourceLocation(Artifacts.MODID, "hallow_star"), EntityHallowStar.class, Artifacts.MODID + ".hallow_star", 1, Artifacts.instance, 64, 3, true);
    }
}
