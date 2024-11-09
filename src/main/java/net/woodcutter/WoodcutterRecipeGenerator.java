package net.woodcutter;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

class WoodcutterRecipeGenerator extends FabricRecipeProvider {

    WoodcutterRecipeGenerator(FabricDataOutput generator, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(generator, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        var stonecuttingRecipes = List.of(
                // acacia
                RecipePair.of(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG),
                RecipePair.of(Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS, 4),
                RecipePair.of(Blocks.ACACIA_PLANKS, Blocks.ACACIA_STAIRS),
                RecipePair.of(Blocks.ACACIA_PLANKS, Blocks.ACACIA_SLAB, 2),
                // bamboo
                RecipePair.of(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK),
                RecipePair.of(Blocks.BAMBOO_BLOCK, Blocks.BAMBOO_PLANKS, 4),
                RecipePair.of(Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_STAIRS),
                RecipePair.of(Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_SLAB, 2),
                // birch
                RecipePair.of(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG),
                RecipePair.of(Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS, 4),
                RecipePair.of(Blocks.BIRCH_PLANKS, Blocks.BIRCH_STAIRS),
                RecipePair.of(Blocks.BIRCH_PLANKS, Blocks.BIRCH_SLAB, 2),
                // cherry
                RecipePair.of(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG),
                RecipePair.of(Blocks.CHERRY_LOG, Blocks.CHERRY_PLANKS, 4),
                RecipePair.of(Blocks.CHERRY_PLANKS, Blocks.CHERRY_STAIRS),
                RecipePair.of(Blocks.CHERRY_PLANKS, Blocks.CHERRY_SLAB, 2),
                // crimson
                RecipePair.of(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM),
                RecipePair.of(Blocks.CRIMSON_STEM, Blocks.CRIMSON_PLANKS, 4),
                RecipePair.of(Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_STAIRS),
                RecipePair.of(Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_SLAB, 2),
                // dark oak
                RecipePair.of(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG),
                RecipePair.of(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, 4),
                RecipePair.of(Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_STAIRS),
                RecipePair.of(Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_SLAB, 2),
                // jungle
                RecipePair.of(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG),
                RecipePair.of(Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS, 4),
                RecipePair.of(Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_STAIRS),
                RecipePair.of(Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_SLAB, 2),
                // mangrove
                RecipePair.of(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG),
                RecipePair.of(Blocks.MANGROVE_LOG, Blocks.MANGROVE_PLANKS, 4),
                RecipePair.of(Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_STAIRS),
                RecipePair.of(Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_SLAB, 2),
                // oak
                RecipePair.of(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG),
                RecipePair.of(Blocks.OAK_LOG, Blocks.OAK_PLANKS, 4),
                RecipePair.of(Blocks.OAK_PLANKS, Blocks.OAK_STAIRS),
                RecipePair.of(Blocks.OAK_PLANKS, Blocks.OAK_SLAB, 2),
                // spruce
                RecipePair.of(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG),
                RecipePair.of(Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS, 4),
                RecipePair.of(Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_STAIRS),
                RecipePair.of(Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_SLAB, 2),
                // warped
                RecipePair.of(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM),
                RecipePair.of(Blocks.WARPED_STEM, Blocks.WARPED_PLANKS, 4),
                RecipePair.of(Blocks.WARPED_PLANKS, Blocks.WARPED_STAIRS),
                RecipePair.of(Blocks.WARPED_PLANKS, Blocks.WARPED_SLAB, 2)
        );
        stonecuttingRecipes.forEach(recipePair -> {
            StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(recipePair.input), RecipeCategory.BUILDING_BLOCKS, recipePair.output, recipePair.count)
                    .criterion("has_input_block", conditionsFromItem(recipePair.input))
                    .offerTo(recipeExporter, String.format("%s_to_%s_from_stone_cutting", recipePair.input.getTranslationKey(), recipePair.output.getTranslationKey()));
        });
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