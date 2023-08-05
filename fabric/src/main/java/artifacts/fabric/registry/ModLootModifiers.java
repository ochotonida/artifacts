package artifacts.fabric.registry;

import artifacts.Artifacts;
import artifacts.fabric.loot.RollLootTableModifier;
import io.github.fabricators_of_create.porting_lib.loot.PortingLibLoot;
import net.minecraft.core.Registry;

public class ModLootModifiers {

    public static void register() {
        Registry.register(PortingLibLoot.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get(), Artifacts.id("roll_loot_table"), RollLootTableModifier.CODEC.get());
    }
}
