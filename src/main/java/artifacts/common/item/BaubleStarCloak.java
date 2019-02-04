package artifacts.common.item;

import artifacts.Artifacts;
import baubles.api.BaubleType;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaubleStarCloak extends BaubleBase implements IRenderCloak {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/star_cloak.png");

    private static final ResourceLocation TEXTURE_OVERLAY = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/star_cloak_overlay.png");

    public BaubleStarCloak() {
        super("star_cloak", BaubleType.BODY);
        setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getTexture() {
        return TEXTURES;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getTextureOverlay() {
        return TEXTURE_OVERLAY;
    }
}
