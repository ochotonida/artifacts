package artifacts.client.render.curio.model.head;

public class VillagerHatModel extends HatModel {

    public VillagerHatModel() {
        super(32, 32);

        // brim
        head.texOffs(0, 16);
        head.addBox(-8, -5.125F, -8, 16, 0, 16);
    }
}
