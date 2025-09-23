package net.woodcutter.generators;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class EntryPoint implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		// TODO: config for opt-in
		fabricDataGenerator.createPack().addProvider(WoodcutterRecipeGenerator::new);
		fabricDataGenerator.createPack().addProvider(ReinforcedDeepslateRecipeGenerator::new);
	}
}
