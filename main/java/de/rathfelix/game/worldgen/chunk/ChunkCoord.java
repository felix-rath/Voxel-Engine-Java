package de.rathfelix.game.worldgen.chunk;

import java.util.Objects;

public final class ChunkCoord {
    private final int chunkX;
    private final int chunkZ;

    public ChunkCoord(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public int getChunkX() { return chunkX; }
    public int getChunkZ() { return chunkZ; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChunkCoord)) return false;
        ChunkCoord that = (ChunkCoord) o;
        return chunkX == that.chunkX && chunkZ == that.chunkZ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chunkX, chunkZ);
    }

    @Override
    public String toString() {
        return "ChunkCoord{" + chunkX + "," + chunkZ + '}';
    }
}
