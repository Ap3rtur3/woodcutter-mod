package net.woodcutter.hud;

import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.DropdownBoxEntry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.woodcutter.config.WoodcutterConfig;
import net.woodcutter.util.HudHelper;

import java.lang.reflect.Field;
import java.util.List;

import static net.woodcutter.WoodcutterModClient.getConfig;

public class CoordsHud {
    public static final int TEXT_MARGIN = 1;
    public static final float FONT_SCALE_MIN = .6F;
    public static final float FONT_SCALE_STEP = .2F;

    public static void register(GuiRegistry guiRegistry) {
        guiRegistry.registerPredicateTransformer(
                (guis, i18n, field, config, defaults, registry) -> CoordsHud.dropdownToEnumList(guis, field), 
                field -> field.getName().equals("position"));

        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            WoodcutterConfig config = getConfig();
            if (!config.coordsHud.enabled) {
                return;
            }
            var client = MinecraftClient.getInstance();
            var player  = client.player;
            assert player != null;

            int x = (int) player.getX();
            int y = (int) player.getY();
            int z = (int) player.getZ();
            String coords = String.format("%s %s %s", x, y, z);
            boolean funnyNumber = List.of(Math.abs(x), Math.abs(y), Math.abs(z)).contains(420);
            String coordsText = funnyNumber ? String.format("%s :D", coords) : coords;
            float fontScale = FONT_SCALE_MIN + FONT_SCALE_STEP * (config.coordsHud.fontSize - 1); // 0.6 -> 0.8 -> 1.0 -> 1.2
            HudHelper.drawTextOnPosition(drawContext, Text.literal(coordsText), config.coordsHud.position, TEXT_MARGIN, fontScale, config.coordsHud.fontColor, true);
        });
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static List<AbstractConfigListEntry> dropdownToEnumList(List<AbstractConfigListEntry> guis, Field field) {
        return guis.stream()
                .filter(DropdownBoxEntry.class::isInstance)
                .map(DropdownBoxEntry.class::cast)
                .map(dropdown -> ConfigEntryBuilder.create()
                        .startEnumSelector(dropdown.getFieldName(), WoodcutterConfig.Position.class, (WoodcutterConfig.Position) dropdown.getValue())
                        .setDefaultValue((WoodcutterConfig.Position) dropdown.getDefaultValue().orElse(WoodcutterConfig.Position.TOP_LEFT))
                        .setSaveConsumer(p -> {
                            try {
                                field.set(getConfig(), p);
                            } catch (IllegalAccessException ignored) { }
                        })
                        .setEnumNameProvider(perspective -> switch ((WoodcutterConfig.Position) perspective) {
                            case TOP_LEFT -> Text.translatable("text.autoconfig.woodcutter-mod.option.position.TOP_LEFT");
                            case TOP_RIGHT -> Text.translatable("text.autoconfig.woodcutter-mod.option.position.TOP_RIGHT");
                            case BOTTOM_LEFT -> Text.translatable("text.autoconfig.woodcutter-mod.option.position.BOTTOM_LEFT");
                            case BOTTOM_RIGHT -> Text.translatable("text.autoconfig.woodcutter-mod.option.position.BOTTOM_RIGHT");
                        })
                        .build())
                .map(AbstractConfigListEntry.class::cast)
                .toList();
    }

}
