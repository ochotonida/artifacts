package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.ObsidianSkullModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.CuriosAPI;

public class ObsidianSkullItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/obsidian_skull.png");

    public ObsidianSkullItem() {
        super(new Properties(), "obsidian_skull");
        MinecraftForge.EVENT_BUS.addListener(this::onLivingHurt);
    }

    public void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getAmount() >= 1 && (event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.LAVA) && event.getEntity() instanceof PlayerEntity) {
            if (CuriosAPI.getCurioEquipped(this, event.getEntityLiving()).isPresent() && !((PlayerEntity) event.getEntity()).getCooldownTracker().hasCooldown(this)) {
                event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 600, 0, false, true));
                ((PlayerEntity) event.getEntity()).getCooldownTracker().setCooldown(this, 1200);
            }
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ObsidianSkullModel getModel() {
                if (model == null) {
                    model = new ObsidianSkullModel();
                }
                return (ObsidianSkullModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }
}
