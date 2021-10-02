package artifacts.common.item.curio.feet;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.HurtSoundModifyingItem;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class KittySlippersItem extends HurtSoundModifyingItem {

    public KittySlippersItem() {
        super(SoundEvents.CAT_HURT);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
        addListener(LivingSetAttackTargetEvent.class, this::onLivingSetAttackTarget, LivingSetAttackTargetEvent::getTarget);
        addListener(LivingEvent.LivingUpdateEvent.class, this::onLivingUpdate, event -> event.getEntityLiving().getLastHurtByMob());
        addListener(LivingAttackEvent.class, this::onLivingAttack, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        if (event.getEntityLiving() instanceof Creeper) {
            damageEquippedStacks(wearer);
        }
    }

    private void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!ModConfig.server.isCosmetic(this) && event.getEntity() instanceof Creeper) {
            ((Creeper) event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>((Creeper) event.getEntity(), Player.class, (entity) -> entity != null && isEquippedBy(entity), 6, 1, 1.3, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test));
        }
    }

    private void onLivingSetAttackTarget(LivingSetAttackTargetEvent event, LivingEntity wearer) {
        if (event.getEntityLiving() instanceof Creeper) {
            ((Mob) event.getEntityLiving()).setTarget(null);
        }
    }

    private void onLivingUpdate(LivingEvent.LivingUpdateEvent event, LivingEntity wearer) {
        if (event.getEntityLiving() instanceof Creeper) {
            event.getEntityLiving().setLastHurtByMob(null);
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.CAT_AMBIENT, 1, 1);
    }
}
