package artifacts.common.item.curio.feet;

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

public class SteadfastSpikesItem extends CurioItem {

    public SteadfastSpikesItem() {
        addListener(LivingAttackEvent.class, this::onLivingAttack);
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
            result.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, new ResourceLocation(Artifacts.MODID, "steadfast_spikes_knockback_resistance").toString(), 1, AttributeModifier.Operation.ADDITION));
        }
        return result;
    }
}
