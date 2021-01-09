package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.ClawsModel;
import artifacts.client.render.model.curio.GloveModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class DiggingClawsItem extends GloveItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/digging_claws_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/digging_claws_default.png");

    public DiggingClawsItem() {
        MinecraftForge.EVENT_BUS.addListener(this::onBreakSpeed);
        MinecraftForge.EVENT_BUS.addListener(this::onHarvestCheck);
    }

    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (isEquippedBy(event.getPlayer())) {
            event.setNewSpeed(event.getNewSpeed() + 4);
        }
    }

    public void onHarvestCheck(PlayerEvent.HarvestCheck event) {
        if (!event.canHarvest() && isEquippedBy(event.getPlayer())) {
            event.setCanHarvest(event.getTargetBlock().getHarvestLevel() <= 2);
        }
    }

    @Override
    protected SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE_DEFAULT;
    }

    @Override
    protected ResourceLocation getSlimTexture() {
        return TEXTURE_SLIM;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected GloveModel createModel(boolean smallArms) {
        return new ClawsModel(smallArms);
    }
}
