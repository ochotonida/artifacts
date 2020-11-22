package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.CloudInABottleModel;
import artifacts.common.init.Items;
import artifacts.common.network.DoubleJumpPacket;
import artifacts.common.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class CloudInABottleItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/cloud_in_a_bottle.png");

    public CloudInABottleItem() {
        super(new Item.Properties(), "cloud_in_a_bottle");
        MinecraftForge.EVENT_BUS.register(new ClientJumpHandler());
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, this::onLivingFall);
    }

    public static void jump(PlayerEntity player) {
        double upwardsMotion = 0.5;
        if (player.isPotionActive(Effects.JUMP_BOOST)) {
            // noinspection ConstantConditions
            upwardsMotion += 0.1 * (player.getActivePotionEffect(Effects.JUMP_BOOST).getAmplifier() + 1);
        }
        upwardsMotion *= player.isSprinting() ? 1.5 : 1;

        Vector3d motion = player.getMotion();
        double motionMultiplier = player.isSprinting() ? 0.65 : 0;
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
    }

    public void onLivingFall(LivingFallEvent event) {
        if (CuriosApi.getCuriosHelper().findEquippedCurio(this, event.getEntityLiving()).isPresent()) {
            event.setDistance(Math.max(0, event.getDistance() - 3));
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            protected SoundEvent getEquipSound() {
                return SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected CloudInABottleModel getModel() {
                if (model == null) {
                    model = new CloudInABottleModel();
                }
                return (CloudInABottleModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }

    private class ClientJumpHandler {

        @OnlyIn(Dist.CLIENT)
        private boolean canDoubleJump;

        @OnlyIn(Dist.CLIENT)
        private boolean hasReleasedJumpKey;

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        @SuppressWarnings("unused")
        public void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                ClientPlayerEntity player = Minecraft.getInstance().player;

                if (player != null) {
                    if ((player.isOnGround() || player.isOnLadder()) && !player.isInWater()) {
                        hasReleasedJumpKey = false;
                        canDoubleJump = true;
                    } else {
                        if (!player.movementInput.jump) {
                            hasReleasedJumpKey = true;
                        } else {
                            if (!player.abilities.isFlying && canDoubleJump && hasReleasedJumpKey) {
                                canDoubleJump = false;
                                CuriosApi.getCuriosHelper().findEquippedCurio(CloudInABottleItem.this, player).ifPresent(stack -> {

                                    NetworkHandler.INSTANCE.sendToServer(new DoubleJumpPacket());
                                    jump(player);
                                    player.fallDistance = 0;
                                    if (CuriosApi.getCuriosHelper().findEquippedCurio(Items.WHOOPEE_CUSHION, player).isPresent()) {
                                        player.playSound(artifacts.common.init.SoundEvents.FART, 1, 0.9F + player.getRNG().nextFloat() * 0.2F);
                                    } else {
                                        player.playSound(SoundEvents.BLOCK_WOOL_FALL, 1, 0.9F + player.getRNG().nextFloat() * 0.2F);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }
}
