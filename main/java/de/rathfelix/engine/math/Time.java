package de.rathfelix.engine.math;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {

    private static float deltaTime;
    private static double lastTime;
    private static double currentTime;

    public static void update() {
        currentTime = glfwGetTime();
        deltaTime = (float)(currentTime - lastTime);
        lastTime = currentTime;
    }

    public static float getDeltaTime() {
        return deltaTime;
    }

    public static double getTime() {
        return currentTime;
    }

    // Optional: reset when game starts
    public static void init() {
        lastTime = glfwGetTime();
        currentTime = lastTime;
        deltaTime = 0f;
    }
}
