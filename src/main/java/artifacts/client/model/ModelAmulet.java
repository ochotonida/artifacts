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
        this.boxGem.addBox(-1, 3, -2.5F, 2, 2, 1, 0);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale) {
        GlStateManager.translate(0, -0.02, 0);
        GlStateManager.scale(4/3F, 4/3F, 4/3F);
        this.boxChain.render(scale);
        GlStateManager.disableLighting();
        this.boxGem.render(scale);
        GlStateManager.enableLighting();
    }
}