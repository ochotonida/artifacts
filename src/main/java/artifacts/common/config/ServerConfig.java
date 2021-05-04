package artifacts.common.config;

import artifacts.Artifacts;
import artifacts.common.config.item.*;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerConfig {

    public Set<Item> cosmetics = Collections.emptySet();

    public final AntidoteVesselConfig antidoteVessel;
    public final BunnyHoppersConfig bunnyHoppers;
    public final CloudInABottleConfig cloudInABottle;
    public final CrossNecklaceConfig crossNecklace;
    public final CrystalHeartConfig crystalHeart;
    public final EverlastingFoodConfig everlastingFood;
    public final FeralClawsConfig feralClaws;

    private final ForgeConfigSpec.ConfigValue<List<String>> cosmeticsValue;

    ServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("items");
        cosmeticsValue = builder
                .worldRestart()
                .comment(
                        "List of cosmetic-only items. All items in this list will have their effects disabled",
                        "To blacklist all items, use \"artifacts:*\"",
                        "Note: blacklisting an item while it is equipped may have unintended side effects",
                        "To completely prevent items from appearing, use a data pack"
                )
                .translation(Artifacts.MODID + ".config.server.cosmetics")
                .define("cosmetics", Lists.newArrayList(""));

        antidoteVessel = new AntidoteVesselConfig(builder);
        bunnyHoppers = new BunnyHoppersConfig(builder);
        cloudInABottle = new CloudInABottleConfig(builder);
        crossNecklace = new CrossNecklaceConfig(builder);
        crystalHeart = new CrystalHeartConfig(builder);
        everlastingFood = new EverlastingFoodConfig(builder);
        feralClaws = new FeralClawsConfig(builder);

        builder.pop();
    }

    public void bake() {
        if (cosmeticsValue.get().contains("artifacts:*")) {
            // noinspection ConstantConditions
            cosmetics = ForgeRegistries.ITEMS.getValues()
                    .stream()
                    .filter(item -> item.getRegistryName().getNamespace().equals(Artifacts.MODID))
                    .collect(Collectors.toSet());
        } else {
            cosmetics = cosmeticsValue.get()
                    .stream()
                    .map(ResourceLocation::new)
                    .filter(registryName -> registryName.getNamespace().equals(Artifacts.MODID))
                    .map(ForgeRegistries.ITEMS::getValue)
                    .collect(Collectors.toSet());
        }

        antidoteVessel.bake();
        bunnyHoppers.bake();
        cloudInABottle.bake();
        crossNecklace.bake();
        crystalHeart.bake();
        everlastingFood.bake();
        feralClaws.bake();
    }

    public boolean isCosmetic(Item item) {
        return cosmetics.contains(item);
    }
}
