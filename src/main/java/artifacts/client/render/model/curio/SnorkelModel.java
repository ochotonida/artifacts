package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class SnorkelModel extends BipedModel<LivingEntity> {

    public SnorkelModel() {
        super(RenderType::entityTranslucent, 0.5F, 0, 64, 64);

        setAllVisible(false);
        head.visible = true;
        hat.visible = true;

        ModelRenderer snorkelMouthPiece = new ModelRenderer(this, 0, 46);
        ModelRenderer snorkelTube = new ModelRenderer(this, 0, 32);

        snorkelMouthPiece.addBox(-2, -1.5F, -6, 8, 2, 2);
        snorkelTube.addBox(4.01F, -5, -3, 2, 2, 12);

        head.addChild(snorkelMouthPiece);
        head.addChild(snorkelTube);

        snorkelTube.xRot = 0.7853F;
    }
}
