package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.DrinkingHatModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;

public class DrinkingHatItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/plastic_drinking_hat.png");
    private static final ResourceLocation TEXTURE_NOVELTY = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/novelty_drinking_hat.png");

    private final boolean isNoveltyHat;

    public DrinkingHatItem(String name, boolean isNoveltyHat) {
        super(new Properties(), name);
        this.isNoveltyHat = isNoveltyHat;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return isNoveltyHat ? Rarity.EPIC : Rarity.RARE;
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
            protected DrinkingHatModel getModel() {
                if (model == null) {
                    model = new DrinkingHatModel();
                }
                return (DrinkingHatModel) model;
            }

            @Override
            protected ResourceLocation getTexture() {
                return isNoveltyHat ? TEXTURE_NOVELTY : TEXTURE_DEFAULT;
            }
        });
    }

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    @SuppressWarnings("unused")
    public static class Events {

        @SubscribeEvent
        public static void onItemUseStart(LivingEntityUseItemEvent.Start event) {
            if (CuriosAPI.getCurioEquipped(stack -> stack.getItem() instanceof DrinkingHatItem, event.getEntityLiving()).isPresent()) {
                if (event.getItem().getUseAction() == UseAction.DRINK) {
                    event.setDuration(event.getDuration() / 4);
                }
            }
        }
    }
}
