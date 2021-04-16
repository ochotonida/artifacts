package artifacts.client.render.model.curio.head;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public class HatModel extends BipedModel<LivingEntity> {

    public HatModel(int textureWidth, int textureHeight) {
        this(RenderType::entityCutoutNoCull, textureWidth, textureHeight);
    }

    public HatModel(Function<ResourceLocation, RenderType> renderType, int textureWidth, int textureHeight) {
        super(renderType, 0, 0, textureWidth, textureHeight);
        setAllVisible(false);

        head = new ModelRenderer(this);

        // hat
        head.texOffs(0, 0);
        head.addBox(-4, -8, -4, 8, 8, 8, 0.5F);
    }
}
