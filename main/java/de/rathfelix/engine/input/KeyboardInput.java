package de.rathfelix.engine.input;

import org.lwjgl.glfw.GLFW;

public class KeyboardInput {

    private static final int KEY_COUNT = GLFW.GLFW_KEY_LAST + 1;
    private static boolean[] currentKeys = new boolean[KEY_COUNT];
    private static boolean[] previousKeys = new boolean[KEY_COUNT];
    private static boolean[] keyDown = new boolean[KEY_COUNT];
    private static long window;

    public static void init(long glfwWindow) {
        window = glfwWindow;
    }

    public static void update() {
        // Alle Tastenzustände für diesen Frame aktualisieren
        for (int i = 32; i <= GLFW.GLFW_KEY_LAST; i++) {
            previousKeys[i] = currentKeys[i];
            currentKeys[i] = GLFW.glfwGetKey(window, i) == GLFW.GLFW_PRESS;
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return currentKeys[keyCode];
    }

    public static boolean isKeyJustPressed(int keyCode) {
        return currentKeys[keyCode] && !previousKeys[keyCode];
    }

    public static boolean isKeyJustReleased(int keyCode) {
        return !currentKeys[keyCode] && previousKeys[keyCode];
    }
}
