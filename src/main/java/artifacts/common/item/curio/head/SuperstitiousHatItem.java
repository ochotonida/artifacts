package artifacts.common.item.curio.head;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import top.theillusivec4.curios.api.SlotContext;

public class SuperstitiousHatItem extends CurioItem {

    public SuperstitiousHatItem() {
        addListener(LivingDeathEvent.class, this::onLivingDeath, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    private void onLivingDeath(LivingDeathEvent event, LivingEntity wearer) {
        damageEquippedStacks(wearer);
    }

    @Override
    public int getLootingLevel(SlotContext slotContext, DamageSource source, LivingEntity target, int baseLooting, ItemStack stack) {
        return ModConfig.server.isCosmetic(this) ? 0 : ModConfig.server.superstitiousHat.lootingBonus.get();
    }
}
