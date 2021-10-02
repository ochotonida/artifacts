package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fmllegacy.RegistryObject;

public class ItemConfig {

    private final String itemName;

    public final ForgeConfigSpec.IntValue durability;

    public ItemConfig(ForgeConfigSpec.Builder builder, RegistryObject<?> item, String maxDamageDescription) {
        this(builder, item.getId().getPath(), maxDamageDescription);
    }

    public ItemConfig(ForgeConfigSpec.Builder builder, String itemName, String maxDamageDescription) {
        this.itemName = itemName;
        builder.push(itemName);
        durability = builder
                .worldRestart()
                .comment(maxDamageDescription, "Setting this to 0 will make this item unbreakable")
                .translation(Artifacts.MODID + ".config.server.items.durability")
                .defineInRange("durability", 0, 0, Short.MAX_VALUE);
        addConfigs(builder);
        builder.pop();
    }

    public void bake() {

    }

    public void addConfigs(ForgeConfigSpec.Builder builder) {

    }

    protected String translate(String value) {
        return String.format("%s.config.server.%s.%s", Artifacts.MODID, itemName, value);
    }
}
