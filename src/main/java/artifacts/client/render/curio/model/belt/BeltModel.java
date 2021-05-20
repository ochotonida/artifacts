package artifacts.client.render.curio.model.belt;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public abstract class BeltModel extends BipedModel<LivingEntity> {

    public BeltModel() {
        this(RenderType::entityCutoutNoCull);
    }

    public BeltModel(Function<ResourceLocation, RenderType> renderType) {
        super(renderType, 0.5F, 0, 32, 32);
        setAllVisible(false);

        body = new ModelRenderer(this);

        // belt
        body.texOffs(0, 0);
        body.addBox(-4, 0, -2, 8, 12, 4, 0.5F);
    }
}
