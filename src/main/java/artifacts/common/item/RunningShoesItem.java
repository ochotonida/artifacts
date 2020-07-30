package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.RunningShoesModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.UUID;

public class RunningShoesItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/running_shoes.png");

    private static final AttributeModifier RUNNING_SHOES_SPEED_BOOST = new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff305"), "artifacts:running_shoes_movement_speed", 0.4, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public RunningShoesItem() {
        super(new Properties(), "running_shoes");
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            @OnlyIn(Dist.CLIENT)
            protected RunningShoesModel getModel() {
                if (model == null) {
                    model = new RunningShoesModel();
                }
                return (RunningShoesModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }

            @Override
            @SuppressWarnings("ConstantConditions")
            public void curioTick(String identifier, int index, LivingEntity livingEntity) {
                ModifiableAttributeInstance movementSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
                if (livingEntity.isSprinting()) {
                    if (!movementSpeed.hasModifier(RUNNING_SHOES_SPEED_BOOST)) {
                        movementSpeed.applyNonPersistentModifier(RUNNING_SHOES_SPEED_BOOST);
                    }
                    if (livingEntity instanceof PlayerEntity) {
                        livingEntity.stepHeight = Math.max(livingEntity.stepHeight, 1.1F);
                    }
                } else if (movementSpeed.hasModifier(RUNNING_SHOES_SPEED_BOOST)) {
                    movementSpeed.removeModifier(RUNNING_SHOES_SPEED_BOOST);
                    livingEntity.stepHeight = 0.6F;
                }
            }

            @Override
            @SuppressWarnings("ConstantConditions")
            public void onUnequip(String identifier, int index, LivingEntity livingEntity) {
                ModifiableAttributeInstance movementSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
                if (movementSpeed.hasModifier(RUNNING_SHOES_SPEED_BOOST)) {
                    movementSpeed.removeModifier(RUNNING_SHOES_SPEED_BOOST);
                    livingEntity.stepHeight = 0.6F;
                }
            }
        });
    }
}
