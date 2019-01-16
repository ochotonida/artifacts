package artifacts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelAmulet extends ModelBase {

    public ModelRenderer boxChain;
    public ModelRenderer boxGem;

    public ModelAmulet() {
        this.textureWidth = 32;
        this.textureHeight = 16;
        this.boxChain = new ModelRenderer(this, 0, 0);
        this.boxGem = new ModelRenderer(this, 24, 0);
        this.boxChain.addBox(-4, 0, -2, 8, 12, 4, 0);
        this.boxGem.addBox(-0.5F, 3, -4.5F, 1, 1, 1, 0);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale) {
        GlStateManager.translate(0, -0.02, 0);
        GlStateManager.scale(7/6F, 7/6F, 7/6F);
        this.boxChain.render(scale);
        GlStateManager.scale(1, 1, 0.5F);
        this.boxGem.render(scale);
    }
}