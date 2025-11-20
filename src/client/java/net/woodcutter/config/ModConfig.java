package net.woodcutter.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.util.Colors;
import net.woodcutter.WoodcutterMod;
import org.lwjgl.glfw.GLFW;

@Config(name = WoodcutterMod.MOD_ID)
@Config.Gui.Background("cloth-config2:transparent")
public class ModConfig implements ConfigData {

    private ModConfig() {
        // has to be here for some reason
    }

    public enum Position {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    public static class CoordsHud {
        public boolean enabled = true;

        @ConfigEntry.BoundedDiscrete(min = 1, max = 4)
        public int fontSize = 2;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.ColorPicker
        public int fontColor = Colors.WHITE;

        //@ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public Position position = Position.TOP_LEFT;
    }

    public static class RulerHud {
        public boolean enabled = false;

        // TODO
        public int keyBinding = GLFW.GLFW_KEY_PERIOD;
    }

    public static class Alchemist {
        public boolean enabled = true;


    }

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public CoordsHud coordsHud = new CoordsHud();

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public RulerHud rulerHud = new RulerHud();
}