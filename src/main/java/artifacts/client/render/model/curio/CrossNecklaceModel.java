package artifacts.client.render.model.curio;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class CrossNecklaceModel extends BipedModel<LivingEntity> {

    public CrossNecklaceModel() {
        super(0, 0, 64, 64);

        setAllVisible(false);

        body = new ModelRenderer(this, 0, 0);
        ModelRenderer cross1 = new ModelRenderer(this, 52, 0);
        ModelRenderer cross2 = new ModelRenderer(this, 56, 0);
        ModelRenderer cross3 = new ModelRenderer(this, 60, 0);

        body.addBox(-(2 * 8 + 1) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8 + 1, 2 * 12 + 1, 2 * 4 + 1);
        cross1.addBox(-0.5F, 4.5F, -5, 1, 4, 1);
        cross2.addBox(-1.5F, 5.5F, -5, 1, 1, 1);
        cross3.addBox(0.5F, 5.5F, -5, 1, 1, 1);

        body.addChild(cross1);
        body.addChild(cross2);
        body.addChild(cross3);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrixStack.scale(0.5F, 0.5F, 0.5F);
        body.render(matrixStack, buffer, light, overlay, red, green, blue, alpha);
    }
}
