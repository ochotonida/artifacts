package artifacts.client.render.curio.model.head;

public class NightVisionGogglesModel extends HatModel {

    public NightVisionGogglesModel() {
        super(32, 32);

        // plate
        head.texOffs(0, 21);
        head.addBox(-4, -6, -5 + 0.05F, 8, 4, 1);

        // eyeholes
        head.texOffs(0, 16);
        head.addBox(1.5F, -5, -8 + 0.05F, 2, 2, 3);
        head.texOffs(10, 16);
        head.addBox(-3.5F, -5, -8 + 0.05F, 2, 2, 3);
    }
}
