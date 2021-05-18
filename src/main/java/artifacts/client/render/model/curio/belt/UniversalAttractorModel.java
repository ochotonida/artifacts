package artifacts.client.render.model.curio.belt;

public class UniversalAttractorModel extends BeltModel {

    public UniversalAttractorModel() {
        body.texOffs(0, 16);
        body.addBox(0, 9, -3, 5, 2, 1);
        body.texOffs(0, 19);
        body.addBox(0, 11, -3, 2, 4, 1);
        body.texOffs(6, 19);
        body.addBox(3, 11, -3, 2, 4, 1);
    }
}
