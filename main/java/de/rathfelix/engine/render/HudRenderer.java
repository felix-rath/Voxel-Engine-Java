package de.rathfelix.engine.render;

import de.rathfelix.Util.Utils;
import de.rathfelix.engine.Window;
import de.rathfelix.engine.math.Transformation;
import de.rathfelix.engine.mesh.MeshBase;
import de.rathfelix.engine.objects.GameItem;
import de.rathfelix.engine.shader.ShaderProgram;
import de.rathfelix.exceptions.ShaderException;
import org.joml.Matrix4f;

import java.util.List;


public class HudRenderer {

    private ShaderProgram shaderProgram;
    private Transformation transformation;

    public void init() throws ShaderException {
        this.shaderProgram = new ShaderProgram();
        this.transformation = Transformation.getInstance();

        shaderProgram.createVertexShader(Utils.loadResource("/hud_vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/hud_fragment.fs"));
        shaderProgram.link();

        shaderProgram.createUniform("projModelMatrix");
        shaderProgram.createUniform("colour");
    }

    public void render(Window window, List<GameItem> hud) {
        shaderProgram.bind();

        Matrix4f ortho = transformation.getOrthoProjectionMatrix(0, window.getResolution().width, window.getResolution().height, 0);
        for (GameItem gameItem : hud) {
            MeshBase mesh = gameItem.getMesh();
            // Set orthotaphic and model matrix for this HUD item
            Matrix4f projModelMatrix = transformation.getModelViewMatrix(gameItem, ortho);
            shaderProgram.setUniform("projModelMatrix", projModelMatrix);
            shaderProgram.setUniform("colour", gameItem.getMesh().getColour());
            mesh.render();
        }
        shaderProgram.unbind();
    }
}
