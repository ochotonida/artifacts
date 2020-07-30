package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.PanicNecklaceModel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class PanicNecklaceItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/panic_necklace.png");

    public PanicNecklaceItem() {
        super(new Properties(), "panic_necklace");
        MinecraftForge.EVENT_BUS.addListener(this::onLivingHurt);
    }

    public void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getAmount() >= 1) {
            if (CuriosApi.getCuriosHelper().findEquippedCurio(this, event.getEntityLiving()).isPresent()) {
                event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.SPEED, 160, 0, false, false));
            }
        }
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
            @OnlyIn(Dist.CLIENT)
            protected PanicNecklaceModel getModel() {
                if (model == null) {
                    model = new PanicNecklaceModel();
                }
                return (PanicNecklaceModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }
}
