package de.rathfelix.game.worldgen.chunk;

import de.rathfelix.engine.camera.Camera;
import de.rathfelix.engine.threading.LightLevelThread;
import org.joml.Vector3f;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChunkHolder {

    // Thread-sichere Container
    public static final Queue<Chunk> chunkItemList = new ConcurrentLinkedQueue<>();
    public static final Queue<Chunk> chunkItemCleanUpList = new ConcurrentLinkedQueue<>();
    public static final Map<ChunkCoord, Chunk> chunkMap = new ConcurrentHashMap<>();

    public static void addChunkItem(Chunk chunk) {
        chunkItemList.add(chunk);
        chunkMap.put(chunk.getChunkCoord(), chunk);
    }

    public static void removeChunkItem(Chunk chunk) {
        chunkItemCleanUpList.add(chunk);
        chunkItemList.remove(chunk);
        chunkMap.remove(chunk.getChunkCoord());
        ChunkLoader.getInstance().removeChunkInQue(chunk);
        LightLevelThread.getInstance().removeChunkInQue(chunk);
        ChunkGenerator.getInstance().removeChunkInQue(chunk);
    }

    /**
     * Entfernt alle Chunks, die außerhalb der Render-Distanz liegen.
     */
    public static void removeDistanceChunkLoop() {
        // Kamera einmalig in Chunk-Koordinaten umrechnen
        final Vector3f camPos = Camera.getInstance().getPosition();
        final int camChunkX = (int) Math.floor(camPos.x / Chunk.SIZE);
        final int camChunkZ = (int) Math.floor(camPos.z / Chunk.SIZE);

        final double maxAllowed = Chunk.RENDER_DIST; // Radius in Chunks
        final double maxAllowedSq = maxAllowed * maxAllowed; // Quadrat für euklidische Distanz

        // Über alle Chunks iterieren
        Iterator<Chunk> iter = chunkItemList.iterator();
        while (iter.hasNext()) {
            Chunk chunk = iter.next();
            final ChunkCoord coord = chunk.getChunkCoord();
            final int dx = coord.getChunkX() - camChunkX;
            final int dz = coord.getChunkZ() - camChunkZ;
            final double distSq = dx * dx + dz * dz;

            if (distSq > maxAllowedSq) {
                // Entfernen + Cleanup vormerken
                iter.remove();
                removeChunkItem(chunk);
            }
        }
    }

    /**
     * Cleanup aufräumen (Meshes freigeben)
     */
    public static void cleanUp() {
        Chunk chunk;
        while ((chunk = chunkItemCleanUpList.poll()) != null) {
            chunk.getMesh().cleanup();
        }
    }

    public static Chunk getChunkByCoord(ChunkCoord coord) {
        return chunkMap.get(coord);
    }
}
