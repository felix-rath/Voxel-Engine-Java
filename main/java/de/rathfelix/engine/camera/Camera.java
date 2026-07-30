package de.rathfelix.engine.camera;

import org.joml.Vector3f;

import javax.vecmath.Quat4f;

public class Camera {

    private static Camera instance;

    private final Vector3f position;

    private final Vector3f rotation;

    private Camera() {
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
    }

    public Camera(Vector3f postion, Vector3f rotation) {
        this.position = postion;
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.z = z;
        position.y = y;
    }

    public void setPosition(Vector3f vector3f) {
        position.x = vector3f.x;
        position.z = vector3f.z;
        position.y = vector3f.y;
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    public void movePosition(Vector3f vector3f) {
        if ( vector3f.z != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * vector3f.z;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * vector3f.z;
        }
        if ( vector3f.x != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * vector3f.x;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * vector3f.x;
        }
        position.y += vector3f.y;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }

    // Getter Setter
    public static Camera getInstance() {
        if (instance == null) instance = new Camera();
        return instance;
    }

    public Vector3f getViewDirectionFlat() {
        float yaw = (float) Math.toRadians(rotation.y);  // Yaw (Rotation um Y-Achse) in Bogenmaß
        float x = (float) Math.sin(yaw);                 // X-Komponente des Richtungsvektors
        float z = (float) Math.cos(yaw);                 // Z-Komponente des Richtungsvektors

        Vector3f dir = new Vector3f(x, 0, z);            // Vektor auf XZ-Ebene (Y=0)
        dir.normalize();                                 // Normieren (auf Länge 1 bringen)
        return dir;
    }

    public Quat4f getRotationQuat() {
        float pitch = (float) Math.toRadians(rotation.x);
        float yaw   = (float) Math.toRadians(rotation.y);
        float roll  = (float) Math.toRadians(rotation.z);

        float cy = (float) Math.cos(yaw * 0.5);
        float sy = (float) Math.sin(yaw * 0.5);
        float cp = (float) Math.cos(pitch * 0.5);
        float sp = (float) Math.sin(pitch * 0.5);
        float cr = (float) Math.cos(roll * 0.5);
        float sr = (float) Math.sin(roll * 0.5);

        Quat4f q = new Quat4f();
        q.w = cr * cp * cy + sr * sp * sy;
        q.x = sr * cp * cy - cr * sp * sy;
        q.y = cr * sp * cy + sr * cp * sy;
        q.z = cr * cp * sy - sr * sp * cy;

        return q;
    }


}