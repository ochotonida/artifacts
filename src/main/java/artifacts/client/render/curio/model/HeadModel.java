package artifacts.client.render.curio.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

public class HeadModel {

    private static HumanoidModel<LivingEntity> head() {
        return head(RenderType::entityCutoutNoCull, 32, 32);
    }

    private static HumanoidModel<LivingEntity> head(Function<ResourceLocation, RenderType> renderType, int textureWidth, int textureHeight) {
        HumanoidModel<LivingEntity> model = new HumanoidModel<>(renderType, 0, 0, textureWidth, textureHeight);
        model.setAllVisible(false);

        model.head = new ModelPart(model);

        // hat
        model.head.texOffs(0, 0);
        model.head.addBox(-4, -8, -4, 8, 8, 8, 0.5F);

        return model;
    }

    public static HumanoidModel<LivingEntity> drinkingHat() {
        HumanoidModel<LivingEntity> model = head(RenderType::entityTranslucent, 64, 32);

        ModelPart straws = new ModelPart(model);
        straws.xRot = 45 * (float) Math.PI / 180;
        model.head.addChild(straws);

        // hat shade
        model.head.texOffs(32, 11);
        model.head.addBox(-4, -6, -8, 8, 1, 4);

        // cans
        model.head.texOffs(32, 0);
        model.head.addBox(4, -11, -1, 3, 6, 3);
        model.head.texOffs(44, 0);
        model.head.addBox(-7, -11, -1, 3, 6, 3);

        // straws
        straws.texOffs(0, 16);
        straws.addBox(5, -4, -3, 1, 1, 8);
        straws.texOffs(18, 16);
        straws.addBox(-6, -4, -3, 1, 1, 8);

        // straw middle
        model.head.texOffs(32, 9);
        model.head.addBox(-6, -1, -5, 12, 1, 1);

        return model;
    }

    public static HumanoidModel<LivingEntity> nightVisionGoggles() {
        HumanoidModel<LivingEntity> model = head();

        // plate
        model.head.texOffs(0, 21);
        model.head.addBox(-4, -6, -5 + 0.05F, 8, 4, 1);

        // eyeholes
        model.head.texOffs(0, 16);
        model.head.addBox(1.5F, -5, -8 + 0.05F, 2, 2, 3);
        model.head.texOffs(10, 16);
        model.head.addBox(-3.5F, -5, -8 + 0.05F, 2, 2, 3);

        return model;
    }

    public static HumanoidModel<LivingEntity> snorkel() {
        HumanoidModel<LivingEntity> model = head(RenderType::entityTranslucent, 64, 32);

        ModelPart tube = new ModelPart(model);
        tube.xRot = 45 * (float) Math.PI / 180;
        model.head.addChild(tube);

        // horizontal tube
        model.head.texOffs(32, 0);
        model.head.addBox(-2, -1.5F, -6, 8, 2, 2);

        // diagonal tube
        tube.texOffs(0, 16);
        tube.addBox(4.01F, -5, -3, 2, 2, 12);

        return model;
    }

    public static HumanoidModel<LivingEntity> superstitiousHat() {
        HumanoidModel<LivingEntity> model = new HumanoidModel<>(RenderType::entityCutoutNoCull, 0, 0, 64, 32);
        model.setAllVisible(false);

        model.head = new ModelPart(model);

        // hat
        model.head.texOffs(0, 0);
        model.head.addBox(-4, -16, -4, 8, 8, 8);

        // brim
        model.head.texOffs(0, 16);
        model.head.addBox(-5, -9, -5, 10, 1, 10);

        return model;
    }

    public static HumanoidModel<LivingEntity> villagerHat() {
        HumanoidModel<LivingEntity> model = head();

        // brim
        model.head.texOffs(0, 16);
        model.head.addBox(-8, -5.125F, -8, 16, 0, 16);

        return model;
    }

    public static HumanoidModel<LivingEntity> whoopeeCushion() {
        HumanoidModel<LivingEntity> model = new HumanoidModel<>(RenderType::entityCutoutNoCull, 0, 0, 32, 16);
        model.setAllVisible(false);

        model.head = new ModelPart(model);

        // cushion
        model.head.texOffs(0, 0);
        model.head.addBox(-3, -10, -3, 6, 2, 6);

        // flap
        model.head.texOffs(0, 8);
        model.head.addBox(-2, -9.25F, 3, 4, 0, 4);

        return model;
    }
}
