package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.PanicNecklaceModel;
import artifacts.common.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;

public class PanicNecklaceItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/panic_necklace.png");

    public PanicNecklaceItem() {
        super(new Properties(), "panic_necklace");
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            protected SoundEvent getEquipSound() {
                return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
            }

            @Override
            protected PanicNecklaceModel getModel() {
                if (model == null) {
                    model = new PanicNecklaceModel();
                }
                return (PanicNecklaceModel) model;
            }

            @Override
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    @SuppressWarnings("unused")
    public static class Events {

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (!event.getEntity().world.isRemote && event.getAmount() >= 1) {
                if (CuriosAPI.getCurioEquipped(Items.PANIC_NECKLACE, event.getEntityLiving()).isPresent()) {
                    event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.SPEED, 70, 1, false, false));
                }
            }
        }
    }
}
