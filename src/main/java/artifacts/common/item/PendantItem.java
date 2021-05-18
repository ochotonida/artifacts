package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.necklace.PendantModel;
import artifacts.common.config.ModConfig;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public abstract class PendantItem extends CurioItem {

    private final ResourceLocation texture;

    public PendantItem(String name) {
        texture = new ResourceLocation(Artifacts.MODID, String.format("textures/entity/curio/%s.png", name));
        addListener(LivingAttackEvent.class, this::onLivingAttack);
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        LivingEntity attacker = DamageSourceHelper.getAttacker(event.getSource());
        if (!wearer.level.isClientSide()
                && event.getAmount() >= 1
                && attacker != null
                && random.nextDouble() < ModConfig.server.pendants.get(this).strikeChance.get()) {
            applyEffect(wearer, attacker);
            damageEquippedStacks(wearer);
        }
    }

    protected abstract void applyEffect(LivingEntity target, LivingEntity attacker);

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND, 1, 1);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new PendantModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return texture;
    }
}
