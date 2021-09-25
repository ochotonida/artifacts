package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.capability.swimhandler.ISwimHandler;
import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import artifacts.common.config.ModConfig;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.UUID;

public class UmbrellaItem extends ArtifactItem {

    public static final AttributeModifier UMBRELLA_SLOW_FALLING = new AttributeModifier(UUID.fromString("a7a25453-2065-4a96-bc83-df600e13f390"), "artifacts:umbrella_slow_falling", -0.875, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public UmbrellaItem() {
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingUpdate);
    }

    private void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!ModConfig.server.isCosmetic(this)) {
            LivingEntity entity = event.getEntityLiving();
            ModifiableAttributeInstance gravity = entity.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
            if (gravity != null) {
                boolean isInWater = entity.isInWater() && !entity.getCapability(SwimHandlerCapability.INSTANCE).map(ISwimHandler::isSinking).orElse(false);
                if (!entity.isOnGround() && !isInWater && event.getEntity().getDeltaMovement().y < 0 && !entity.hasEffect(Effects.SLOW_FALLING)
                        && (entity.getOffhandItem().getItem() == this
                        || entity.getMainHandItem().getItem() == this) && !(entity.isUsingItem() && !entity.getUseItem().isEmpty() && entity.getUseItem().getItem().getUseAnimation(entity.getUseItem()) == UseAction.BLOCK)) {
                    if (!gravity.hasModifier(UMBRELLA_SLOW_FALLING)) {
                        gravity.addTransientModifier(UMBRELLA_SLOW_FALLING);
                    }
                    entity.fallDistance = 0;
                } else if (gravity.hasModifier(UMBRELLA_SLOW_FALLING)) {
                    gravity.removeModifier(UMBRELLA_SLOW_FALLING);
                }
            }
        }
    }

    @Override
    public boolean isShield(@Nullable ItemStack stack, @Nullable LivingEntity entity) {
        return !ModConfig.server.isCosmetic(this) && ModConfig.server.umbrella.isShield.get();
    }

    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BLOCK;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!isShield(null, null)) {
            return super.use(world, player, hand);
        }
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return ActionResult.consume(itemstack);
    }

    public static boolean isHoldingUmbrellaUpright(LivingEntity entity, Hand hand) {
        return entity.getItemInHand(hand).getItem() instanceof UmbrellaItem && (!entity.isUsingItem() || entity.getUsedItemHand() != hand);
    }

    public static boolean isHoldingUmbrellaUpright(LivingEntity entity) {
        return isHoldingUmbrellaUpright(entity, Hand.MAIN_HAND) || isHoldingUmbrellaUpright(entity, Hand.OFF_HAND);
    }

    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Artifacts.MODID)
    public static class ClientEvents {

        @SubscribeEvent
        public static void onLivingRender(RenderLivingEvent.Pre<?, ?> event) {
            if (!(event.getRenderer().getModel() instanceof BipedModel)) {
                return;
            }

            LivingEntity entity = event.getEntity();
            BipedModel<?> model = (BipedModel<?>) event.getRenderer().getModel();

            boolean isHoldingOffHand = isHoldingUmbrellaUpright(entity, Hand.OFF_HAND);
            boolean isHoldingMainHand = isHoldingUmbrellaUpright(entity, Hand.MAIN_HAND);
            boolean isRightHanded = entity.getMainArm() == HandSide.RIGHT;

            if ((isHoldingMainHand && isRightHanded) || (isHoldingOffHand && !isRightHanded)) {
                model.rightArmPose = BipedModel.ArmPose.THROW_SPEAR;
            }
            if ((isHoldingMainHand && !isRightHanded) || (isHoldingOffHand && isRightHanded)) {
                model.leftArmPose = BipedModel.ArmPose.THROW_SPEAR;
            }
        }
    }
}
