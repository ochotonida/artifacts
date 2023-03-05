package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.RegistryObject;

public class ItemConfig {

    private final String itemName;

    public ItemConfig(ForgeConfigSpec.Builder builder, RegistryObject<?> item) {
        this(builder, item.getId().getPath());
    }

    public ItemConfig(ForgeConfigSpec.Builder builder, String itemName) {
        this.itemName = itemName;
        builder.push(itemName);
        addConfigs(builder);
        builder.pop();
    }

    public void addConfigs(ForgeConfigSpec.Builder builder) {

    }

    protected String translate(String value) {
        return String.format("%s.config.server.%s.%s", Artifacts.MODID, itemName, value);
    }
}
