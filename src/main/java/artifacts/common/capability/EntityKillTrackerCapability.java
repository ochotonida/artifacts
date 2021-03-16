package artifacts.common.capability;

import artifacts.Artifacts;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
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
        public INBT writeNBT(Capability<IEntityKillTracker> capability, IEntityKillTracker instance, Direction side) {
            ListNBT list = new ListNBT();
            for (EntityType<?> type : instance.getEntityTypes()) {
                // noinspection ConstantConditions
                list.add(StringNBT.valueOf(type.getRegistryName().toString()));
            }
            return list;
        }

        @Override
        public void readNBT(Capability<IEntityKillTracker> capability, IEntityKillTracker instance, Direction side, INBT nbt) {
            instance.clear();
            for (INBT type : (ListNBT) nbt) {
                ResourceLocation entityType = new ResourceLocation(type.getString());
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
            if (event.getObject() instanceof PlayerEntity) {
                EntityKillTrackerProvider provider = new EntityKillTrackerProvider();
                event.addCapability(new ResourceLocation(Artifacts.MODID, "entity_kill_tracker"), provider);
                event.addListener(provider::invalidate);
            }
        }

        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                PlayerEntity player = ((PlayerEntity) event.getSource().getTrueSource());
                player.getCapability(INSTANCE).ifPresent(
                        tracker -> tracker.addEntityTypes(event.getEntityLiving().getType())
                );
            }
        }
    }
}
