package artifacts.common.item.curio.feet;

import artifacts.common.init.ModGameRules;
import artifacts.common.init.ModTags;
import artifacts.common.item.curio.HurtSoundModifyingItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class KittySlippersItem extends HurtSoundModifyingItem {

    public KittySlippersItem() {
        super(SoundEvents.CAT_HURT);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
        addListener(LivingChangeTargetEvent.class, this::onLivingChangeTargetEvent, LivingChangeTargetEvent::getNewTarget);
        addListener(LivingEvent.LivingTickEvent.class, this::onLivingUpdate, event -> event.getEntity().getLastHurtByMob());
    }

    private void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof PathfinderMob creeper && creeper.getType().is(ModTags.CREEPERS)) {
            creeper.goalSelector.addGoal(3, new AvoidEntityGoal<>(creeper, Player.class, (entity) -> entity != null && ModGameRules.KITTY_SLIPPERS_ENABLED.get() && isEquippedBy(entity), 6, 1, 1.3, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test));
        }
    }

    private void onLivingChangeTargetEvent(LivingChangeTargetEvent event, LivingEntity wearer) {
        if (ModGameRules.KITTY_SLIPPERS_ENABLED.get() && event.getEntity() instanceof Mob creeper && creeper.getType().is(ModTags.CREEPERS)) {
            event.setCanceled(true);
        }
    }

    private void onLivingUpdate(LivingEvent.LivingTickEvent event, LivingEntity wearer) {
        if (ModGameRules.KITTY_SLIPPERS_ENABLED.get() && event.getEntity().getType().is(ModTags.CREEPERS)) {
            event.getEntity().setLastHurtByMob(null);
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.CAT_AMBIENT, 1, 1);
    }
}
