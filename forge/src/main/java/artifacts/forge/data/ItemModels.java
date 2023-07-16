package artifacts.forge.data;

import artifacts.forge.ArtifactsForge;
import artifacts.forge.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemModels extends ItemModelProvider {

    public ItemModels(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, ArtifactsForge.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // noinspection ConstantConditions
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(item -> ForgeRegistries.ITEMS.getKey(item).getNamespace().equals(ArtifactsForge.MOD_ID))
                .filter(item -> item != ModItems.MIMIC_SPAWN_EGG.get())
                .filter(item -> item != ModItems.UMBRELLA.get())
                .forEach(this::addGeneratedModel);
    }

    private void addGeneratedModel(Item item) {
        // noinspection ConstantConditions
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();
        withExistingParent("item/" + name, "item/generated").texture("layer0", ArtifactsForge.id("item/%s", name));
    }
}
