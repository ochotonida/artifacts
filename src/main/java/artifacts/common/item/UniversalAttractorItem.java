package artifacts.common.item;

import artifacts.common.config.ModConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class UniversalAttractorItem extends CurioItem {

    public UniversalAttractorItem() {
        addListener(PlayerEvent.ItemPickupEvent.class, this::onItemPickup);
        MinecraftForge.EVENT_BUS.addListener(this::onItemToss);
    }

    private void onItemPickup(PlayerEvent.ItemPickupEvent event, LivingEntity wearer) {
        damageEquippedStacks(wearer);
    }

    public static int getCooldown(ItemStack stack) {
        return stack.getOrCreateTag().getInt("Cooldown");
    }

    public static void setCooldown(ItemStack stack, int cooldown) {
        stack.getOrCreateTag().putInt("Cooldown", cooldown);
    }

    private void onItemToss(ItemTossEvent event) {
        int cooldown = ModConfig.server.universalAttractor.cooldown.get();
        if (cooldown > 0) {
            CuriosApi.getCuriosHelper().findEquippedCurio(this, event.getPlayer()).ifPresent((triple) -> setCooldown(triple.right, cooldown));
        }
    }

    // magnet logic from Botania, see https://github.com/Vazkii/Botania
    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (ModConfig.server.isCosmetic(this) || entity.isSpectator() || !(entity instanceof PlayerEntity)) {
            return;
        }

        int cooldown = getCooldown(stack);
        if (cooldown <= 0) {
            Vector3d playerPos = entity.position().add(0, 0.75, 0);

            int range = ModConfig.server.universalAttractor.range.get();
            List<ItemEntity> items = entity.level.getEntitiesOfClass(ItemEntity.class, new AxisAlignedBB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
            int pulled = 0;
            for (ItemEntity item : items) {
                if (item.isAlive() && !item.hasPickUpDelay() && !item.getPersistentData().getBoolean("PreventRemoteMovement")) {
                    if (pulled++ > 200) {
                        break;
                    }

                    Vector3d motion = playerPos.subtract(item.position().add(0, item.getBbHeight() / 2, 0));
                    if (Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z) > 1) {
                        motion = motion.normalize();
                    }
                    item.setDeltaMovement(motion.scale(ModConfig.server.universalAttractor.motionMultiplier.get()));
                }
            }
        } else {
            setCooldown(stack, cooldown - 1);
        }
    }
}
