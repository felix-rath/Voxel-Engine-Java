package de.rathfelix.engine.shader;

import de.rathfelix.exceptions.ShaderException;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private final int programId;

    private int vertexShaderId;

    private int fragmentShaderId;

    private final Map<String, Integer> uniforms;

    public ShaderProgram() throws ShaderException {
        programId = glCreateProgram();
        uniforms = new HashMap<>();

        if (programId == 0)
            throw  new ShaderException("Could not create Shader.");
    }

    public void createVertexShader(String shaderCode) throws ShaderException {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws ShaderException {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    private int createShader(String shaderCode, int shaderType) throws ShaderException {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0)
            throw new ShaderException("Could not create Shader. Type: " + shaderType);

        glShaderSource(shaderId, shaderCode); // Load GLSL Code in Shader.
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0)
            throw new ShaderException("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));

        glAttachShader(programId, shaderId);
        return shaderId;
    }

    // Link shader program wil OpenGL.
    public void link() throws ShaderException {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new ShaderException(("Error linking Shader code: ") + glGetProgramInfoLog(programId, 1024));
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }

        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);

            glValidateProgram(programId);
            if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
                System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
            }
        }
    }

    // Create a Uniform(variable inside the Shader).
    public void createUniform(String uniformName) throws ShaderException {
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
            throw new ShaderException("Could not find uniform: " + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    // Set a Matrix Uniform
    public void setUniform(String uniformName, Matrix4f value) {
        // Dump the matrix into a float buffer
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    // Set a int Uniform
    public void setUniform(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    // Set a float Uniform
    public void setUniform(String uniformName, float value) {
        glUniform1f(uniforms.get(uniformName), value);
    }

    // Set a vector3 Uniform
    public void setUniform(String uniformName, Vector3f value) {
        glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
    }

    // Set a vector4 Uniform
    public void setUniform(String uniformName, Vector4f value) {
        glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
    }

    // Bind Shaders to OpenGL
    public void bind() {
        glUseProgram(programId);
    }

    // Unbind Shaders from OpenGL
    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }
}
