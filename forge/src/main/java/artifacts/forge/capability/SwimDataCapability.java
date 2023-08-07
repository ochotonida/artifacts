package artifacts.forge.capability;

import artifacts.Artifacts;
import artifacts.component.SwimData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class SwimDataCapability {

    public static final Capability<SwimData> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    public static void setup() {
        MinecraftForge.EVENT_BUS.addListener(SwimDataCapability::onRegisterCapabilities);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, SwimDataCapability::onAttachCapabilities);
    }

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(SwimData.class);
    }

    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            SwimDataProvider provider = new SwimDataProvider();
            event.addCapability(Artifacts.id("swim_handler"), provider);
        }
    }
}
