package artifacts.client.model;

import baubles.common.Config;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;

import javax.annotation.Nonnull;

public abstract class LayerBauble implements LayerRenderer<EntityPlayer> {

    protected final RenderPlayer renderPlayer;
    protected ModelPlayer model;

    public LayerBauble(RenderPlayer renderPlayer) {
        this(renderPlayer, new ModelPlayer(0.5F, false));
    }

    public LayerBauble(RenderPlayer renderPlayer, ModelPlayer model) {
        this.renderPlayer = renderPlayer;
        this.model = model;
        model.setVisible(false);
    }

    @Override
    public final void doRenderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!Config.renderBaubles || player.getActivePotionEffect(MobEffects.INVISIBILITY) != null) {
            return;
        }

        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();

        model.setModelAttributes(renderPlayer.getMainModel());
        model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, player);

        GlStateManager.pushMatrix();
        renderLayer(player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.popMatrix();
    }

    protected abstract void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale);

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
