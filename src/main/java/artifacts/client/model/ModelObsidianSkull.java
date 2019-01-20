package artifacts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ModelObsidianSkull extends ModelBase {

    public ModelRenderer belt;
    // skull
    public ModelRenderer skull1;
    // upper face
    public ModelRenderer skull2;
    // middle face
    public ModelRenderer skull3;
    public ModelRenderer skull4;
    public ModelRenderer skull5;
    // lower face
    public ModelRenderer skull6;
    // teeth
    public ModelRenderer skull7;
    public ModelRenderer skull8;
    public ModelRenderer skull9;

    public ModelObsidianSkull() {
        textureWidth = 32;
        textureHeight = 32;

        belt = new ModelRenderer(this, 0, 0);
        skull1 = new ModelRenderer(this, 0, 16);
        skull2 = new ModelRenderer(this, 0, 28);
        skull3 = new ModelRenderer(this, 24, 0);
        skull4 = new ModelRenderer(this, 24, 3);
        skull5 = new ModelRenderer(this, 24, 6);
        skull6 = new ModelRenderer(this, 16, 28);
        skull7 = new ModelRenderer(this, 24, 9);
        skull8 = new ModelRenderer(this, 24, 13);
        skull9 = new ModelRenderer(this, 24, 17);

        belt.addBox(-4, 0, -2, 8, 12, 4);
        skull1.addBox(0, 0, 1, 7, 6, 6);
        skull2.addBox(0, 0, 0, 7, 2, 1);
        skull3.addBox(0, 2, 0, 1, 2, 1);
        skull4.addBox(3, 2, 0, 1, 2, 1);
        skull5.addBox(6, 2, 0, 1, 2, 1);
        skull6.addBox(0, 4, 0, 7, 2, 1);
        skull7.addBox(1, 6, 0, 1, 1, 3);
        skull8.addBox(3, 6, 0, 1, 1, 3);
        skull9.addBox(5, 6, 0, 1, 1, 3);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale) {
        boolean hasPants = !((EntityPlayer) entity).getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty();

        GlStateManager.scale(7/6F, 7/6F, 7/6F);

        GlStateManager.scale(1, 1, hasPants ? 1.2F : 1.1F);
        belt.render(scale);
        GlStateManager.scale(1, 1, hasPants ? 1/1.2F : 1/1.1F);

        GlStateManager.scale(1/2F, 1/2F, 1/2F);
        GlStateManager.translate(0, 1, -1/2F);
        GlStateManager.translate(1/5F, 0, 0);
        GlStateManager.rotate(-25, 0, 1, 0);

        skull1.render(scale);
        skull2.render(scale);
        skull3.render(scale);
        skull4.render(scale);
        skull5.render(scale);
        skull6.render(scale);
        skull7.render(scale);
        skull8.render(scale);
        skull9.render(scale);
    }
}