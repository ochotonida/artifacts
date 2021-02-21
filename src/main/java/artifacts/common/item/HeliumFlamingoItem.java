package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.HeliumFlamingoModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;

public class HeliumFlamingoItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/helium_flamingo.png");

    public HeliumFlamingoItem() {
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
    }

    private void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof ItemEntity && ((ItemEntity) event.getEntity()).getItem().getItem() == this) {
            event.getEntity().setNoGravity(true);
        }
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ENTITY_ITEM_PICKUP, 1, 0.7F);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new HeliumFlamingoModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
