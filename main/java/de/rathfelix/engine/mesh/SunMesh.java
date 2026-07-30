package de.rathfelix.engine.mesh;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import de.rathfelix.engine.texture.Texture;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.opengl.GL30.*;

public class SunMesh extends MeshBase{

    private List<Integer> vboIdList = new ArrayList<>(); // Vertex Buffer Object List

    private int vaoId;
    private int vertexCount;
    private int vboId;
    private Texture texture;

    public SunMesh(float[] positions, float[] textCoords, int[] indices, Texture texture) {
        this.texture = texture;
        this.vertexCount = indices.length;

        vaoId = glGenVertexArrays(); // Create and bind vao
        glBindVertexArray(vaoId);

        // Positions VBO
        int vboId = glGenBuffers();
        vboIdList.add(vboId);
        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
        verticesBuffer.put(positions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // Texture Coords VBO
        vboId = glGenBuffers();
        vboIdList.add(vboId);
        FloatBuffer textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
        textCoordsBuffer.put(textCoords).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        // Index VBO
        vboId = glGenBuffers();
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);


        // Unbind VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        memFree(verticesBuffer);
        memFree(textCoordsBuffer);
        memFree(indicesBuffer);
    }

    @Override
    public void render() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getId());
        glBindVertexArray(vaoId);

        // Activate Layer-Index Attribut.
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0); // Mesh draw call

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
    }

    @Override
    public void cleanup() {
        glDisableVertexAttribArray(0);

        // Delete the VBO.
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        vboIdList.forEach(GL30::glDeleteBuffers);


        // Delete the VAO.
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}
