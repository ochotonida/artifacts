package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.feet.ShoesModel;
import artifacts.common.config.ModConfig;
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

    private static AttributeModifier getSpeedBonus() {
        double speedMultiplier = ModConfig.server.runningShoes.speedMultiplier.get();
        return new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff305"), "artifacts:running_shoes_movement_speed", speedMultiplier, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

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
        if (!ModConfig.server.isCosmetic(this)) {
            ModifiableAttributeInstance movementSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeModifier speedBonus = getSpeedBonus();
            if (livingEntity.isSprinting()) {
                if (!movementSpeed.hasModifier(speedBonus)) {
                    movementSpeed.addTransientModifier(speedBonus);
                }
                if (livingEntity instanceof PlayerEntity) {
                    livingEntity.maxUpStep = Math.max(livingEntity.maxUpStep, 1.1F);
                }
            } else if (movementSpeed.hasModifier(speedBonus)) {
                movementSpeed.removeModifier(speedBonus);
                livingEntity.maxUpStep = 0.6F;
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        ModifiableAttributeInstance movementSpeed = slotContext.getWearer().getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier speedBonus = getSpeedBonus();
        if (movementSpeed != null && movementSpeed.hasModifier(speedBonus)) {
            movementSpeed.removeModifier(speedBonus);
            slotContext.getWearer().maxUpStep = 0.6F;
        }
    }
}
