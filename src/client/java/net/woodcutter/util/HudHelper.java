package net.woodcutter.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.woodcutter.config.WoodcutterConfig;

public class HudHelper {

    public static void withMatrixStack(DrawContext drawContext, MatrixStackCallback callback) {
        var matrixStack = drawContext.getMatrices();
        matrixStack.push();
        callback.run(matrixStack);
        matrixStack.pop();
    }

    /**
     * Draws text on screen with relative x & y values ranging from 0.0 to 1.0
     *
     * @param drawContext Minecraft DrawContext
     * @param text Text to draw
     * @param position Decides in where text is drawn (e.g. in which screen corner)
     * @param margin Margin in pixels
     * @param scale Font scaling factor
     * @param color Color in hex 0xAARRGGBB
     * @param shadow Draw text with shadows
     */
    public static void drawTextOnPosition(DrawContext drawContext, Text text, WoodcutterConfig.Position position, int margin, float scale, int color, boolean shadow) {
        MinecraftClient client = MinecraftClient.getInstance();
        withMatrixStack(drawContext, (matrixStack) -> {
            matrixStack.scale(scale, scale, scale);
            float invFontScale = 1F / scale;
            int screenWidth = drawContext.getScaledWindowWidth();
            int screenHeight = drawContext.getScaledWindowHeight();
            int textWidth = client.textRenderer.getWidth(text);
            int textHeight = client.textRenderer.getWrappedLinesHeight(text, textWidth);
            int x = isPositionedLeft(position) ? margin : (int) (screenWidth * invFontScale - textWidth) - margin;
            int y = isPositionedTop(position) ? margin : (int) (screenHeight  * invFontScale - textHeight) - margin;
            drawContext.drawText(client.textRenderer, text, x, y, color, shadow);
        });
    }


    /**
     * Draws text on screen with relative x & y values ranging from 0.0 to 1.0
     *
     * @param drawContext Minecraft DrawContext
     * @param text Text to draw
     * @param origin Decides in which direction text is drawn
     * @param x Relative position on screen: 0.0 = left, 1.0 = right
     * @param y Relative position on screen: 0.0 = top, 1.0 = bottom
     * @param margin Margin in pixels
     * @param scale Font scaling factor
     * @param color Color in hex 0xAARRGGBB
     * @param shadow Draw text with shadows
     */
    public static void drawScaledTextFromOrigin(DrawContext drawContext, Text text, WoodcutterConfig.Position origin, float x, float y, int margin, float scale, int color, boolean shadow) {
        MinecraftClient client = MinecraftClient.getInstance();
        withMatrixStack(drawContext, (matrixStack) -> {
            matrixStack.scale(scale, scale, scale);
            float invFontScale = 1F / scale;
            float screenWidth = drawContext.getScaledWindowWidth() * invFontScale;
            float screenHeight = drawContext.getScaledWindowHeight() * invFontScale;
            int textWidth = client.textRenderer.getWidth(text);
            int textHeight = client.textRenderer.getWrappedLinesHeight(text, textWidth);
            float xPos = x * screenWidth;
            float yPos = y * screenHeight;
            int i = isPositionedLeft(origin)
                    ? (int) (xPos) + margin
                    : (int) (screenWidth - xPos - textWidth) - margin;
            int j = isPositionedTop(origin)
                    ? (int) (yPos) + margin
                    : (int) (screenHeight - yPos - textHeight) - margin;
            drawContext.drawText(client.textRenderer, text, i, j, color, shadow);
        });
    }

    public static void drawScaledText(DrawContext drawContext, Text text, float x, float y, int margin, float scale, int color, boolean shadow) {
        drawScaledTextFromOrigin(drawContext, text, WoodcutterConfig.Position.TOP_LEFT, x, y, margin, scale, color, shadow);
    }

    private static boolean isPositionedLeft(WoodcutterConfig.Position position) {
        return WoodcutterConfig.Position.TOP_LEFT.equals(position) || WoodcutterConfig.Position.BOTTOM_LEFT.equals(position);
    }

    private static boolean isPositionedTop(WoodcutterConfig.Position position) {
        return WoodcutterConfig.Position.TOP_LEFT.equals(position) || WoodcutterConfig.Position.TOP_RIGHT.equals(position);
    }

    public interface MatrixStackCallback {
        void run(MatrixStack matrixStack);
    }

}
