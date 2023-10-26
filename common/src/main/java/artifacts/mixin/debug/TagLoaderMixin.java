package artifacts.mixin.debug;

import artifacts.Artifacts;
import com.google.gson.JsonElement;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagFile;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Mixin(TagLoader.class)
public class TagLoaderMixin {

    @SuppressWarnings("rawtypes")
    @Inject(method = "load", require = 1, locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V"))
    private void detectClear(ResourceManager resourceManager, CallbackInfoReturnable<Map<ResourceLocation, List<TagLoader.EntryWithSource>>> cir, Map<ResourceLocation, List<TagLoader.EntryWithSource>> map, FileToIdConverter fileToIdConverter, Iterator var4, Map.Entry<ResourceLocation, List<Resource>> entry, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, Iterator var8, Resource resource, Reader reader, JsonElement jsonElement, List<TagLoader.EntryWithSource> list, TagFile tagFile) {
        if (resourceLocation.getNamespace().equals("trinkets") || resourceLocation.getNamespace().equals("curios")) {
            Artifacts.LOGGER.warn("Tag entries for {} cleared by {}", resourceLocation, resource.sourcePackId());
        }
    }
}
