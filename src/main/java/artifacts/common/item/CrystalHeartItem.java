package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.belt.CrystalHeartModel;
import artifacts.common.config.ModConfig;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class CrystalHeartItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/crystal_heart.png");

    public CrystalHeartItem() {
        addListener(LivingDamageEvent.class, this::onLivingDamage);
    }

    private static AttributeModifier getHealthBonus() {
        int healthBonus = ModConfig.server.crystalHeart.healthBonus.get();
        return new AttributeModifier(UUID.fromString("99fa0537-90b9-481a-bc76-4650987faba3"), "artifacts:crystal_heart_health_bonus", healthBonus, AttributeModifier.Operation.ADDITION);
    }

    private void onLivingDamage(LivingDamageEvent event) {
        if (!event.isCanceled() && event.getAmount() >= 1) {
            damageEquippedStacks(event.getEntityLiving(), (int) event.getAmount());
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack originalStack, ItemStack newStack) {
        if (!ModConfig.server.isCosmetic(this) && !slotContext.getWearer().level.isClientSide()) {
            ModifiableAttributeInstance health = slotContext.getWearer().getAttribute(Attributes.MAX_HEALTH);
            AttributeModifier healthBonus = getHealthBonus();
            if (health != null && !health.hasModifier(healthBonus)) {
                health.addPermanentModifier(healthBonus);
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack originalStack, ItemStack newStack) {
        if (!ModConfig.server.isCosmetic(this) && !slotContext.getWearer().level.isClientSide()) {
            ModifiableAttributeInstance health = slotContext.getWearer().getAttribute(Attributes.MAX_HEALTH);
            AttributeModifier healthBonus = getHealthBonus();
            if (health != null && health.hasModifier(healthBonus)) {
                health.removeModifier(healthBonus);
                if (slotContext.getWearer().getHealth() > slotContext.getWearer().getMaxHealth()) {
                    slotContext.getWearer().setHealth(slotContext.getWearer().getMaxHealth());
                }
            }
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND, 1, 1);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new CrystalHeartModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
