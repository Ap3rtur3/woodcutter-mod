package net.woodcutter;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WoodcutterRecipeGenerator extends FabricRecipeProvider {

    WoodcutterRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                var stonecuttingRecipes = new ArrayList<RecipePair>();
                stonecuttingRecipes.addAll(generateWoodcutterRecipePairs(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_PLANKS, Blocks.ACACIA_STAIRS, Blocks.ACACIA_SLAB));
                stonecuttingRecipes.addAll(generateWoodcutterRecipePairs(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK, Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_STAIRS, Blocks.BAMBOO_SLAB));
                stonecuttingRecipes.addAll(generateWoodcutterRecipePairs(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_PLANKS, Blocks.BIRCH_STAIRS, Blocks.BIRCH_SLAB));
                stonecuttingRecipes.addAll(generateWoodcutterRecipePairs(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG, Blocks.CHERRY_PLANKS, Blocks.CHERRY_STAIRS, Blocks.CHERRY_SLAB));
                stonecuttingRecipes.addAll(generateWoodcutterRecipePairs(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM, Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_STAIRS, Blocks.CRIMSON_SLAB));
                stonecuttingRecipes.addAll(generateWoodcutterRecipePairs(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_STAIRS, Blocks.DARK_OAK_SLAB));
                stonecuttingRecipes.addAll(generateWoodcutterRecipePairs(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_STAIRS, Blocks.JUNGLE_SLAB));
                stonecuttingRecipes.addAll(generateWoodcutterRecipePairs(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG, Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_STAIRS, Blocks.MANGROVE_SLAB));
                stonecuttingRecipes.addAll(generateWoodcutterRecipePairs(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_PLANKS, Blocks.OAK_STAIRS, Blocks.OAK_SLAB));
                stonecuttingRecipes.addAll(generateWoodcutterRecipePairs(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_STAIRS, Blocks.SPRUCE_SLAB));
                stonecuttingRecipes.addAll(generateWoodcutterRecipePairs(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM, Blocks.WARPED_PLANKS, Blocks.WARPED_STAIRS, Blocks.WARPED_SLAB));
                stonecuttingRecipes.forEach(recipePair -> {
                    StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(recipePair.input), RecipeCategory.BUILDING_BLOCKS, recipePair.output, recipePair.count)
                            .criterion("has_input_block", conditionsFromItem(recipePair.input))
                            .offerTo(recipeExporter, String.format("%s_to_%s_from_stone_cutting", recipePair.input.getTranslationKey(), recipePair.output.getTranslationKey()));
                });
            }
        };
    }

    @Override
    public String getName() {
        return "Woodcutter Recipes";
    }

    private List<RecipePair> generateWoodcutterRecipePairs(Block log, Block stripped, Block planks, Block stairs, Block slab) {
        return List.of(
                RecipePair.of(log, stripped),
                RecipePair.of(log, planks, 4),
                RecipePair.of(planks, stairs),
                RecipePair.of(planks, slab, 2));
    }

    static class RecipePair {
        Block input;
        Block output;
        int count;

        public RecipePair(Block input, Block output, int count) {
            this.input = input;
            this.output = output;
            this.count = count;
        }

        public static RecipePair of(Block input, Block output) {
            return of(input, output, 1);
        }

        public static RecipePair of(Block input, Block output, int count) {
            return new RecipePair(input, output, count);
        }
    }
}