package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.feet.FlippersModel;
import artifacts.common.config.ModConfig;
import com.google.common.collect.Multimap;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class FlippersItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/flippers.png");

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity.tickCount % 20 == 0 && livingEntity.isSwimming()) {
            damageStack(identifier, index, livingEntity, stack);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getAttributeModifiers(slotContext, uuid, stack);
        if (!ModConfig.server.isCosmetic(this)) {
            double swimSpeedBonus = ModConfig.server.flippers.swimSpeedBonus.get();
            result.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(uuid, new ResourceLocation(Artifacts.MODID, "flipper_swim_speed").toString(), swimSpeedBonus, AttributeModifier.Operation.ADDITION));
        }
        return result;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new FlippersModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
