package artifacts.common.item;

import artifacts.client.render.model.curio.DrinkingHatModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.UseAction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class DrinkingHatItem extends CurioItem {

    private final ResourceLocation texture; //TODO move to artifactItem (use name)

    public DrinkingHatItem(ResourceLocation texture) {
        this.texture = texture;
        MinecraftForge.EVENT_BUS.addListener(this::onItemUseStart);
    }

    public void onItemUseStart(LivingEntityUseItemEvent.Start event) {
        if (CuriosApi.getCuriosHelper().findEquippedCurio(this, event.getEntityLiving()).isPresent()) {
            if (event.getItem().getUseAction() == UseAction.DRINK) {
                event.setDuration(event.getDuration() / 4);
            }
        }
    }

    @Override
    protected SoundEvent getEquipSound() {
        return SoundEvents.ITEM_BOTTLE_FILL;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new DrinkingHatModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return texture;
    }
}
