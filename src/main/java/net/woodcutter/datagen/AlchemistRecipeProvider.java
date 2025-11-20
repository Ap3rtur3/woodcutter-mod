package net.woodcutter.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.ComplexRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.woodcutter.WoodcutterMod;

import java.util.concurrent.CompletableFuture;

public class AlchemistRecipeProvider extends FabricRecipeProvider {

//    public static final Potion TATER_POTION =
//            Registry.register(
//                    Registries.POTION,
//                    Identifier.of(WoodcutterMod.MOD_ID, "tater"),
//                    new Potion("tater",
//                            new StatusEffectInstance(
//                                    AlchemistRecipeProvider.,
//                                    3600,
//                                    0)));

    public AlchemistRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
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

            }
        };
    }

}