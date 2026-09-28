package artifacts.neoforge.registry;

import artifacts.component.SwimData;
import artifacts.registry.Register;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachmentTypes {

    public static final Register<AttachmentType<?>> ATTACHMENT_TYPES = Register.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES);

    public static final Supplier<AttachmentType<SwimData>> SWIM_DATA = ATTACHMENT_TYPES.register("swim_data", () ->
            AttachmentType.builder(SwimData::new).serialize(SwimData.MAP_CODEC).build()
    );
}
