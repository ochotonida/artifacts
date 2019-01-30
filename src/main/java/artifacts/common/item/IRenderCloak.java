package artifacts.common.item;

import artifacts.client.model.ModelCloak;
import baubles.api.render.IRenderBauble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * see {@link ModelCloak}
 */
public interface IRenderCloak extends IRenderBauble {

    @SideOnly(Side.CLIENT)
    ModelCloak model = new ModelCloak();

    @SideOnly(Side.CLIENT)
    ResourceLocation getTexture();

    @Nullable
    @SideOnly(Side.CLIENT)
    ResourceLocation getTextureOverlay();

    @Override
    @SideOnly(Side.CLIENT)
    default void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
        if (type == RenderType.BODY) {
            Helper.rotateIfSneaking(player);
            boolean armor = !player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty();
            GlStateManager.translate(0, armor ? -0.07F : -0.01F, 0);

            float s = 1F / 16F;
            GlStateManager.scale(s, s, s);

            GlStateManager.enableLighting();
            GlStateManager.enableRescaleNormal();

            Minecraft.getMinecraft().renderEngine.bindTexture(getTexture());
            model.render(1);

            if (getTextureOverlay() == null) {
                return;
            }

            // save these values to revert them later to prevent some rendering issues
            float lastLightmapX = OpenGlHelper.lastBrightnessX;
            float lastLightmapY = OpenGlHelper.lastBrightnessY;

            int light = 15728880;
            int lightmapX = light % 65536;
            int lightmapY = light / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
            Minecraft.getMinecraft().renderEngine.bindTexture(getTextureOverlay());
            model.render(1);

            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastLightmapX, lastLightmapY);
        }
    }
}
