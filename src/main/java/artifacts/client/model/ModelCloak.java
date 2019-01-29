package artifacts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * This class was created by <wiiv>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * <p>
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * <p>
 * Created using Tabula 4.1.1
 */
public class ModelCloak extends ModelBase {

    public ModelRenderer collar;
    public ModelRenderer sideL;
    public ModelRenderer sideR;

    public ModelCloak() {
        textureWidth = 64;
        textureHeight = 64;
        float scaleFactor = 0.01F;

        collar = new ModelRenderer(this, 0, 0);
        collar.setRotationPoint(0F, -3F, -4.5F);
        collar.addBox(-5.5F, 0F, -1.5F, 11, 5, 11, scaleFactor);
        setRotateAngle(collar, 0.08726646259971647F, 0F, 0F);
        sideL = new ModelRenderer(this, 0, 16);
        sideL.mirror = true;
        sideL.setRotationPoint(0F, 0F, 0F);
        sideL.addBox(-0.5F, -0.5F, -5.5F, 11, 21, 10, scaleFactor);
        setRotateAngle(sideL, 0.08726646259971647F, -0.08726646259971647F, -0.17453292519943295F);
        sideR = new ModelRenderer(this, 0, 16);
        sideR.setRotationPoint(0F, 0F, 0F);
        sideR.addBox(-10.5F, -0.5F, -5.5F, 11, 21, 10, scaleFactor);
        setRotateAngle(sideR, 0.08726646259971647F, 0.08726646259971647F, 0.17453292519943295F);
    }

    public void render(float scale) {
        collar.render(scale);
        sideL.render(scale);
        sideR.render(scale);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
