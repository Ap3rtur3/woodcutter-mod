package net.woodcutter;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public class ModItems {

    public static void initialize() {
        // Needs to be called on mod initialization, so items get registered before registries freeze.
    }

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Constants.idOf(name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static final Item DEEPSLATE_INGOT = register("deepslate_ingot", Item::new, new Item.Settings());
    public static final Item MENDED_SCULK = register("mended_sculk", Item::new, new Item.Settings());
    public static final Item MENDED_DEEPSLATE = Items.register(ModBlocks.MENDED_DEEPSLATE);
    public static final Item HARDENED_DEEPSLATE = Items.register(ModBlocks.HARDENED_DEEPSLATE);

}
