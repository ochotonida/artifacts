package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;

public class ItemConfig {

    private final Item item;

    public final ForgeConfigSpec.IntValue durability;

    public ItemConfig(ForgeConfigSpec.Builder builder, Item item, String maxDamageComment) {
        this.item = item;
        // noinspection ConstantConditions
        builder.push(item.getRegistryName().getPath());
        durability = builder
                .worldRestart()
                .comment(maxDamageComment, "Setting this to 0 will make this item unbreakable")
                .translation(Artifacts.MODID + ".config.server.items.durability")
                .defineInRange("durability", 0, 0, Short.MAX_VALUE);
        addConfigs(builder);
        builder.pop();
    }

    public Item getItem() {
        return item;
    }

    public void bake() {

    }

    public void addConfigs(ForgeConfigSpec.Builder builder) {

    }

    protected String translate(String value) {
        // noinspection ConstantConditions
        return Artifacts.MODID + ".config.server." + item.getRegistryName().getPath() + "." + value;
    }
}
