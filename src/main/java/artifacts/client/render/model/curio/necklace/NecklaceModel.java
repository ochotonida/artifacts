package artifacts.client.render.model.curio.necklace;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public abstract class NecklaceModel extends BipedModel<LivingEntity> {

    public NecklaceModel() {
        super(RenderType::entityTranslucent, 0, 0, 64, 48);
        setAllVisible(false);

        body = new ModelRenderer(this);

        // chain
        body.texOffs(0, 0);
        body.addBox(-(2 * 8) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8, 2 * 12 + 1, 2 * 4 + 1);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrixStack.scale(0.5F, 0.5F, 0.5F);
        body.render(matrixStack, buffer, light, overlay, red, green, blue, alpha);
    }
}
