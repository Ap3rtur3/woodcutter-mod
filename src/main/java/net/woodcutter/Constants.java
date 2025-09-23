package net.woodcutter;

import net.minecraft.util.Identifier;

public class Constants {
    public static final String MOD_ID = "woodcutter";

    public static Identifier idOf(String key) {
        return Identifier.of(MOD_ID, key);
    }
}
