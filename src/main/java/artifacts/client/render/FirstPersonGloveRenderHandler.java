package artifacts.client.render;

import artifacts.common.config.ModConfig;
import artifacts.common.item.GloveItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

@Mod.EventBusSubscriber(Dist.CLIENT)
@SuppressWarnings("unused")
public class FirstPersonGloveRenderHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderHand(RenderHandEvent event) {
        if (!event.isCanceled() && Minecraft.getInstance().player != null && ModConfig.client.showFirstPersonGloves.get()) {
            event.getMatrixStack().pushPose();
            ClientPlayerEntity player = Minecraft.getInstance().player;
            HandSide handside = event.getHand() == Hand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
            if (event.getItemStack().isEmpty()) {
                if (event.getHand() == Hand.MAIN_HAND) {
                    renderGloveOnHand(event.getMatrixStack(), event.getBuffers(), event.getLight(), event.getEquipProgress(), event.getSwingProgress(), player, handside, event.getHand());
                }
            } else if (event.getItemStack().getItem() == Items.FILLED_MAP) {
                if (event.getHand() == Hand.MAIN_HAND && player.getOffhandItem().isEmpty()) {
                    renderTwoHandedMapGloves(event.getMatrixStack(), event.getBuffers(), event.getLight(), event.getInterpolatedPitch(), event.getEquipProgress(), event.getSwingProgress(), player, handside);
                } else {
                    renderSingleHandedMapGlove(event.getMatrixStack(), event.getBuffers(), event.getLight(), event.getEquipProgress(), event.getSwingProgress(), player, handside, event.getHand());
                }
            }
            event.getMatrixStack().popPose();
        }
    }

    private static ItemStack getEquippedGlove(ClientPlayerEntity entity, Hand hand) {
        LazyOptional<ICuriosItemHandler> optionalHandler = CuriosApi.getCuriosHelper().getCuriosHandler(entity);
        if (!optionalHandler.isPresent()) {
            return ItemStack.EMPTY;
        }
        // noinspection ConstantConditions
        ICuriosItemHandler handler = optionalHandler.orElse(null);
        ICurioStacksHandler stacksHandler = handler.getCurios().get("hands");
        if (stacksHandler == null) {
            return ItemStack.EMPTY;
        }
        IDynamicStackHandler stacks = stacksHandler.getStacks();
        IDynamicStackHandler cosmeticStacks = stacksHandler.getCosmeticStacks();

        for (int slot = hand == Hand.MAIN_HAND ? 0 : 1; slot < stacks.getSlots(); slot += 2) {
            ItemStack stack = cosmeticStacks.getStackInSlot(slot);
            if (stack.isEmpty()) {
                if (stacksHandler.getRenders().get(slot)) {
                    stack = stacks.getStackInSlot(slot);
                } else {
                    continue;
                }
            }

            if (stack.getItem() instanceof GloveItem) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    private static void renderGloveOnHand(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, float equipProgress, float swingProgress, ClientPlayerEntity player, HandSide handSide, Hand hand) {
        ItemStack glove = getEquippedGlove(player, hand);
        if (glove.isEmpty()) {
            return;
        }

        int side = handSide == HandSide.RIGHT ? 1 : -1;
        float swingProgressRoot = MathHelper.sqrt(swingProgress);
        float xOffset = -0.3F * MathHelper.sin(swingProgressRoot * (float) Math.PI);
        float yOffset = 0.4F * MathHelper.sin(swingProgressRoot * ((float) Math.PI * 2F));
        float zOffset = -0.4F * MathHelper.sin(swingProgress * (float) Math.PI);
        matrixStack.translate(side * (xOffset + 0.64), yOffset - 0.6 + equipProgress * -0.6, zOffset - 0.72);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(side * 45));
        float zRotation = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float yRotation = MathHelper.sin(swingProgressRoot * (float) Math.PI);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(side * yRotation * 70));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(side * zRotation * -20));
        matrixStack.translate(side * -1, 3.6, 3.5);
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(side * 120));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(200));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(side * -135));
        matrixStack.translate(side * 5.6, 0, 0);

        ((GloveItem) glove.getItem()).renderArm(matrixStack, buffer, combinedLight, player, handSide, glove.hasFoil());
    }

    private static void renderSingleHandedMapGlove(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, float equippedProgress, float swingProgress, ClientPlayerEntity player, HandSide handSide, Hand hand) {
        float side = handSide == HandSide.RIGHT ? 1 : -1;
        matrixStack.translate(side * 0.125, -0.125, 0);
        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(side * 10));
        renderGloveOnHand(matrixStack, buffer, combinedLight, equippedProgress, swingProgress, player, handSide, hand);
        matrixStack.popPose();
    }

    private static void renderTwoHandedMapGloves(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, float pitch, float equippedProgress, float swingProgress, ClientPlayerEntity player, HandSide mainHandSide) {
        float swingProgressRoot = MathHelper.sqrt(swingProgress);
        float yOffset = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
        float zOffset = -0.4F * MathHelper.sin(swingProgressRoot * (float) Math.PI);
        matrixStack.translate(0, -yOffset / 2, zOffset);
        float mapAngle = getMapAngle(pitch);
        matrixStack.translate(0, 0.04 + equippedProgress * -1.2 + mapAngle * -0.5, -0.72);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(mapAngle * -85));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(90));

        renderTwoHandedMapGlove(matrixStack, buffer, combinedLight, player, mainHandSide, Hand.MAIN_HAND);
        renderTwoHandedMapGlove(matrixStack, buffer, combinedLight, player, mainHandSide.getOpposite(), Hand.OFF_HAND);
    }

    private static float getMapAngle(float pitch) {
        float f = 1 - pitch / 45 + 0.1F;
        f = MathHelper.clamp(f, 0, 1);
        return -MathHelper.cos(f * (float) Math.PI) * 0.5F + 0.5F;
    }

    private static void renderTwoHandedMapGlove(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, ClientPlayerEntity player, HandSide handSide, Hand hand) {
        ItemStack glove = getEquippedGlove(player, hand);
        if (glove.isEmpty()) {
            if (hand == Hand.OFF_HAND) {
                System.out.println("glove = " + glove);
            }
            return;
        }

        matrixStack.pushPose();
        float side = handSide == HandSide.RIGHT ? 1 : -1;
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(92));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(45));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(side * -41));
        matrixStack.translate(side * 0.3, -1.1, 0.45);

        ((GloveItem) glove.getItem()).renderArm(matrixStack, buffer, combinedLight, player, handSide, glove.hasFoil());
        matrixStack.popPose();
    }
}
