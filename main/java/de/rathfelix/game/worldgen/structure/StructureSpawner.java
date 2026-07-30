package de.rathfelix.game.worldgen.structure;

import de.rathfelix.game.worldgen.chunk.Chunk;

public class StructureSpawner {

    // Oak tree
    private static OakTreeStructure oakTreeStructure;
    public static OakTreeStructure getOakTreeStructure() {
        if (oakTreeStructure == null) oakTreeStructure = new OakTreeStructure();
        return oakTreeStructure;
    }

    // Birch tree
    private static BirchTreeStructure birchTreeStructure;
    public static BirchTreeStructure getBirchTreeStructure() {
        if (birchTreeStructure == null) birchTreeStructure = new BirchTreeStructure();
        return birchTreeStructure;
    }

    // Spruce tree
    private static SpruceTreeStructure spruceTreeStructure;
    public static SpruceTreeStructure getSpruceTreeStructure() {
        if (spruceTreeStructure == null) spruceTreeStructure = new SpruceTreeStructure();
        return spruceTreeStructure;
    }

    public static void spawnStructure(Chunk chunk, StructureBase structure, int startX, int startY, int startZ) {
        int size = structure.getSize();

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    Byte blockId = structure.getBlock(x, y, z);
                    if (blockId != null && blockId > 0) {
                        int cx = startX + x;
                        int cy = startY + y;
                        int cz = startZ + z;

                        // ERST prüfen ob innerhalb des Chunks!
                        if (cx >= 0 && cx < Chunk.SIZE &&
                                cy >= 0 && cy < Chunk.HEIGHT &&
                                cz >= 0 && cz < Chunk.SIZE) {

                            // DANN prüfen ob bereits Block da ist
                            if (chunk.getBlockData()[cx][cy][cz] != 0)
                                continue; // continue statt return!

                            chunk.addBlock(cx, cy, cz, blockId);
                        }
                    }
                }
            }
        }
    }
}
