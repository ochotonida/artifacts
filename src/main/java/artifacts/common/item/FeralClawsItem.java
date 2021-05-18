package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.hands.ClawsModel;
import artifacts.common.config.ModConfig;
import artifacts.common.util.DamageSourceHelper;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class FeralClawsItem extends GloveItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/feral_claws.png");

    public FeralClawsItem() {
        addListener(LivingAttackEvent.class, this::onLivingAttack, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        if (DamageSourceHelper.isMeleeAttack(event.getSource())) {
            damageEquippedStacks(wearer);
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);
        if (!ModConfig.server.isCosmetic(this)) {
            double attackSpeedBonus = ModConfig.server.feralClaws.attackSpeedBonus.get();
            result.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, new ResourceLocation(Artifacts.MODID, "feral_claws_attack_speed").toString(), attackSpeedBonus, AttributeModifier.Operation.ADDITION));
        }
        return result;
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    protected ResourceLocation getSlimTexture() {
        return getTexture();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected ClawsModel createModel(boolean smallArms) {
        return new ClawsModel(smallArms);
    }
}
