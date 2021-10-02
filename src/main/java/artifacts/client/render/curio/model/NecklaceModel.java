package artifacts.client.render.curio.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;

public class NecklaceModel extends HumanoidModel<LivingEntity> {

    protected NecklaceModel() {
        super(RenderType::entityTranslucent, 0, 0, 64, 48);
        setAllVisible(false);

        body = new ModelPart(this);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrixStack.scale(0.5F, 0.5F, 0.5F);
        body.render(matrixStack, buffer, light, overlay, red, green, blue, alpha);
    }

    private static NecklaceModel necklace() {
        NecklaceModel model = new NecklaceModel();

        // chain
        model.body.texOffs(0, 0);
        model.body.addBox(-(2 * 8) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8, 2 * 12 + 1, 2 * 4 + 1);

        return model;
    }

    private static NecklaceModel centeredNecklace() {
        NecklaceModel model = new NecklaceModel();

        // chain
        model.body.texOffs(0, 0);
        model.body.addBox(-(2 * 8 + 1) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8 + 1, 2 * 12 + 1, 2 * 4 + 1);

        return model;
    }

    public static NecklaceModel charmOfSinking() {
        NecklaceModel model = necklace();
        // gem
        model.body.texOffs(50, 0);
        model.body.addBox(-1, 3.5F, -5, 2, 4, 1);

        return model;
    }

    public static NecklaceModel crossNecklace() {
        NecklaceModel model = centeredNecklace();

        // cross vertical
        model.body.texOffs(52, 0);
        model.body.addBox(-0.5F, 4.5F, -5, 1, 4, 1);

        // cross horizontal
        model.body.texOffs(56, 0);
        model.body.addBox(-1.5F, 5.5F, -5, 3, 1, 1);

        return model;
    }

    public static NecklaceModel panicNecklace() {
        NecklaceModel model = centeredNecklace();

        // gem top
        model.body.texOffs(52, 0);
        model.body.addBox(-2.5F, 5.5F, -5, 2, 2, 1);
        model.body.texOffs(58, 0);
        model.body.addBox(0.5F, 5.5F, -5, 2, 2, 1);

        // gem middle
        model.body.texOffs(52, 3);
        model.body.addBox(-1.5F, 6.5F, -5, 3, 2, 1);

        // gem bottom
        model.body.texOffs(60, 4);
        model.body.addBox(-0.5F, 8.5F, -5, 1, 1, 1);

        return model;
    }

    public static NecklaceModel pendant() {
        NecklaceModel model = necklace();

        // gem
        model.body.texOffs(50, 0);
        model.body.addBox(-1, 4.5F, -5, 2, 2, 1);

        return model;
    }
}
