package artifacts.client;

import artifacts.item.UmbrellaItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class UmbrellaArmPoseHelper {

    public static void setUmbrellaArmPose(EntityModel<?> model, LivingEntity entity) {
        if (model instanceof HumanoidModel<?> humanoidModel) {
            boolean isHoldingOffHand = UmbrellaItem.isHoldingUmbrellaUpright(entity, InteractionHand.OFF_HAND);
            boolean isHoldingMainHand = UmbrellaItem.isHoldingUmbrellaUpright(entity, InteractionHand.MAIN_HAND);
            boolean isRightHanded = entity.getMainArm() == HumanoidArm.RIGHT;

            if ((isHoldingMainHand && isRightHanded) || (isHoldingOffHand && !isRightHanded)) {
                humanoidModel.rightArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
            }
            if ((isHoldingMainHand && !isRightHanded) || (isHoldingOffHand && isRightHanded)) {
                humanoidModel.leftArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
            }
        }
    }
}
