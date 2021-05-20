package artifacts.common.item.curio.head;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class SuperstitiousHatItem extends CurioItem {

    public SuperstitiousHatItem() {
        addListener(LivingDeathEvent.class, this::onLivingDeath, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    private void onLivingDeath(LivingDeathEvent event, LivingEntity wearer) {
        damageEquippedStacks(wearer);
    }

    @Override
    public int getLootingBonus(String identifier, LivingEntity livingEntity, ItemStack curio, int index) {
        return ModConfig.server.isCosmetic(this) ? 0 : ModConfig.server.superstitiousHat.lootingBonus.get();
    }
}
