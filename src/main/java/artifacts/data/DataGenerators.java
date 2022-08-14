package artifacts.data;

import artifacts.Artifacts;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Artifacts.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        BlockTags blockTags = new BlockTags(generator, existingFileHelper);
        LootModifiers lootModifiers = new LootModifiers(generator);

        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ItemTags(generator, blockTags, existingFileHelper));
        generator.addProvider(event.includeServer(), lootModifiers);
        generator.addProvider(event.includeServer(), new LootTables(generator, existingFileHelper, lootModifiers));
        generator.addProvider(event.includeServer(), new EntityTypeTags(generator, existingFileHelper));
        generator.addProvider(event.includeServer(), new MobEffectTags(generator, existingFileHelper));

        generator.addProvider(event.includeClient(), new ItemModels(generator, existingFileHelper));
    }
}
