package artifacts.common.item.curio.hands;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import artifacts.common.util.DamageSourceHelper;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class PowerGloveItem extends CurioItem {

    public PowerGloveItem() {
        addListener(LivingAttackEvent.class, this::onLivingAttack, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        if (DamageSourceHelper.isMeleeAttack(event.getSource())) {
            damageEquippedStacks(wearer);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);
        if (!ModConfig.server.isCosmetic(this)) {
            int attackDamageBonus = ModConfig.server.powerGlove.attackDamageBonus.get();
            result.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, new ResourceLocation(Artifacts.MODID, "power_glove_attack_damage").toString(), attackDamageBonus, AttributeModifier.Operation.ADDITION));
        }
        return result;
    }
}
