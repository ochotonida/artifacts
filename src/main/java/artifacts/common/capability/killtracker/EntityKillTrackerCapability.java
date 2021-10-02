package artifacts.common.capability.killtracker;

import artifacts.Artifacts;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityKillTrackerCapability {

    @CapabilityInject(IEntityKillTracker.class)
    public static Capability<IEntityKillTracker> INSTANCE = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IEntityKillTracker.class, new Storage(), EntityKillTracker::new);
    }

    public static class Storage implements Capability.IStorage<IEntityKillTracker> {

        @Override
        public Tag writeNBT(Capability<IEntityKillTracker> capability, IEntityKillTracker instance, Direction side) {
            ListTag list = new ListTag();
            for (EntityType<?> type : instance.getEntityTypes()) {
                // noinspection ConstantConditions
                list.add(StringTag.valueOf(type.getRegistryName().toString()));
            }
            return list;
        }

        @Override
        public void readNBT(Capability<IEntityKillTracker> capability, IEntityKillTracker instance, Direction side, Tag nbt) {
            instance.clear();
            for (Tag type : (ListTag) nbt) {
                ResourceLocation entityType = new ResourceLocation(type.getAsString());
                if (ForgeRegistries.ENTITIES.containsKey(entityType)) {
                    instance.addEntityType(ForgeRegistries.ENTITIES.getValue(entityType));
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    public static class CapabilityEventHandler {

        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                EntityKillTrackerProvider provider = new EntityKillTrackerProvider();
                event.addCapability(new ResourceLocation(Artifacts.MODID, "entity_kill_tracker"), provider);
                event.addListener(provider::invalidate);
            }
        }

        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            if (event.getSource().getEntity() instanceof Player) {
                Player player = ((Player) event.getSource().getEntity());
                player.getCapability(INSTANCE).ifPresent(
                        tracker -> tracker.addEntityType(event.getEntityLiving().getType())
                );
            }
        }
    }
}
