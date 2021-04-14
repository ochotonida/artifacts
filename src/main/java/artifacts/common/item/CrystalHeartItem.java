package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.CrystalHeartModel;
import artifacts.common.config.Config;
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
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class CrystalHeartItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/crystal_heart.png");

    private static final AttributeModifier HEALTH_BONUS = new AttributeModifier(UUID.fromString("99fa0537-90b9-481a-bc76-4650987faba3"), "artifacts:crystal_heart_health_bonus", 10, AttributeModifier.Operation.ADDITION);

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND, 1, 1);
    }

    @Override
    public void onEquip(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (!Config.isCosmetic(this) && !entity.level.isClientSide()) {
            ModifiableAttributeInstance health = entity.getAttribute(Attributes.MAX_HEALTH);
            if (health != null && !health.hasModifier(HEALTH_BONUS)) {
                health.addPermanentModifier(HEALTH_BONUS);
            }
        }
    }

    @Override
    public void onUnequip(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (!Config.isCosmetic(this) && !entity.level.isClientSide()) {
            ModifiableAttributeInstance health = entity.getAttribute(Attributes.MAX_HEALTH);
            if (health != null && health.hasModifier(HEALTH_BONUS)) {
                health.removeModifier(HEALTH_BONUS);
                if (entity.getHealth() > entity.getMaxHealth()) {
                    entity.setHealth(entity.getMaxHealth());
                }
            }
        }
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
