//package net.woodcutter.hud;
//
//import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
//import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.option.KeyBinding;
//import net.minecraft.client.util.InputUtil;
//import net.minecraft.text.Text;
//import net.minecraft.util.hit.BlockHitResult;
//import net.minecraft.util.hit.HitResult;
//import net.minecraft.util.math.BlockPos;
//import net.woodcutter.config.ModConfig;
//import net.woodcutter.util.HudHelper;
//import org.lwjgl.glfw.GLFW;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Objects;
//import java.util.Optional;
//
//import static net.woodcutter.WoodcutterMod.MOD_ID;
//import static net.woodcutter.WoodcutterModClient.getConfig;
//
//public class RulerHud {
//
//    private static final Logger log = LoggerFactory.getLogger(MOD_ID);
//    private static final MarkedBlocks markedBlocks = new MarkedBlocks();
//    private static KeyBinding keyBinding = null;
//
//    public static void register() {
//        registerKeybinding();
//
//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            while (keyBinding.wasPressed()) {
//                assert client.player != null;
//                markedBlocks.addMarker();
//            }
//        });
//
//        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
//            if (!isEnabled()) {
//                return;
//            }
//            ModConfig config = getConfig();
//            markLookedAtBlock();
//            if (markedBlocks.hasBaseAndHead()) {
//                String str = String.format("%s -> %s | %s", markedBlocks.getStart().toShortString(), markedBlocks.getEnd().toShortString(), (int) markedBlocks.distance());
//                HudHelper.drawScaledTextFromOrigin(drawContext, Text.literal(str), ModConfig.Position.TOP_LEFT,.6F, .6F, 1, 1F, 0xffffffff, true);
//            }
//        });
//    }
//
//    private static void registerKeybinding() {
//        if (keyBinding == null) {
//            keyBinding = KeyBindingHelper.registerKeyBinding(getMarkerKeyBinding());
//        }
//    }
//
//    private static KeyBinding getMarkerKeyBinding() {
//        int key = Optional.of(getConfig().rulerHud.keyBinding).orElse(GLFW.GLFW_KEY_PERIOD);
//        return new KeyBinding(
//                "key.woodcutter.mark-block",
//                InputUtil.Type.KEYSYM,
//                key,
//                "category.woodcutter.ruler"
//        );
//    }
//
//    private static void markLookedAtBlock() {
//        MinecraftClient client = MinecraftClient.getInstance();
//        assert client.world != null;
//        assert client.cameraEntity != null;
//        double maxReach = 1000F;
//        float tickDelta = 1.0F; // Used for tracking animation progress; no tracking is 1.0F
//        boolean includeFluids = true;
//        HitResult hit = client.cameraEntity.raycast(maxReach, tickDelta, includeFluids);
//
//        switch(Objects.requireNonNull(hit).getType()) {
//            case HitResult.Type.MISS:
//                markedBlocks.setEnd(null);
//                break;
//            case HitResult.Type.BLOCK:
//                BlockHitResult blockHit = (BlockHitResult) hit;
//                BlockPos blockPos = blockHit.getBlockPos();
//                markedBlocks.setEnd(blockPos);
//                break;
//        }
//    }
//
//    private static boolean isEnabled() {
//        MinecraftClient client = MinecraftClient.getInstance();
//        return getConfig().rulerHud.enabled && client.world != null && client.cameraEntity != null;
//    }
//
//    static class MarkedBlocks {
//        private BlockPos start = null;
//        private BlockPos end = null;
//        private boolean locked = false;
//
//        void reset() {
//            start = null;
//            end = null;
//            locked = false;
//        }
//
//        BlockPos getStart() {
//            return start;
//        }
//
//        void setStart(BlockPos pos) {
//            start = pos;
//        }
//
//        BlockPos getEnd() {
//            return end;
//        }
//
//        void setEnd(BlockPos pos) {
//            if (locked) {
//                return;
//            }
//            end = pos;
//        }
//
//        boolean isLocked() {
//            return locked;
//        }
//
//        boolean hasBaseAndHead() {
//            return start != null && end != null;
//        }
//
//        double distance() {
//            if (start == null || end == null) {
//                return 0F;
//            }
//            return Math.sqrt(start.getSquaredDistance(end.toCenterPos()));
//        }
//
//        int manhattanDistance() {
//            if (start == null || end == null) {
//                return 0;
//            }
//            return start.getManhattanDistance(end);
//        }
//
//        void addMarker() {
//            if (end == null) {
//                reset();
//            } else if (start == null) {
//                start = end;
//            } else if (locked) {
//                reset();
//            } else if (end == start) {
//                reset(); // Reset early if same block is selected
//            } else {
//                locked = true;
//            }
//        }
//    }
//}
