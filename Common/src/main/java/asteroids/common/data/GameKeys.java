package asteroids.common.data;

import imgui.ImGui;

/**
 * Used to check the state of a given key
 */
public class GameKeys {
    /**
     * Get the key down state for any key
     */
    public static boolean isDown(int k) {
        return ImGui.isKeyDown(k);
    }

    /**
     * Check if the key was just pressed, is only true for one frame
     */
    public static boolean isPressed(int k) {
        return ImGui.isKeyPressed(k, false);
    }

    /**
     * Map the keys over to Core library
     */
    public static class Keys {
        public static final int UP = 265;
        public static final int LEFT = 262;
        public static final int DOWN = 264;
        public static final int RIGHT = 263;
        public static final int ENTER = 257;
        public static final int ESCAPE = 256;
        public static final int SPACE = 32;
        public static final int SHIFT = 340;
    }
}
