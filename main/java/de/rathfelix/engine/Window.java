package de.rathfelix.engine;

import de.rathfelix.engine.config.Resolution;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private static Window instance;

    private long window;
    private Resolution resolution;
    private String title;
    private int fps = 120;
    private boolean vSync;
    private boolean resized;

    private Window(String title, Resolution resolution, boolean vSync) {
        this.resolution = resolution;
        this.title = title;
        this.vSync = vSync;
        resized = true;
    }

    public static Window getInstance(String title, Resolution resolution, boolean vSync) {
        if (instance == null) {
            instance = new Window(title, resolution, vSync);
        }
        return instance;
    }

    public static Window getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Window not initialized yet.");
        }
        return  instance;
    }

    // initialize window.
    public void init() {
        GLFWErrorCallback.createPrint(System.err).set(); // Print error message in System.err

        if (!glfwInit()) // Initiate glfw
            throw new IllegalStateException("Unable to initialize GLFW");

        windowHints();
        createWindow();

        resizeCallback();

        glfwMakeContextCurrent(window); // Set the OpenGL window in use.s
        glfwSwapInterval(vSync ? 1 : 0); // Use vSync or not.
        glfwShowWindow(window); // Show the window.

        GL.createCapabilities(); // Make OpenGL bindings available for us.
        // GLUtil.setupDebugMessageCallback();

        glEnable(GL_DEPTH_TEST); // Transparency of rendered faces.
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        glEnable(GL_BLEND); // For material alpha.
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        frontFaceRender();
        setClearColor(0.5f, 0.7f, 1.0f, 1.0f);
    }

    // Sets the window start-parameters.
    private void windowHints() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); // Best profile for 3D game.
    }

    private void createWindow() { // Create glfw window.
        window = glfwCreateWindow(resolution.width, resolution.height, title, 0, 0);
        if (window == 0)
            throw new RuntimeException("Failed to create GLFW window");
    }

    public long getWindow() {
        return window;
    }

    // Sets the color after clear/frame swap
    public void setClearColor(float color_r, float color_g, float color_b, float alpha) {
        glClearColor(color_r, color_g, color_b, alpha);
    }

    public boolean isResized() {
        return resized;
    }

    public void setResized(boolean bool) {
        resized = bool;
    }

    public Resolution getResolution() {
        return resolution;
    }

    // Resize callback
    private void resizeCallback() {
        glfwSetFramebufferSizeCallback(window, (handle, width, height) -> {
            Window.this.resolution.width = width;
            Window.this.resolution.height = height;
            Window.this.setResized(true);
        });
    }

    // Wire Render.
    public void setWireMode(boolean wire) {
        if (wire) glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        else glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }

    // Which side the face will render.
    private void frontFaceRender() {
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glFrontFace(GL_CCW);
    }
}
