package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;

public abstract class ItemConfig {

    private final Item item;

    public ItemConfig(ForgeConfigSpec.Builder builder, Item item) {
        this.item = item;
        // noinspection ConstantConditions
        builder.push(item.getRegistryName().getPath());
        addConfigs(builder);
        builder.pop();
    }

    public Item getItem() {
        return item;
    }

    public void bake() {

    }

    public abstract void addConfigs(ForgeConfigSpec.Builder builder);

    protected String translate(String value) {
        // noinspection ConstantConditions
        return Artifacts.MODID + ".config.server." + item.getRegistryName().getPath() + "." + value;
    }
}
