package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraftforge.common.ForgeConfigSpec;

public abstract class ItemConfig {

    protected String name;

    public ItemConfig(ForgeConfigSpec.Builder builder, String name) {
        this.name = name;
        builder.push(name);
        addConfigs(builder);
        builder.pop();
    }

    public abstract void bake();

    public abstract void addConfigs(ForgeConfigSpec.Builder builder);

    protected String translate(String value) {
        return Artifacts.MODID + ".config.server." + name + "." + value;
    }
}
