package de.rathfelix.engine;

import de.rathfelix.engine.config.Resolution;
import de.rathfelix.engine.input.KeyboardInput;
import de.rathfelix.engine.input.MouseInput;
import de.rathfelix.engine.math.Time;
import de.rathfelix.engine.camera.Camera;
import de.rathfelix.game.entities.player.Player;
import de.rathfelix.game.logic.GameLogic;
import de.rathfelix.game.logic.Physics.PlayerCollisionChunkLogic;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class GameEngine implements Runnable{
    private final Thread gameLoopThread;
    private final Window window;
    private final List<GameLogic> gameLogics;

    private final MouseInput mouseInput;

    private int fps = 1000;

    private long currentTime; // For fps counter.
    private long lastTime;

    public GameEngine(String title, Resolution resolution, boolean vSync) {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        this.window = Window.getInstance(title, resolution, vSync);
        this.gameLogics = GameLogic.getList();
        mouseInput = new MouseInput();
    }

    public void start() {
        gameLoopThread.start();
    }

    // Own Thread for gameloop.
    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void init() throws Exception {
        window.init();
        mouseInput.init(window);
        KeyboardInput.init(window.getWindow());
        //
        Camera.getInstance();
        Player.getPlayer();
        PlayerCollisionChunkLogic.getInstance();

        //
        for (GameLogic logic : gameLogics) { // Game logic scripts code.
           logic.init();
        }

        Time.init();
    }

    // Main loop.
    private void gameLoop() {
        long lastTime = System.currentTimeMillis(); // For fps counter.
        int fpsCounter = 0;
        int lastFps = 0;
        int hiddenFpsCounter = 0;
        double frameTime = 1.0d / (double) fps;
        while(!glfwWindowShouldClose(window.getWindow())) {
            double startTime = glfwGetTime();
            glfwPollEvents();
            input();
            Player.getPlayer().physicsEngine.update(Time.getDeltaTime());
            update();
            render();

            fpsCounter++;
            if ( System.currentTimeMillis() - lastTime >= 1000) {
                lastFps = fpsCounter;
                fpsCounter = 0;
                lastTime = System.currentTimeMillis();

                glfwSetWindowTitle(window.getWindow(), "FPS: " + lastFps);
            }

            double elapsed = glfwGetTime() - startTime;
            double sleepTime = frameTime - elapsed;
            if (sleepTime > 0) {
                try {
                    Thread.sleep((long) (sleepTime * 1000)); // Sync gameloop with fps, * 1000 bc secs to millisecs
                } catch (InterruptedException ignored) { }
            }
        }
    }

    public void input() {
        mouseInput.input(window);
        KeyboardInput.update();

        for (GameLogic logic : gameLogics) {
            logic.input(window,mouseInput);
        }
    }

    public void update() {
        Time.update();

        for (GameLogic logic : gameLogics) {
            logic.update(mouseInput);
        }
    }

    public void render() {
        for (GameLogic logic : gameLogics) {
            logic.render(window);
        }
    }

    public void cleanup() {
        for (GameLogic logic : gameLogics) {
            logic.cleanup();
        }
    }

    public Window getWindow() {
        return window;
    }

}
