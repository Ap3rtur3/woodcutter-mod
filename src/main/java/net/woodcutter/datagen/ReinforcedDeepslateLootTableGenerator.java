package net.woodcutter.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import net.woodcutter.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ReinforcedDeepslateLootTableGenerator extends FabricBlockLootTableProvider {

    protected ReinforcedDeepslateLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.MENDED_DEEPSLATE);
        addDrop(ModBlocks.HARDENED_DEEPSLATE);
    }

}