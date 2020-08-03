package artifacts.client.render.model.curio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;

public class GloveModel extends PlayerModel<LivingEntity> {

    public GloveModel(boolean smallArms) {
        super(0.5F, smallArms);

        setVisible(false);
    }

    public void renderHand(boolean mainHand, LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (Minecraft.getInstance().gameSettings.mainHand == HandSide.LEFT) {
            mainHand = !mainHand;
        }
        bipedRightArmwear.copyModelAngles(bipedRightArm);
        bipedLeftArmwear.copyModelAngles(bipedLeftArm);
        bipedLeftArm.showModel = !mainHand;
        bipedLeftArmwear.showModel = !mainHand;
        bipedRightArm.showModel = mainHand;
        bipedRightArmwear.showModel = mainHand;
        render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
}
