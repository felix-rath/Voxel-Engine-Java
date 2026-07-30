package de.rathfelix.engine.mesh;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import de.rathfelix.engine.texture.Texture;
import de.rathfelix.engine.texture.TextureArray;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.opengl.GL30.*;

public class ObjMesh extends MeshBase {
    private List<Integer> vboIdList = new ArrayList<>(); // Vertex Buffer Object List

    private final int vaoId; // Vertex Array Object

    private final int vertexCount;

    private TextureArray textureArray;

    public ObjMesh(float[] positions, float[] textCoords, float[] normals, int[] indices) {
        this.textureArray = Texture.getTextureArray();
        setColour(new Vector4f(1, 1, 1, 1));
        vertexCount = indices.length;

        vaoId = glGenVertexArrays();
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

        // Vertex normals VBO
        vboId = glGenBuffers();
        vboIdList.add(vboId);
        FloatBuffer vecNormalsBuffer = MemoryUtil.memAllocFloat(normals.length);
        vecNormalsBuffer.put(normals).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vecNormalsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

        // Index VBO
        vboId = glGenBuffers();
        vboIdList.add(vboId);
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        // Unbind VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        // Free buffers
        memFree(verticesBuffer);
        memFree(textCoordsBuffer);
        memFree(indicesBuffer);
    }

    @Override
    public void render() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D_ARRAY, textureArray.getId()); // Care: Texture Array

        glBindVertexArray(getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);  // Activate Layer-Index Attribut.

        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

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

    // Getter Setter
    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

}
