package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.UniversalAttractorModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.List;

public class UniversalAttractorItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/universal_attractor.png");

    public UniversalAttractorItem() {
        super(new Properties(), "universal_attractor");
        MinecraftForge.EVENT_BUS.addListener(this::onItemToss);
    }

    public void onItemToss(ItemTossEvent event) {
        CuriosApi.getCuriosHelper().findEquippedCurio(this, event.getPlayer()).ifPresent((triple) -> setCooldown(triple.right, 100));
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
            public void curioTick(String identifier, int index, LivingEntity entity) {
                if (entity.isSpectator() || !(entity instanceof PlayerEntity)) {
                    return;
                }

                int cooldown = getCooldown(stack);
                if (cooldown <= 0) {
                    Vector3d playerPos = entity.getPositionVec().add(0, 0.75, 0);

                    int range = 5;
                    List<ItemEntity> items = entity.world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
                    int pulled = 0;
                    for (ItemEntity item : items) {
                        if (item.isAlive() && !item.cannotPickup() && !item.getPersistentData().getBoolean("PreventRemoteMovement")) {
                            if (pulled++ > 200) {
                                break;
                            }

                            Vector3d motion = playerPos.subtract(item.getPositionVec().add(0, item.getHeight() / 2, 0));
                            if (Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z) > 1) {
                                motion = motion.normalize();
                            }
                            item.setMotion(motion.scale(0.6));
                        }
                    }
                } else {
                    setCooldown(stack, cooldown - 1);
                }
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected UniversalAttractorModel getModel() {
                if (model == null) {
                    model = new UniversalAttractorModel();
                }
                return (UniversalAttractorModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }
}
