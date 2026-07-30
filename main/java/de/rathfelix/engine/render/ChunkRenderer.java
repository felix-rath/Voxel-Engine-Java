package de.rathfelix.engine.render;

import de.rathfelix.Util.Utils;
import de.rathfelix.engine.math.Transformation;
import de.rathfelix.engine.objects.GameItem;
import de.rathfelix.engine.camera.Camera;
import de.rathfelix.engine.shader.ShaderProgram;
import de.rathfelix.exceptions.ShaderException;
import de.rathfelix.game.worldgen.chunk.Chunk;
import org.joml.Matrix4f;

import java.util.Queue;

public class ChunkRenderer {
    private final Transformation transformation = Transformation.getInstance();
    private final Camera camera = Camera.getInstance();
    private ShaderProgram shaderProgram;

    public static float FOV = (float) Math.toRadians(90f);
    private static final float Z_NEAR = 0.2f;
    private static final float Z_FAR = 2000f;

    public void init() throws ShaderException {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/fragment.fs"));
        shaderProgram.link();

        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("modelViewMatrix");
        shaderProgram.createUniform("colour");
        shaderProgram.createUniform("useColour");
    }

    public void render(Queue<Chunk> chunks) {
        shaderProgram.bind();

        Matrix4f viewMatrix = transformation.getViewMatrix(camera);
        shaderProgram.setUniform("projectionMatrix", transformation.getProjectionMatrix(
                FOV, 1920, 1080, Z_NEAR, Z_FAR
        ));

        for (Chunk chunk : chunks) {
            GameItem item = new GameItem();
            item.setMesh(chunk.getMesh());
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(item, viewMatrix);
            shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
            chunk.getMesh().render();
        }

        shaderProgram.unbind();
    }
}

