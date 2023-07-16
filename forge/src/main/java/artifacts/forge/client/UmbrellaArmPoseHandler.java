package artifacts.forge.client;

import artifacts.forge.item.UmbrellaItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;

public class UmbrellaArmPoseHandler {

    public static void setup() {
        MinecraftForge.EVENT_BUS.addListener(UmbrellaArmPoseHandler::onLivingRender);
    }

    public static void onLivingRender(RenderLivingEvent.Pre<?, ?> event) {
        if (!(event.getRenderer().getModel() instanceof HumanoidModel<?> model)) {
            return;
        }

        LivingEntity entity = event.getEntity();

        boolean isHoldingOffHand = UmbrellaItem.isHoldingUmbrellaUpright(entity, InteractionHand.OFF_HAND);
        boolean isHoldingMainHand = UmbrellaItem.isHoldingUmbrellaUpright(entity, InteractionHand.MAIN_HAND);
        boolean isRightHanded = entity.getMainArm() == HumanoidArm.RIGHT;

        if ((isHoldingMainHand && isRightHanded) || (isHoldingOffHand && !isRightHanded)) {
            model.rightArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
        }
        if ((isHoldingMainHand && !isRightHanded) || (isHoldingOffHand && isRightHanded)) {
            model.leftArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
        }
    }
}
