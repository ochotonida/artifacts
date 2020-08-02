package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.ClawsModel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.player.PlayerEvent;
import top.theillusivec4.curios.api.CuriosAPI;

public class DiggingClawsItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/digging_claws_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/digging_claws_default.png");

    public DiggingClawsItem() {
        super(new Properties(), "digging_claws");
        MinecraftForge.EVENT_BUS.addListener(this::onBreakSpeed);
        MinecraftForge.EVENT_BUS.addListener(this::onHarvestCheck);
    }

    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (CuriosAPI.getCurioEquipped(this, event.getEntityLiving()).isPresent()) {
            event.setNewSpeed(event.getNewSpeed() + 4);
        }
    }

    public void onHarvestCheck(PlayerEvent.HarvestCheck event) {
        if (!event.canHarvest() && CuriosAPI.getCurioEquipped(this, event.getEntityLiving()).isPresent()) {
            event.setCanHarvest(event.canHarvest() || event.getTargetBlock().getHarvestLevel() <= 2);
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new GloveCurio(this) {

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE_DEFAULT;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getSlimTexture() {
                return TEXTURE_SLIM;
            }

            @OnlyIn(Dist.CLIENT)
            protected ClawsModel getSlimModel() {
                if (modelSlim == null) {
                    modelSlim = new ClawsModel(true);
                }
                return (ClawsModel) modelSlim;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ClawsModel getModel() {
                if (modelDefault == null) {
                    modelDefault = new ClawsModel(false);
                }
                return (ClawsModel) modelDefault;
            }
        });
    }
}
