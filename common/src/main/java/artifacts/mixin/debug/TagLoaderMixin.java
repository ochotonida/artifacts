package artifacts.mixin.debug;

import artifacts.Artifacts;
import artifacts.integration.ModCompat;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(TagLoader.class)
public class TagLoaderMixin {

    @Inject(method = "load", require = 1, at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V"))
    private void detectClear(ResourceManager resourceManager, CallbackInfoReturnable<Map<ResourceLocation, List<TagLoader.EntryWithSource>>> cir, @Local(ordinal = 0) ResourceLocation resourceLocation, @Local Resource resource) {
        String namespace = resourceLocation.getNamespace();

        if (namespace.equals(ModCompat.TRINKETS.modId()) || namespace.equals(ModCompat.CURIOS.modId()) || namespace.equals(ModCompat.ACCESSORIES.modId()) || namespace.equals("artifacts")) {
            Artifacts.LOGGER.warn(
                    "Tag entries for {} cleared by {}, this is probably a bug. " +
                            "If you're unable to equip specific items, " +
                            "try removing the offending mod before reporting",
                    resourceLocation, resource.sourcePackId()
            );
        }
    }
}
