package artifacts.client.render.model.curio;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class PendantModel extends BipedModel<LivingEntity> {

    public PendantModel() {
        super(RenderType::getEntityTranslucent, 0, 0, 64, 64);

        setVisible(false);

        bipedBody = new ModelRenderer(this, 0, 0);
        ModelRenderer gem = new ModelRenderer(this, 50, 0);

        bipedBody.addBox(-(2 * 8) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8, 2 * 12 + 1, 2 * 4 + 1);
        gem.addBox(-1, 4.5F, -5, 2, 2, 1);

        bipedBody.addChild(gem);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrixStack.scale(0.5F, 0.5F, 0.5F);
        bipedBody.render(matrixStack, buffer, light, overlay, red, green, blue, alpha);
    }
}
