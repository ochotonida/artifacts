package artifacts.common.item;

import artifacts.client.render.model.curio.DrinkingHatModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import top.theillusivec4.curios.api.CuriosAPI;

public class DrinkingHatItem extends ArtifactItem {

    private final ResourceLocation texture;

    public DrinkingHatItem(String name, ResourceLocation texture) {
        super(new Properties(), name);
        this.texture = texture;
        MinecraftForge.EVENT_BUS.addListener(this::onItemUseStart);
    }

    public void onItemUseStart(LivingEntityUseItemEvent.Start event) {
        if (CuriosAPI.getCurioEquipped(this, event.getEntityLiving()).isPresent()) {
            if (event.getItem().getUseAction() == UseAction.DRINK) {
                event.setDuration(event.getDuration() / 4);
            }
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {

            private Object model;

            @Override
            protected SoundEvent getEquipSound() {
                return SoundEvents.ITEM_BOTTLE_FILL;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected DrinkingHatModel getModel() {
                if (model == null) {
                    model = new DrinkingHatModel();
                }
                return (DrinkingHatModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return texture;
            }
        });
    }
}
