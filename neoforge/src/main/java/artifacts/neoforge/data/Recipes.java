package artifacts.neoforge.data;

import artifacts.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class Recipes extends RecipeProvider {

    protected Recipes(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        cookingRecipes(ModItems.EVERLASTING_BEEF.value(), ModItems.ETERNAL_STEAK.value());
    }

    private void cookingRecipes(Item ingredient, Item result) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD, result, 0.1F, 200)
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

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput outpu) {
            return new Recipes(registries, outpu);
        }

        @Override
        public String getName() {
            return "Artifacts Recipes";
        }
    }
}
