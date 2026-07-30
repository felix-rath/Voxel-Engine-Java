package de.rathfelix.engine.shader;

import de.rathfelix.game.worldgen.chunk.Chunk;

import java.util.Random;

public class LightLevel {

    private static Random random = new Random();

    public static void lightBlocks(byte[][][] blockData, byte[][][] lightData) {
        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int z = 0; z < Chunk.SIZE; z++) {

                boolean found = false;

                // von oben nach unten
                for (int y = Chunk.HEIGHT - 1; y >= 0; y--) {
                    byte materialId = blockData[x][y][z];

                    if (!found) {
                        // Luft bekommt Licht
                        if (materialId == 0) {
                            lightData[x][y][z] = 10;
                        } else {
                            // erster feste Block bekommt auch Licht
                            lightData[x][y][z] = 10;
                            found = true;
                        }
                    } else {
                        // alles darunter bleibt dunkel
                        lightData[x][y][z] = 9;
                    }
                }
            }
        }
    }


}
