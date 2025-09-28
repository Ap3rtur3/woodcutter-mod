package net.woodcutter;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

import static net.woodcutter.Constants.idOf;

public class ModBlocks {

    public static final Block MENDED_DEEPSLATE = Blocks.register(
            RegistryKey.of(RegistryKeys.BLOCK, idOf("mended_deepslate")),
            Block::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DEEPSLATE_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool()
                    .strength(1.0F, 60.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE)
    );

    public static final Block HARDENED_DEEPSLATE = Blocks.register(
            RegistryKey.of(RegistryKeys.BLOCK, idOf("hardened_deepslate")),
            Block::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DEEPSLATE_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool()
                    .strength(2.5F, 1200.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE)
    );

}
