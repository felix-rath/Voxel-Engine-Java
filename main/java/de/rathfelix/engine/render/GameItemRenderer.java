package de.rathfelix.engine.render;

import de.rathfelix.Util.Utils;
import de.rathfelix.engine.math.Transformation;
import de.rathfelix.engine.mesh.MeshBase;
import de.rathfelix.engine.objects.GameItem;
import de.rathfelix.engine.camera.Camera;
import de.rathfelix.engine.shader.ShaderProgram;
import de.rathfelix.exceptions.ShaderException;
import org.joml.Matrix4f;

import java.util.Queue;

public class GameItemRenderer {
    private final Transformation transformation = Transformation.getInstance();
    private final Camera camera = Camera.getInstance();
    private ShaderProgram shaderProgram;

    private static final float FOV = (float) Math.toRadians(90.0f);
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

    public void render(Queue<GameItem> gameItems) {
        shaderProgram.bind();

        Matrix4f viewMatrix = transformation.getViewMatrix(camera);
        shaderProgram.setUniform("projectionMatrix", transformation.getProjectionMatrix(
                FOV, 1920, 1080, Z_NEAR, Z_FAR
        ));

        for (GameItem gameItem : gameItems) {
            MeshBase mesh = gameItem.getMesh();
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItem, viewMatrix);
            shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
            shaderProgram.setUniform("colour", mesh.getColour());
            shaderProgram.setUniform("useColour", mesh.isTextured() ? 0 : 1);
            mesh.render();
        }

        shaderProgram.unbind();
    }
}
