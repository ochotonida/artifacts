package artifacts.common.item.curio.belt;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ObsidianSkullItem extends CurioItem {

    public ObsidianSkullItem() {
        addListener(LivingHurtEvent.class, this::onLivingHurt);
    }

    private void onLivingHurt(LivingHurtEvent event, LivingEntity wearer) {
        if (
                !wearer.level.isClientSide
                && event.getAmount() >= 1
                && event.getSource().isFire()
                && wearer instanceof Player player
                && !player.getCooldowns().isOnCooldown(this)
        ) {
            int cooldown = ModConfig.server.obsidianSkull.cooldown.get();
            int fireResistanceDuration = ModConfig.server.obsidianSkull.fireResistanceDuration.get();

            wearer.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, fireResistanceDuration, 0, false, true));
            player.getCooldowns().addCooldown(this, cooldown);

            damageEquippedStacks(wearer);
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_IRON, 1, 1);
    }
}
