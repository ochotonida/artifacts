package artifacts.common.item.curio.feet;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class FlippersItem extends CurioItem {

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity().tickCount % 20 == 0 && slotContext.entity().isSwimming()) {
            damageStack(slotContext, stack);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);
        if (!ModConfig.server.isCosmetic(this)) {
            double swimSpeedBonus = ModConfig.server.flippers.swimSpeedBonus.get();
            result.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(uuid, Artifacts.id("flipper_swim_speed").toString(), swimSpeedBonus, AttributeModifier.Operation.ADDITION));
        }
        return result;
    }
}
