package artifacts.client.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;

public abstract class RendererUtil {

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }
}
