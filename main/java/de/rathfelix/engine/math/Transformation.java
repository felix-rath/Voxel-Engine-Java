package de.rathfelix.engine.math;

import de.rathfelix.engine.objects.GameItem;
import de.rathfelix.engine.camera.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {

    private static Transformation instance;

    private final Matrix4f projectionMatrix;
    private final Matrix4f modelViewMatrix;
    private final Matrix4f viewMatrix;
    private final Matrix4f orthoMatrix;

    private Transformation() {
        modelViewMatrix = new Matrix4f();
        projectionMatrix = new Matrix4f();
        orthoMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
    }

    public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        float aspectRation = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRation, zNear, zFar);
        return projectionMatrix;
    }

    public Matrix4f getModelViewMatrix(GameItem item, Matrix4f viewMatrix) {
        Vector3f rotation = item.getRotation();
        modelViewMatrix.identity().translate(item.getPosition()).
                rotateX((float)Math.toRadians(-rotation.x)).
                rotateY((float)Math.toRadians(-rotation.y)).
                rotateZ((float)Math.toRadians(-rotation.z)).
                scale(item.getScale());
        Matrix4f viewCurr = new Matrix4f(viewMatrix);
        return viewCurr.mul(modelViewMatrix);
    }

    public Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f rotation = camera.getRotation();

        viewMatrix.identity();
        // First do the rotation so camera rotates over its position
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
    }

    public final Matrix4f getOrthoProjectionMatrix(float left, float right, float bottom, float top) {
        orthoMatrix.identity();
        orthoMatrix.setOrtho2D(left, right, bottom, top);
        return orthoMatrix;
    }

    // Getter setter

    public static Transformation getInstance() {
        if (instance == null) instance = new Transformation();
        return instance;
    }
}
