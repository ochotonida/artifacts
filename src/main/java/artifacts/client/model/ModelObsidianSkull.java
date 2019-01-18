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
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.belt = new ModelRenderer(this, 0, 0);
        this.skull1 = new ModelRenderer(this, 0, 16);
        this.skull2 = new ModelRenderer(this, 0, 28);
        this.skull3 = new ModelRenderer(this, 24, 0);
        this.skull4 = new ModelRenderer(this, 24, 3);
        this.skull5 = new ModelRenderer(this, 24, 6);
        this.skull6 = new ModelRenderer(this, 16, 28);
        this.skull7 = new ModelRenderer(this, 24, 9);
        this.skull8 = new ModelRenderer(this, 24, 13);
        this.skull9 = new ModelRenderer(this, 24, 17);

        this.belt.addBox(-4, 0, -2, 8, 12, 4, 0);
        this.skull1.addBox(0, 0, 1, 7, 6, 6, 0);
        this.skull2.addBox(0, 0, 0, 7, 2, 1, 0);
        this.skull3.addBox(0, 2, 0, 1, 2, 1, 0);
        this.skull4.addBox(3, 2, 0, 1, 2, 1, 0);
        this.skull5.addBox(6, 2, 0, 1, 2, 1, 0);
        this.skull6.addBox(0, 4, 0, 7, 2, 1, 0);
        this.skull7.addBox(1, 6, 0, 1, 1, 3, 0);
        this.skull8.addBox(3, 6, 0, 1, 1, 3, 0);
        this.skull9.addBox(5, 6, 0, 1, 1, 3, 0);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale) {
        boolean hasPants = !((EntityPlayer) entity).getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty();

        GlStateManager.scale(7/6F, 7/6F, 7/6F);

        GlStateManager.scale(hasPants ? 1 : 0.9F, 1, hasPants ? 1.2F : 1.1F);
        this.belt.render(scale);
        GlStateManager.scale(hasPants ? 1 : 1/0.9F, 1, hasPants ? 1/1.2F : 1/1.1F);

        GlStateManager.scale(1/2F, 1/2F, 1/2F);
        GlStateManager.translate(0, 1, -1/2F);

        this.skull1.render(scale);
        this.skull2.render(scale);
        this.skull3.render(scale);
        this.skull4.render(scale);
        this.skull5.render(scale);
        this.skull6.render(scale);
        this.skull7.render(scale);
        this.skull8.render(scale);
        this.skull9.render(scale);
    }
}