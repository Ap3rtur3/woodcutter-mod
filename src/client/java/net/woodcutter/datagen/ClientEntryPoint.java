package net.woodcutter.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ClientEntryPoint implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        // TODO: config for opt-in
        pack.addProvider(ReinforcedDeepslateModelGenerator::new);
        pack.addProvider(ReinforcedDeepslateRecipeProvider::new);
        pack.addProvider(WoodcutterRecipeProvider::new);
        pack.addProvider(LootTableGenerator::new);
    }
}