package net.woodcutter.hud;

import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.DropdownBoxEntry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.woodcutter.Constants;
import net.woodcutter.WoodcutterMod;
import net.woodcutter.config.WoodcutterConfig;
import net.woodcutter.util.HudHelper;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.List;

import static net.woodcutter.WoodcutterModClient.getConfig;

public class CoordsHud {
    public static final int TEXT_MARGIN = 1;
    public static final float FONT_SCALE_MIN = .6F;
    public static final float FONT_SCALE_STEP = .2F;
    private static final Logger log = WoodcutterMod.logger("coords-hud");

    public static void register(GuiRegistry guiRegistry) {
        // Transforms position field to enum list, this fixes translations in button
        guiRegistry.registerPredicateTransformer(
                (guis, i18n, field, config, defaults, registry) -> CoordsHud.dropdownToEnumList(guis, field), 
                field -> field.getName().equals("position"));

        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Constants.idOf("coords-hud"), (drawContext, renderTickCounter) -> {
            WoodcutterConfig config = getConfig(); // maybe create reference?
            if (!config.coordsHud.enabled) {
                return;
            }
            var player = MinecraftClient.getInstance().player;
            if (player == null) {
                return;
            }

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
                                field.set(getConfig().coordsHud, p);
                            } catch (IllegalAccessException e) {
                                log.error("Could not set position in cords hud", e);
                            }
                        })
                        .setEnumNameProvider(perspective -> switch ((WoodcutterConfig.Position) perspective) {
                            case TOP_LEFT -> Text.translatable("text.autoconfig.woodcutter.option.coordsHud.position.TOP_LEFT");
                            case TOP_RIGHT -> Text.translatable("text.autoconfig.woodcutter.option.coordsHud.position.TOP_RIGHT");
                            case BOTTOM_LEFT -> Text.translatable("text.autoconfig.woodcutter.option.coordsHud.position.BOTTOM_LEFT");
                            case BOTTOM_RIGHT -> Text.translatable("text.autoconfig.woodcutter.option.coordsHud.position.BOTTOM_RIGHT");
                        })
                        .build())
                .map(AbstractConfigListEntry.class::cast)
                .toList();
    }

}
