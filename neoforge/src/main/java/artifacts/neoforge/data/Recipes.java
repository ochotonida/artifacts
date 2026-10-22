package artifacts.neoforge.data;

import artifacts.registry.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

import java.util.Set;

public class Recipes extends RecipeProvider {

    public Recipes(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    protected void buildRecipes() {
        cookingRecipes(ModItems.EVERLASTING_BEEF.value(), ModItems.ETERNAL_STEAK.value());
    }

    private void cookingRecipes(Item ingredient, Item result) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD, CookingBookCategory.FOOD, result, 0.1F, 200)
                .unlockedBy(getHasName(ingredient), this.has(ingredient))
                .save(this.output, getDefaultRecipeId(result) + "_from_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, 0.35F, 100)
                .unlockedBy(getHasName(ingredient), this.has(ingredient))
                .save(this.output, getDefaultRecipeId(result) + "_from_smoking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, 0.35F, 600)
                .unlockedBy(getHasName(ingredient), this.has(ingredient))
                .save(this.output, getDefaultRecipeId(result) + "_from_campfire_cooking");
    }

    static Identifier getDefaultRecipeId(ItemLike output) {
        return BuiltInRegistries.ITEM.getKey(output.asItem());
    }

    public static MultiRegistryBootstrap create() {
        return new MultiRegistryBootstrap() {
            @Override
            public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
                // Return the registries we are adding entries to.
                return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
            }

            @Override
            public void run(MultiRegistryBootstrap.BootstrapGetter registries) {
                // Run the recipe provider.
                new Recipes(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT)).buildRecipes();
            }
        };
    }
}
