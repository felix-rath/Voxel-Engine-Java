package de.rathfelix.game.worldgen.structure;

/*
Stricture class, is the holder of structure blocks..
example: trees, houses, dungeons...
blockData: byte is the block material id.
 */

public abstract class StructureBase {

    private final int SIZE = 9; // structure 3d grid size.
    private final byte[][][] blockData = new byte[SIZE][SIZE][SIZE]; // contains all blocks of the structure.

    abstract void structurePropeties();


    // Set the material of a specific block pos in the 3d blockData grid.
    public void setBlock(int x, int y, int z, Byte materialId) {
        blockData[x][y][z] = materialId;
    }

    public Byte getBlock(int x, int y, int z) {
        return blockData[x][y][z];
    }

    public int getSize() {
        return SIZE;
    }
}
