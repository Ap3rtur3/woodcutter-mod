package net.woodcutter.generators;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.woodcutter.generators.woodcutter.RecipeProvider;

public class EntryPoint implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		// TODO: config for opt-in
		fabricDataGenerator.createPack().addProvider(RecipeProvider::new);
		fabricDataGenerator.createPack().addProvider(net.woodcutter.generators.reinforced_deepslate.RecipeProvider::new);
	}
}
