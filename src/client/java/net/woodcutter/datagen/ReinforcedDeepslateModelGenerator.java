package net.woodcutter.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.woodcutter.ModBlocks;
import net.woodcutter.ModItems;

public class ReinforcedDeepslateModelGenerator extends FabricModelProvider {

    public ReinforcedDeepslateModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "Reinforced Deepslate Models";
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.HARDENED_DEEPSLATE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MENDED_DEEPSLATE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.DEEPSLATE_INGOT);
        itemModelGenerator.register(ModItems.MENDED_SCULK);
    }

}