package artifacts.platform;

import artifacts.component.SwimData;
import artifacts.registry.Register;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public interface PlatformHelper {

    /**
     * Returns null for non-player entities
     */
    @Nullable
    SwimData getSwimData(LivingEntity entity);

    Holder<Attribute> getSwimSpeedAttribute();

    boolean isFishingRod(ItemStack stack);

    boolean isDedicatedServer();

    Path getConfigDir();

    <R> Register<R> createRegister(ResourceKey<Registry<R>> registry);
}
