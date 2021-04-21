package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.feet.ShoesModel;
import artifacts.common.config.Config;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class RunningShoesItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/running_shoes.png");

    private static final AttributeModifier RUNNING_SHOES_SPEED_BOOST = new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff305"), "artifacts:running_shoes_movement_speed", 0.4, AttributeModifier.Operation.MULTIPLY_TOTAL);

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new ShoesModel(0.5F);
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (!Config.isCosmetic(this)) {
            ModifiableAttributeInstance movementSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (livingEntity.isSprinting()) {
                if (!movementSpeed.hasModifier(RUNNING_SHOES_SPEED_BOOST)) {
                    movementSpeed.addTransientModifier(RUNNING_SHOES_SPEED_BOOST);
                }
                if (livingEntity instanceof PlayerEntity) {
                    livingEntity.maxUpStep = Math.max(livingEntity.maxUpStep, 1.1F);
                }
            } else if (movementSpeed.hasModifier(RUNNING_SHOES_SPEED_BOOST)) {
                movementSpeed.removeModifier(RUNNING_SHOES_SPEED_BOOST);
                livingEntity.maxUpStep = 0.6F;
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        ModifiableAttributeInstance movementSpeed = slotContext.getWearer().getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed != null && movementSpeed.hasModifier(RUNNING_SHOES_SPEED_BOOST)) {
            movementSpeed.removeModifier(RUNNING_SHOES_SPEED_BOOST);
            slotContext.getWearer().maxUpStep = 0.6F;
        }
    }
}
