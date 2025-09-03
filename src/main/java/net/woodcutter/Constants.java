package net.woodcutter;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class Constants {
    public static final String WOODCUTTER_ID = "woodcutter";

    public static Identifier idOf(String key) {
        return Identifier.of(WOODCUTTER_ID, key);
    }

    private static RegistryKey<Block> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla(id));
    }
}
