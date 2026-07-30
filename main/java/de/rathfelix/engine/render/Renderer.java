package de.rathfelix.engine.render;

import de.rathfelix.engine.hud.Hud;
import de.rathfelix.engine.mesh.MeshBase;
import de.rathfelix.engine.objects.GameItem;
import de.rathfelix.engine.Window;
import de.rathfelix.game.worldgen.chunk.Chunk;

import java.util.List;
import java.util.Queue;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private final ChunkRenderer chunkRenderer;
    private final GameItemRenderer gameItemRenderer;
    private final HudRenderer hudRenderer;
    private final SunRenderer sunRenderer;

    public Renderer() {
        this.chunkRenderer = new ChunkRenderer();
        this.gameItemRenderer = new GameItemRenderer();
        this.hudRenderer = new HudRenderer();
        this.sunRenderer = new SunRenderer();
    }

    // Create Fragment and Vertex Shader and load it into a program.
    public void init() throws Exception {
        chunkRenderer.init();
        gameItemRenderer.init();
        hudRenderer.init();
        sunRenderer.init();
    }

    public void render(Window window, Queue<Chunk> chunkQue, Queue<GameItem> gameItemQue, List<GameItem> renderList) {
        prepareFrames(window);

        chunkRenderer.render(chunkQue);
        gameItemRenderer.render(gameItemQue);
        sunRenderer.render(renderList);
        hudRenderer.render(window, Hud.getGameItems());

        swapBuffer(window);
    }

    // Clear current Frames
    private void prepareFrames(Window window) {
        if (window.isResized()) {
            glViewport(0, 0, window.getResolution().width, window.getResolution().height);
            window.setResized(false);
        }
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    // Swap front/back buffer and load new frame.
    private void swapBuffer(Window window) {
        glfwSwapBuffers(window.getWindow());
    }

}