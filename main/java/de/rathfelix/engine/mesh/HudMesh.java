package de.rathfelix.engine.mesh;

import de.rathfelix.engine.texture.Texture;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.GL30.*;

public class HudMesh extends MeshBase {

    private final List<Integer> vboIdList = new ArrayList<>();

    private int vboId;
    private int vaoId;

    private Texture texture;
    private int vertexCount;

    public HudMesh(float[] positions, int[] indices, float[] textCoords, Texture texture) {
        this.texture = texture;
        this.vertexCount = indices.length;
        setColour(new Vector4f(1,1,1,1));

        // Create and bind array buffer
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // Vertex position VBO
        vboId = glGenBuffers();
        vboIdList.add(vboId);
        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
        verticesBuffer.put(positions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        memFree(verticesBuffer);

        // Vertex indices VBO
        vboId = glGenBuffers();
        vboIdList.add(vboId);
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        memFree(indicesBuffer);

        // Texture coordinates VBO
        vboId = glGenBuffers();
        vboIdList.add(vboId);
        FloatBuffer textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
        textCoordsBuffer.put(textCoords).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        memFree(textCoordsBuffer);
    }

    @Override
    public void render() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getId());
        glBindVertexArray(vaoId);

        glEnableVertexAttribArray(0); // Position
        glEnableVertexAttribArray(1); // TexCoords

        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }

    @Override
    public void cleanup() {
        // Delete the VBO
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        vboIdList.forEach(GL30::glDeleteBuffers);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}
