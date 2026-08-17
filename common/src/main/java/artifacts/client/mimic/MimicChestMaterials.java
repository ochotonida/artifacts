package artifacts.client.mimic;

import artifacts.Artifacts;
import artifacts.entity.MimicEntity;
import artifacts.integration.ModCompat;
import artifacts.integration.lootr.LootrCompat;
import artifacts.platform.PlatformServices;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MimicChestMaterials {

    public static final List<String> QUARK_WOODEN_CHEST_MATERIALS = Arrays.asList(
            "oak",
            "spruce",
            "birch",
            "cherry",
            "jungle",
            "acacia",
            "dark_oak",
            "warped",
            "crimson",
            "azalea",
            "blossom",
            "mangrove",
            "bamboo"
    );

    private static final Material CHEST_LOOTR = createMaterial(ModCompat.LOOTR, "entity/chest/normal");

    private final boolean isChristmas;
    private final List<Material> moddedChestMaterials = new ArrayList<>();
    private final List<Material> moddedLootrChestMaterials = new ArrayList<>();

    public MimicChestMaterials() {
        Calendar calendar = Calendar.getInstance();
        isChristmas = calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26
                || calendar.get(Calendar.MONTH) == Calendar.APRIL && calendar.get(Calendar.DATE) == 1;
        addQuarkMaterials(moddedChestMaterials, "normal");
        addQuarkMaterials(moddedLootrChestMaterials, "lootr_normal");
    }

    private static Material createMaterial(String modId, String location) {
        ResourceLocation chestAtlas = ResourceLocation.withDefaultNamespace("textures/atlas/chest.png");
        return new Material(chestAtlas, ResourceLocation.fromNamespaceAndPath(modId, location));
    }

    private static void addQuarkMaterials(List<Material> chestMaterials, String chestVariant) {
        if (PlatformServices.getModList().isModLoaded(ModCompat.QUARK)) {
            for (String chestMaterial : QUARK_WOODEN_CHEST_MATERIALS) {
                chestMaterials.add(createMaterial(ModCompat.QUARK, String.format("quark_variant_chests/%s/%s", chestMaterial, chestVariant)));
            }
        }
    }

    public Material getChestSprite(MimicEntity mimic) {
        if (isChristmas) {
            return Sheets.CHEST_XMAS_LOCATION;
        }

        boolean useLootrTextures = PlatformServices.getModList().isModLoaded(ModCompat.LOOTR) && !LootrCompat.useVanillaTextures();
        Material defaultSprite = useLootrTextures ? CHEST_LOOTR : Sheets.CHEST_LOCATION;
        List<Material> moddedSprites = useLootrTextures ? moddedLootrChestMaterials : moddedChestMaterials;

        if (moddedSprites.isEmpty() || !Artifacts.CONFIG.client.useModdedMimicTextures.get()) {
            return defaultSprite;
        }
        return selectRandomSprite(defaultSprite, moddedSprites, mimic);
    }

    private Material selectRandomSprite(Material defaultSprite, List<Material> moddedSprites, MimicEntity mimic) {
        if (mimic.getRandom().nextDouble() < 1D / (moddedSprites.size() + 1)) {
            return defaultSprite;
        }
        return moddedSprites.get((int) (Math.abs(mimic.getUUID().getMostSignificantBits()) % moddedSprites.size()));
    }
}
