package de.rathfelix.engine.render;

import de.rathfelix.Util.Utils;
import de.rathfelix.engine.camera.Camera;
import de.rathfelix.engine.math.Transformation;
import de.rathfelix.engine.objects.GameItem;
import de.rathfelix.engine.shader.ShaderProgram;
import de.rathfelix.exceptions.ShaderException;
import org.joml.Matrix4f;

import java.util.List;

public class SunRenderer {

    private final float FOV = (float) Math.toRadians(90f);
    private static final float Z_NEAR = 0.2f;
    private static final float Z_FAR = 2000f;

    private ShaderProgram shaderProgram;
    private Transformation transformation;
    private Camera camera;

    public void init() throws ShaderException {
        this.shaderProgram = new ShaderProgram();
        this.transformation = Transformation.getInstance();
        this.camera = Camera.getInstance();

        shaderProgram.createFragmentShader(Utils.loadResource("/sun_fragment.fs"));
        shaderProgram.createVertexShader(Utils.loadResource("/sun_vertex.vs"));
        shaderProgram.link();

        shaderProgram.createUniform("projMatrix");
        shaderProgram.createUniform("modelViewMatrix");
    }

    public void render(List<GameItem> renderList) {
        shaderProgram.bind();

        for (GameItem items : renderList) {
            Matrix4f viewMatrix = transformation.getViewMatrix(camera);
            viewMatrix.m30(0);
            viewMatrix.m31(0);
            viewMatrix.m32(0);

            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(items, viewMatrix);
            Matrix4f projMatrix = transformation.getProjectionMatrix(FOV, 1920, 1080, Z_NEAR, Z_FAR);

            shaderProgram.setUniform("projMatrix", projMatrix);
            shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
            items.getMesh().render();
        }

        shaderProgram.unbind();
    }
}
