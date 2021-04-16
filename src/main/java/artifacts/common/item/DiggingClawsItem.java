package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.hands.ClawsModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class DiggingClawsItem extends GloveItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/digging_claws.png");

    public DiggingClawsItem() {
        addListener(EventPriority.LOW, PlayerEvent.BreakSpeed.class, this::onBreakSpeed, PlayerEvent::getPlayer);
        addListener(PlayerEvent.HarvestCheck.class, this::onHarvestCheck);
    }

    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        event.setNewSpeed(event.getNewSpeed() + 3.2F);
    }

    public void onHarvestCheck(PlayerEvent.HarvestCheck event) {
        if (!event.canHarvest()) {
            event.setCanHarvest(event.getTargetBlock().getHarvestLevel() <= 1);
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    protected ResourceLocation getSlimTexture() {
        return getTexture();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected ClawsModel createModel(boolean smallArms) {
        return new ClawsModel(smallArms);
    }
}
