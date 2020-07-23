package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.ClawsModel;
import artifacts.common.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;

public class DiggingClawsItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/digging_claws_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/digging_claws_default.png");

    public DiggingClawsItem() {
        super(new Properties(), "digging_claws");
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
                if (model_slim == null) {
                    model_slim = new ClawsModel(true);
                }
                return (ClawsModel) model_slim;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ClawsModel getModel() {
                if (model_default == null) {
                    model_default = new ClawsModel(false);
                }
                return (ClawsModel) model_default;
            }
        });
    }

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    @SuppressWarnings("unused")
    public static class Events {

        @SubscribeEvent
        public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
            if (CuriosAPI.getCurioEquipped(Items.DIGGING_CLAWS, event.getEntityLiving()).isPresent()) {
                event.setNewSpeed(event.getNewSpeed() + 4);
            }
        }

        @SubscribeEvent
        public static void onHarvestCheck(PlayerEvent.HarvestCheck event) {
            if (!event.canHarvest() && CuriosAPI.getCurioEquipped(Items.DIGGING_CLAWS, event.getEntityLiving()).isPresent()) {
                event.setCanHarvest(event.canHarvest() || event.getTargetBlock().getHarvestLevel() <= 2);
            }
        }
    }
}
