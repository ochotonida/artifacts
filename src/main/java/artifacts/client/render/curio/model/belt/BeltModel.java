package artifacts.client.render.curio.model.belt;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public abstract class BeltModel extends BipedModel<LivingEntity> {

    protected final ModelRenderer charm = new ModelRenderer(this);

    private final float xOffset;
    private final float zOffset;
    private final float rotation;

    public BeltModel(float xOffset, float zOffset, float rotation) {
        this(RenderType::entityCutoutNoCull, xOffset, zOffset, rotation);
    }

    public BeltModel(Function<ResourceLocation, RenderType> renderType, float xOffset, float zOffset, float rotation) {
        super(renderType, 0.5F, 0, 32, 32);
        this.xOffset = xOffset;
        this.zOffset = zOffset;
        this.rotation = rotation;

        setAllVisible(false);

        body = new ModelRenderer(this);

        // belt
        body.texOffs(0, 0);
        body.addBox(-4, 0, -2, 8, 12, 4, 0.5F);

        // charm
        body.addChild(charm);
    }

    public void setCharmPosition(int slot) {
        float xOffset = slot % 2 == 0 ? this.xOffset : -this.xOffset;
        float zOffset = slot % 4 < 2 ? this.zOffset : -this.zOffset;
        charm.setPos(xOffset, 9, zOffset);

        float rotation = slot % 4 < 2 ? 0 : (float) -Math.PI;
        rotation += slot % 2 == 0 ^ slot % 4 >= 2 ? this.rotation : -this.rotation;
        charm.yRot = rotation;
    }
}
