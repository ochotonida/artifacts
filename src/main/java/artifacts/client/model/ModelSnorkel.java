package artifacts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.lwjgl.opengl.GL11;

public class ModelSnorkel extends ModelBase {
    public ModelRenderer snorkelbox1;
    public ModelRenderer snorkelbox2;
    public ModelRenderer head;
    public ModelRenderer headOverlay;

    public ModelSnorkel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.snorkelbox1 = new ModelRenderer(this, 0, 14);
        this.snorkelbox1.addBox(-2, 2, -6, 8, 2, 2, 0);
        this.head = new ModelRenderer(this, 28, 0);
        this.headOverlay = new ModelRenderer(this, 28, 16);
        this.head.addBox(-4, -3.5F, -4, 8, 8, 8, 0);
        this.headOverlay.addBox(-4, -3.5F, -4, 8, 8, 8, 0);
        this.snorkelbox2 = new ModelRenderer(this, 0, 0);
        this.snorkelbox2.addBox(4.0F, -2.0F, -5, 2, 2, 12, 0);
        snorkelbox2.rotateAngleX = 0.7853981633974483F;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale) {
        this.snorkelbox1.render(scale);
        this.snorkelbox2.render(scale);
        if (((EntityPlayer) entity).getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty()) {
            GlStateManager.pushMatrix();

            GlStateManager.scale(1.2, 1.2, 1.2);
            this.head.render(scale);

            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(1, 1, 1, 0.3F);
            this.headOverlay.render(scale);
            GlStateManager.disableBlend();

            GlStateManager.popMatrix();
        }
    }
}