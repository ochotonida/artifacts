package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.CloudInABottleModel;
import artifacts.common.init.ModItems;
import artifacts.common.init.ModSoundEvents;
import artifacts.common.network.DoubleJumpPacket;
import artifacts.common.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CloudInABottleItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/cloud_in_a_bottle.png");

    public CloudInABottleItem() {
        MinecraftForge.EVENT_BUS.register(new DoubleJumpHandler());
        addListener(EventPriority.HIGHEST, LivingFallEvent.class, this::onLivingFall);
    }

    public static void jump(PlayerEntity player) {
        player.fallDistance = 0;

        double upwardsMotion = 0.5;
        if (player.isPotionActive(Effects.JUMP_BOOST)) {
            // noinspection ConstantConditions
            upwardsMotion += 0.1 * (player.getActivePotionEffect(Effects.JUMP_BOOST).getAmplifier() + 1);
        }
        upwardsMotion *= player.isSprinting() ? 1.5 : 1;

        Vector3d motion = player.getMotion();
        double motionMultiplier = player.isSprinting() ? 0.5 : 0;
        float direction = (float) (player.rotationYaw * Math.PI / 180);
        player.setMotion(player.getMotion().add(
                -MathHelper.sin(direction) * motionMultiplier,
                upwardsMotion - motion.y,
                MathHelper.cos(direction) * motionMultiplier)
        );

        player.isAirBorne = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(player);

        player.addStat(Stats.JUMP);
        if (player.isSprinting()) {
            player.addExhaustion(0.2F);
        } else {
            player.addExhaustion(0.05F);
        }

        if (CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.WHOOPEE_CUSHION.get(), player).isPresent()) {
            player.playSound(ModSoundEvents.FART.get(), 1, 0.9F + player.getRNG().nextFloat() * 0.2F);
        } else {
            player.playSound(SoundEvents.BLOCK_WOOL_FALL, 1, 0.9F + player.getRNG().nextFloat() * 0.2F);
        }
    }

    public void onLivingFall(LivingFallEvent event) {
        event.setDistance(Math.max(0, event.getDistance() - 3));
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, 1, 1);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new CloudInABottleModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    private class DoubleJumpHandler {

        @OnlyIn(Dist.CLIENT)
        private boolean canDoubleJump;

        @OnlyIn(Dist.CLIENT)
        private boolean hasReleasedJumpKey;

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        @SuppressWarnings("unused")
        public void onClientTick(TickEvent.ClientTickEvent event) {
            ClientPlayerEntity player = Minecraft.getInstance().player;

            if (event.phase == TickEvent.Phase.END && player != null && player.movementInput != null) {
                if ((player.isOnGround() || player.isOnLadder()) && !player.isInWater()) {
                    hasReleasedJumpKey = false;
                    canDoubleJump = true;
                } else if (!player.movementInput.jump) {
                    hasReleasedJumpKey = true;
                } else if (!player.abilities.isFlying && canDoubleJump && hasReleasedJumpKey) {
                    canDoubleJump = false;

                    CuriosApi.getCuriosHelper().findEquippedCurio(CloudInABottleItem.this, player).ifPresent(stack -> {
                        NetworkHandler.INSTANCE.sendToServer(new DoubleJumpPacket());
                        jump(player);
                    });
                }
            }
        }
    }
}
