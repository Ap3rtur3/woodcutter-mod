package net.woodcutter.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;

import java.util.concurrent.CompletableFuture;

import static net.woodcutter.ModItems.*;

public class ReinforcedDeepslateRecipeProvider extends FabricRecipeProvider {

    public ReinforcedDeepslateRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "Reinforced Deepslate Recipes";
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                // Smelt: Deepslate => Deepslate Ingot
                CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItem(Blocks.DEEPSLATE), RecipeCategory.MISC, DEEPSLATE_INGOT, 1.0F, 60)
                        .criterion(hasItem(Items.DEEPSLATE), conditionsFromItem(Blocks.DEEPSLATE))
                        .offerTo(recipeExporter);

                // Craft: Sculk + Soul Sand + Gold Ingot => Mended Sculk
                this.createShapeless(RecipeCategory.MISC, MENDED_SCULK)
                        .input(Items.SCULK)
                        .input(Items.SOUL_SAND)
                        .input(Items.GOLD_INGOT)
                        .criterion(hasItem(Items.SCULK), conditionsFromItem(Items.SCULK))
                        .criterion(hasItem(Items.SOUL_SAND), conditionsFromItem(Items.SOUL_SAND))
                        .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                        .offerTo(recipeExporter);

                // Craft: Deepslate Ingot x8 + Mended Sculk => Mended Deepslate
                this.createShaped(RecipeCategory.MISC, MENDED_DEEPSLATE)
                        .input('#', DEEPSLATE_INGOT)
                        .input('m', MENDED_SCULK)
                        .pattern("###")
                        .pattern("#m#")
                        .pattern("###")
                        .criterion(hasItem(DEEPSLATE_INGOT), conditionsFromItem(DEEPSLATE_INGOT))
                        .criterion(hasItem(MENDED_SCULK), conditionsFromItem(MENDED_SCULK))
                        .offerTo(recipeExporter);

                // Blast: Mended Deepslate => Hardened Deepslate
                CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItem(MENDED_DEEPSLATE), RecipeCategory.BUILDING_BLOCKS, HARDENED_DEEPSLATE, 2.0F, 100)
                        .criterion(hasItem(MENDED_DEEPSLATE), conditionsFromItem(MENDED_DEEPSLATE))
                        .offerTo(recipeExporter);

                // Craft: Hardened Deepslate x4 (sides) + Iron Ingot x4 (edges) + Obsidian (middle) => Reinforced Deepslate
                this.createShaped(RecipeCategory.BUILDING_BLOCKS, Blocks.REINFORCED_DEEPSLATE)
                        .input('#', HARDENED_DEEPSLATE)
                        .input('i', Items.IRON_INGOT)
                        .input('o', Blocks.OBSIDIAN)
                        .pattern("i#i")
                        .pattern("#o#")
                        .pattern("i#i")
                        .criterion(hasItem(HARDENED_DEEPSLATE), conditionsFromItem(HARDENED_DEEPSLATE))
                        .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                        .criterion(hasItem(Blocks.OBSIDIAN), conditionsFromItem(Blocks.OBSIDIAN))
                        .offerTo(recipeExporter);
            }
        };
    }
}