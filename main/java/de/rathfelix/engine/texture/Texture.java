package de.rathfelix.engine.texture;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Texture {

    private static TextureArray textureArray;

    private int textureId;
    private int width;
    private int height;

    public Texture(String fileName) {
        PNGDecoder decoder = null;
        ByteBuffer buf = null;
        try {   
            decoder = new PNGDecoder(
                    Texture.class.getResourceAsStream(fileName)
            );

            buf = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.width = decoder.getWidth();
        this.height = decoder.getHeight();


        textureId = glGenTextures(); // Create a new OpenGL texture.
        glBindTexture(GL_TEXTURE_2D, textureId); // Bind the texture.
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1); // How OpenGL unpack RGBA-Bytes.

        // Upload texture.
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(),
                decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST); // Texture draw sharpness and.
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glGenerateMipmap(GL_TEXTURE_2D);
    }

    private static void createTextureArray() { // Create with all Textures.
        String[] textures = {
                "/textures/dirt_texture.png",
                "/textures/dirt_texture.png",
                "/textures/grass_texture.png",
                "/textures/stone_texture.png",
                "/textures/sand_texture.png",
                "/textures/water_texture.png",
                "/textures/wood_oak_texture.png",
                "/textures/leave_oak_texture.png",
                "/textures/snow_texture.png",
                "/textures/wood_birch_texture.png",
                "/textures/leave_birch_texture.png",
        };

        textureArray  = new TextureArray(textures);
    }

    // Getter Setter
    public int getId() {
        return textureId;
    }

    public static TextureArray getTextureArray() { // Get Texture array and create if null.
        if (textureArray == null) createTextureArray();
        return textureArray;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
