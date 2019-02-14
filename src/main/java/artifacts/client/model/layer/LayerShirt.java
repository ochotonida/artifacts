package artifacts.client.model.layer;

import artifacts.Artifacts;
import artifacts.common.ModItems;
import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class LayerShirt extends LayerBauble {

    public final ResourceLocation TEXTURES;

    public LayerShirt(RenderPlayer renderPlayer, boolean smallArms) {
        super(renderPlayer, new ModelPlayer(0.2F, smallArms));
        TEXTURES = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/tiny_shirt_" + (smallArms ? "slim" : "normal") + ".png");
        model.setVisible(false);
        model.bipedLeftArm.showModel = true;
        model.bipedRightArm.showModel = true;
        model.bipedBody.showModel = true;
    }

    @Override
    protected void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (BaublesApi.isBaubleEquipped(player, ModItems.TINY_SHIRT) != -1) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURES);
            model.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }
}
