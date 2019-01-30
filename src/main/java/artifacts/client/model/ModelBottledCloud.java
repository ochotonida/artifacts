package artifacts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.lwjgl.opengl.GL11;

public class ModelBottledCloud extends ModelBase {

    public ModelRenderer belt;

    public ModelRenderer jar;
    public ModelRenderer lid;
    public ModelRenderer cloud1;
    public ModelRenderer cloud2;

    public ModelBottledCloud() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        belt = new ModelRenderer(this, 0, 0);
        jar = new ModelRenderer(this, 0, 16);
        lid = new ModelRenderer(this, 44, 0);
        cloud1 = new ModelRenderer(this, 24, 0);
        cloud2 = new ModelRenderer(this, 24, 10);

        belt.addBox(-4, 0, -2, 8, 12, 4);
        jar.addBox(0, 0, 0, 7, 9, 7);
        lid.addBox(1, -1F, 1, 5, 1, 5);
        cloud1.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5);
        cloud2.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3);
    }

    @Override
    public void render(Entity entity, float partialticks, float f1, float f2, float f3, float f4, float scale) {
        boolean hasPants = !((EntityPlayer) entity).getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty();

        GlStateManager.scale(7 / 6F, 7 / 6F, 7 / 6F);

        GlStateManager.scale(1, 1, hasPants ? 1.2F : 1.1F);
        belt.render(scale);
        GlStateManager.scale(1, 1, hasPants ? 1 / 1.2F : 1 / 1.1F);

        GlStateManager.scale(1/2F, 1/2F, 1/2F);
        GlStateManager.translate(0, 1, -2/3F);
        GlStateManager.translate(1/5F, 0, 0);
        GlStateManager.rotate(-15, 0, 1, 0);

        jar.render(scale);
        lid.render(scale);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1, 1, 1, 0.5F);
        GlStateManager.translate(3.5F/16F, 4.5F/16F, 3.5F/16F);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 1/16F * Math.sin(0.05 * (entity.ticksExisted + partialticks)), 0);
        GlStateManager.scale(1 + 0.1F * Math.sin(0.03 * (entity.ticksExisted + partialticks)),
                0.8F + 0.1F * Math.sin(0.032 * (entity.ticksExisted + partialticks)),
                0.8F + 0.1F * Math.sin(0.034 * (entity.ticksExisted + partialticks)));
        cloud2.render(scale);
        GlStateManager.popMatrix();

        GlStateManager.translate(0, 1/16F * Math.sin(0.05 * (entity.ticksExisted + partialticks) + 0.5), 0);
        GlStateManager.scale(1 + 0.1F * Math.sin(0.033 * (entity.ticksExisted + partialticks)),
                0.8F + 0.1F * Math.sin(0.031 * (entity.ticksExisted + partialticks)),
                0.8F + 0.1F * Math.sin(0.034 * (entity.ticksExisted + partialticks)));
        cloud1.render(scale);

        GlStateManager.disableBlend();

    }
}