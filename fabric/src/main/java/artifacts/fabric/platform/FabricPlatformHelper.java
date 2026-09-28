package artifacts.fabric.platform;

import artifacts.component.SwimData;
import artifacts.fabric.extensions.PlayerExtensions;
import artifacts.fabric.registry.FabricRegister;
import artifacts.fabric.registry.ModAttributesFabric;
import artifacts.platform.PlatformHelper;
import artifacts.registry.Register;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class FabricPlatformHelper implements PlatformHelper {

    @Nullable
    @Override
    public SwimData getSwimData(LivingEntity entity) {
        if (entity instanceof PlayerExtensions playerExtensions) {
            return playerExtensions.artifacts$getSwimData();
        }
        return null;
    }

    @Override
    public Holder<Attribute> getSwimSpeedAttribute() {
        return ModAttributesFabric.SWIM_SPEED;
    }

    @Override
    public boolean isFishingRod(ItemStack stack) {
        return stack.getItem() instanceof FishingRodItem;
    }

    @Override
    public boolean isDedicatedServer() {
        return FabricLoader.getInstance().getEnvironmentType().equals(EnvType.SERVER);
    }

    @Override
    public Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public <R> Register<R> createRegister(ResourceKey<Registry<R>> registry) {
        return new FabricRegister<>(registry);
    }
}
