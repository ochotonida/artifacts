package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.CrystalHeartModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.UUID;

public class CrystalHeartItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/crystal_heart.png");

    private static final AttributeModifier HEALTH_BONUS = new AttributeModifier(UUID.fromString("99fa0537-90b9-481a-bc76-4650987faba3"), "artifacts:crystal_heart_health_bonus", 10, AttributeModifier.Operation.ADDITION);

    public CrystalHeartItem() {
        super(new Properties(), "crystal_heart");
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            public void onEquip(String identifier, int index, LivingEntity entity) {
                if (!entity.world.isRemote()) {
                    ModifiableAttributeInstance health = entity.getAttribute(Attributes.MAX_HEALTH);
                    if (health != null && !health.hasModifier(HEALTH_BONUS)) {
                        health.applyPersistentModifier(HEALTH_BONUS);
                    }
                }
            }

            @Override
            public void onUnequip(String identifier, int index, LivingEntity entity) {
                if (!entity.world.isRemote()) {
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
            protected CrystalHeartModel getModel() {
                if (model == null) {
                    model = new CrystalHeartModel();
                }
                return (CrystalHeartModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }
}
