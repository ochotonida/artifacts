package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.ObsidianSkullModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ObsidianSkullItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/obsidian_skull.png");

    public ObsidianSkullItem() {
        addListener(LivingHurtEvent.class, this::onLivingHurt);
    }

    public void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote
                && event.getAmount() >= 1
                && (event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE || event.getSource() == DamageSource.LAVA)
                && event.getEntity() instanceof PlayerEntity) {

            if (!((PlayerEntity) event.getEntity()).getCooldownTracker().hasCooldown(this)) {
                event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 600, 0, false, true));
                ((PlayerEntity) event.getEntity()).getCooldownTracker().setCooldown(this, 1200);
            }
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 1);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new ObsidianSkullModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
