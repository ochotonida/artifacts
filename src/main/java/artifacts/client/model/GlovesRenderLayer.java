package artifacts.client.model;

import artifacts.Artifacts;
import artifacts.common.ModItems;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import baubles.common.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GlovesRenderLayer implements LayerRenderer<EntityPlayer> {

    private final RenderPlayer renderPlayer;

    private final ModelBiped model;

    public final ResourceLocation feralClawsTextures;
    public final ResourceLocation titanGloveTextures;
    public final ResourceLocation powerGloveTextures;

    public GlovesRenderLayer(boolean smallArms, RenderPlayer renderPlayer) {
        this.renderPlayer = renderPlayer;
        this.model = new ModelPlayer(0.5F, smallArms);
        model.setVisible(false);
        this.feralClawsTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/feral_claws_" + (smallArms ? "slim" : "normal") + ".png");
        this.titanGloveTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/titan_glove_" + (smallArms ? "slim" : "normal") + ".png");
        this.powerGloveTextures = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/power_glove_" + (smallArms ? "slim" : "normal") + ".png");
    }

    @Override
    public void doRenderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!Config.renderBaubles || player.getActivePotionEffect(MobEffects.INVISIBILITY) != null) {
            return;
        }

        model.setModelAttributes(renderPlayer.getMainModel());
        model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, player);

        IBaublesItemHandler baubleHandler = BaublesApi.getBaublesHandler(player);

        ResourceLocation textures = getTextures(baubleHandler.getStackInSlot(BaubleType.RING.getValidSlots()[0]));
        if (textures != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(textures);
            model.bipedLeftArm.showModel = true;
            model.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            model.bipedLeftArm.showModel = false;
        }
        textures = getTextures(baubleHandler.getStackInSlot(BaubleType.RING.getValidSlots()[1]));
        if (textures != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(textures);
            model.bipedRightArm.showModel = true;
            model.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            model.bipedRightArm.showModel = false;
        }
    }

    private @Nullable ResourceLocation getTextures(ItemStack stack) {
        if (stack.getItem() == ModItems.TITAN_GLOVE) {
            return titanGloveTextures;
        } else if (stack.getItem() == ModItems.POWER_GLOVE) {
            return powerGloveTextures;
        } else if (stack.getItem() == ModItems.FERAL_CLAWS) {
            return feralClawsTextures;
        }
        return null;
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
