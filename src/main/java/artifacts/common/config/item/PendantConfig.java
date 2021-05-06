package artifacts.common.config.item;

import artifacts.Artifacts;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;

public abstract class PendantConfig extends ItemConfig {

    public ForgeConfigSpec.DoubleValue strikeChance;

    public PendantConfig(ForgeConfigSpec.Builder builder, Item item) {
        super(builder, item);
    }

    @Override
    public void addConfigs(ForgeConfigSpec.Builder builder) {
        strikeChance = builder
                .comment("Chance for the pendant to strike an attacker when the wearer is attacked")
                .translation(Artifacts.MODID + ".config.server.pendant.strike_chance")
                .defineInRange("strike_chance", getDefaultStrikeChance(), 0, 1);
    }

    protected abstract double getDefaultStrikeChance();
}
