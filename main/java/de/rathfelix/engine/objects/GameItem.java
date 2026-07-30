package de.rathfelix.engine.objects;

import de.rathfelix.engine.mesh.MeshBase;
import org.joml.Vector3f;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameItem {
    private MeshBase mesh;

    private final Vector3f position;

    private float scale;

    private final Vector3f rotation;

    private boolean selected;

    public static final Queue<GameItem> gameItemList = new ConcurrentLinkedQueue<>();

    public GameItem() {
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public MeshBase getMesh() {
        return mesh;
    }

    public void setMesh(MeshBase mesh) {
        this.mesh = mesh;
    }

    public void setSelected(boolean isSelected) {
        this.selected = isSelected;
    }
    public boolean isSelected(){
        return selected;
    }
}
