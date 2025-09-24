package net.woodcutter.generators.woodcutter;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {

    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "Woodcutter Recipes";
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                var woodcuttingRecipes = new ArrayList<RecipePair>();
                try {
                    woodcuttingRecipes.addAll(generateWoodcutterRecipePairsFrom("ACACIA"));
                    woodcuttingRecipes.addAll(generateWoodcutterRecipePairsFrom("BIRCH"));
                    woodcuttingRecipes.addAll(generateWoodcutterRecipePairsFrom("CHERRY"));
                    woodcuttingRecipes.addAll(generateWoodcutterRecipePairsFrom("CRIMSON"));
                    woodcuttingRecipes.addAll(generateWoodcutterRecipePairsFrom("DARK_OAK"));
                    woodcuttingRecipes.addAll(generateWoodcutterRecipePairsFrom("JUNGLE"));
                    woodcuttingRecipes.addAll(generateWoodcutterRecipePairsFrom("MANGROVE"));
                    woodcuttingRecipes.addAll(generateWoodcutterRecipePairsFrom("OAK"));
                    woodcuttingRecipes.addAll(generateWoodcutterRecipePairsFrom("PALE_OAK"));
                    woodcuttingRecipes.addAll(generateWoodcutterRecipePairsFrom("SPRUCE"));
                    woodcuttingRecipes.addAll(generateWoodcutterRecipePairsFrom("WARPED"));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    LOGGER.error("Something bad happened...");
                    throw new RuntimeException(e);
                }
                generateStonecuttingRecipes(woodcuttingRecipes);
            }

            private void generateStonecuttingRecipes(List<RecipePair> recipes) {
                recipes.forEach(recipePair -> StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItem(recipePair.input), RecipeCategory.BUILDING_BLOCKS, recipePair.output, recipePair.count)
                        .criterion("has_input_block", conditionsFromItem(recipePair.input))
                        .offerTo(recipeExporter, recipePair.recipePath()));
            }
        };
    }

    private List<RecipePair> generateWoodcutterRecipePairsFrom(String name) throws NoSuchFieldException, IllegalAccessException {
        var logName = List.of("CRIMSON", "WARPED").contains(name) ? name + "_STEM" : name + "_LOG";
        var log = (ItemConvertible) Blocks.class.getDeclaredField(logName).get(Blocks.class);
        var stripped = (ItemConvertible) Blocks.class.getDeclaredField("STRIPPED_" + logName).get(Blocks.class);
        var planks = (ItemConvertible) Blocks.class.getDeclaredField(name + "_PLANKS").get(Blocks.class);
        var stairs = (ItemConvertible) Blocks.class.getDeclaredField(name + "_STAIRS").get(Blocks.class);
        var slab = (ItemConvertible) Blocks.class.getDeclaredField(name + "_SLAB").get(Blocks.class);
        var door = (ItemConvertible) Blocks.class.getDeclaredField(name + "_DOOR").get(Blocks.class);
        var trapdoor = (ItemConvertible) Blocks.class.getDeclaredField(name + "_TRAPDOOR").get(Blocks.class);
        return generateWoodcutterRecipePairs(log, stripped, planks, stairs, slab, door, trapdoor);
    }

    private List<RecipePair> generateWoodcutterRecipePairs(ItemConvertible log, ItemConvertible stripped, ItemConvertible planks, ItemConvertible stairs, ItemConvertible slab, ItemConvertible door, ItemConvertible trapdoor) {
        return List.of(
                RecipePair.of(log, stripped),
                RecipePair.of(log, planks, 4),
                RecipePair.of(log, door, 3),
                RecipePair.of(log, trapdoor, 3),
                RecipePair.of(stripped, planks, 4),
                RecipePair.of(planks, stairs),
                RecipePair.of(planks, slab, 2),
                RecipePair.of(planks, Items.STICK, 2),
                RecipePair.of(slab, Items.STICK)
        );
    }

    static class RecipePair {
        ItemConvertible input;
        ItemConvertible output;
        int count;

        public RecipePair(ItemConvertible input, ItemConvertible output, int count) {
            this.input = input;
            this.output = output;
            this.count = count;
        }

        public static RecipePair of(ItemConvertible input, ItemConvertible output) {
            return of(input, output, 1);
        }

        public static RecipePair of(ItemConvertible input, ItemConvertible output, int count) {
            return new RecipePair(input, output, count);
        }

        public String recipePath() {
            return String.format("%s_to_%s_from_stone_cutting", input.asItem().getTranslationKey(), output.asItem().getTranslationKey());
        }
    }
}