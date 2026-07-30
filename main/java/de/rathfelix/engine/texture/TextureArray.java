package de.rathfelix.engine.texture;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL42.*;

public class TextureArray {

    private int textureId;
    private int width, height, layers;

    public TextureArray(String[] fileNames) {
        layers = fileNames.length;

        // Lade alle Texturen mit PNGDecoder, speichere in ByteBuffers
        ByteBuffer[] buffers = new ByteBuffer[layers];

        try {
            PNGDecoder decoder = new PNGDecoder(TextureArray.class.getResourceAsStream(fileNames[0]));
            width = decoder.getWidth();
            height = decoder.getHeight();
            buffers[0] = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffers[0], width * 4, PNGDecoder.Format.RGBA);
            buffers[0].flip();

            for (int i = 1; i < layers; i++) {
                decoder = new PNGDecoder(TextureArray.class.getResourceAsStream(fileNames[i]));
                if (decoder.getWidth() != width || decoder.getHeight() != height) {
                    throw new RuntimeException("All textures must have the same size!");
                }
                buffers[i] = ByteBuffer.allocateDirect(4 * width * height);
                decoder.decode(buffers[i], width * 4, PNGDecoder.Format.RGBA);
                buffers[i].flip();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Generiere Texture Array
        textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D_ARRAY, textureId);

        // Reserviere Speicher
        glTexStorage3D(GL_TEXTURE_2D_ARRAY, 1, GL_RGBA8, width, height, layers);

        // Fülle Layer einzeln
        for (int i = 0; i < layers; i++) {
            glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0,
                    0, 0, i,
                    width, height, 1,
                    GL_RGBA, GL_UNSIGNED_BYTE,
                    buffers[i]);
        }

        // Filter & Wrapping
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glBindTexture(GL_TEXTURE_2D_ARRAY, 0);
    }

    public int getId() {
        return textureId;
    }
}

