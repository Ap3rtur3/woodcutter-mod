package net.woodcutter;

import net.minecraft.util.Identifier;

public class Constants {
    public static final String WOODCUTTER_ID = "woodcutter";

    public static Identifier ofNamespace(String key) {
        return Identifier.of(WOODCUTTER_ID, key);
    }
}
