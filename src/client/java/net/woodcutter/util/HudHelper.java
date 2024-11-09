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
    
    public static void drawTextOnPosition(DrawContext drawContext, Text text, WoodcutterConfig.Position position, int margin, float scale, int color, boolean shadow) {
        MinecraftClient client = MinecraftClient.getInstance();
        withMatrixStack(drawContext, (matrixStack) -> {
            matrixStack.scale(scale, scale, scale);
            float invFontScale = 1F / scale;
            int screenWidth = drawContext.getScaledWindowWidth();
            int screenHeight = drawContext.getScaledWindowHeight();
            int textWidth = client.textRenderer.getWidth(text);
            int textHeight = client.textRenderer.getWrappedLinesHeight(text, textWidth);
            int x = isPositionedLeft(position) ? margin : (int) ((screenWidth - textWidth) * invFontScale) - margin;
            int y = isPositionedTop(position) ? margin : (int) ((screenHeight - textHeight) * invFontScale) - margin;
            drawContext.drawText(client.textRenderer, text, x, y, color, shadow);
        });
    }

    public static void drawScaledTextFromOrigin(DrawContext drawContext, Text text, WoodcutterConfig.Position origin, float x, float y, int margin, float scale, int color, boolean shadow) {
        MinecraftClient client = MinecraftClient.getInstance();
        withMatrixStack(drawContext, (matrixStack) -> {
            matrixStack.scale(scale, scale, scale);
            float invFontScale = 1F / scale;
            float screenWidth = drawContext.getScaledWindowWidth() * invFontScale;
            float screenHeight = drawContext.getScaledWindowHeight() * invFontScale;
            float textWidth = client.textRenderer.getWidth(text) * invFontScale;
            float textHeight = client.textRenderer.getWrappedLinesHeight(text, (int) textWidth) * invFontScale;
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
