package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.MagnetModel;
import artifacts.common.init.Items;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;

import javax.annotation.Nullable;
import java.util.List;

public class MagnetItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/magnet.png");

    public MagnetItem() {
        super(new Properties(), "magnet");
    }

    public static int getCooldown(ItemStack stack) {
        return stack.getOrCreateTag().getInt("Cooldown");
    }

    public static void setCooldown(ItemStack stack, int cooldown) {
        stack.getOrCreateTag().putInt("Cooldown", cooldown);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            // magnet logic from Botania, see https://github.com/Vazkii/Botania
            @Override
            public void onCurioTick(String identifier, int index, LivingEntity entity) {
                if (entity.isSpectator() || !(entity instanceof PlayerEntity)) {
                    return;
                }

                int cooldown = getCooldown(stack);
                if (cooldown <= 0) {
                    Vec3d playerPos = entity.getPositionVector().add(0, 0.75, 0);

                    int range = 4;
                    List<ItemEntity> items = entity.world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
                    int pulled = 0;
                    for (ItemEntity item : items) {
                        if (item.isAlive() && !item.cannotPickup() && !item.getPersistentData().getBoolean("PreventRemoteMovement")) {
                            if (pulled++ > 200) {
                                break;
                            }

                            Vec3d newPos = playerPos.subtract(item.getPositionVector().add(0, item.getHeight() / 2, 0));
                            if (Math.sqrt(newPos.x * newPos.x + newPos.y * newPos.y + newPos.z * newPos.z) > 1) {
                                newPos = newPos.normalize();
                            }
                            item.setMotion(newPos.scale(0.4));
                        }
                    }
                } else {
                    setCooldown(stack, cooldown - 1);
                }
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected MagnetModel getModel() {
                if (model == null) {
                    model = new MagnetModel();
                }
                return (MagnetModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    @SuppressWarnings("unused")
    public static class Events {

        @SubscribeEvent
        public static void onItemToss(ItemTossEvent event) {
            CuriosAPI.getCurioEquipped(Items.MAGNET, event.getPlayer()).ifPresent((triple) -> setCooldown(triple.right, 100));
        }
    }
}
