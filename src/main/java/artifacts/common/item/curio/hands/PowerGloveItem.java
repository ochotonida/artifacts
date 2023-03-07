package artifacts.common.item.curio.hands;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class PowerGloveItem extends CurioItem {

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);
        if (!ModConfig.server.isCosmetic(this)) {
            int attackDamageBonus = ModConfig.server.powerGlove.attackDamageBonus.get();
            result.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, Artifacts.id("power_glove_attack_damage").toString(), attackDamageBonus, AttributeModifier.Operation.ADDITION));
        }
        return result;
    }
}
