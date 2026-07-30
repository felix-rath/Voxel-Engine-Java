package de.rathfelix.engine.mesh;

import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.Buffer;

public abstract class MeshBase {
    public abstract void render();
    public abstract void cleanup();

    public Vector4f Colour;
    public Vector4f getColour() {return Colour;}
    public void setColour(Vector4f vector4f) {Colour = vector4f;}

    private boolean useTexture;
    public boolean isTextured() {return useTexture;}
    public void setUseTexture(boolean value) {useTexture = value;}

    public void memFree(Buffer buffer) {
        if (buffer != null) {
            MemoryUtil.memFree(buffer);
        }
    }
}
