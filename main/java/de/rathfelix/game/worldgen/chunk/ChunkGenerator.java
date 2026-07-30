package de.rathfelix.game.worldgen.chunk;

import de.rathfelix.engine.camera.Camera;
import de.rathfelix.engine.threading.LightLevelThread;
import org.joml.Vector3f;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Generiert Chunks in einem Kreis um den Spieler herum.
 * Lädt nächste Chunks zuerst (Spiral-Pattern von innen nach außen).
 */
public class ChunkGenerator implements Runnable {

    private static volatile ChunkGenerator instance;

    private final Thread thread;
    private final AtomicBoolean running = new AtomicBoolean(true);

    private final Set<ChunkCoord> loadingChunks = ConcurrentHashMap.newKeySet();
    private final Queue<ChunkLoadTask> loadQueue = new LinkedList<>();

    private ChunkCoord lastPlayerChunk = null;

    private static final int UPDATE_INTERVAL_MS = 50;
    private static final int CHUNKS_PER_TICK = 8;

    private ChunkGenerator() {
        thread = new Thread(this, "CHUNK_GEN");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        while (running.get()) {
            try {
                tick();
                Thread.sleep(UPDATE_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("ChunkGenerator Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void tick() {
        Camera cam = Camera.getInstance();
        if (cam == null) return;

        Vector3f camPos = cam.getPosition();
        if (camPos == null) return;

        int playerChunkX = Math.floorDiv((int) camPos.x, Chunk.SIZE);
        int playerChunkZ = Math.floorDiv((int) camPos.z, Chunk.SIZE);
        ChunkCoord playerChunk = new ChunkCoord(playerChunkX, playerChunkZ);

        // Update Queue nur wenn sich Spieler-Chunk geändert hat
        if (!playerChunk.equals(lastPlayerChunk)) {
            updateLoadQueue(playerChunkX, playerChunkZ);
            lastPlayerChunk = playerChunk;
        }

        // Lade Chunks aus der Queue (auch wenn nicht bewegt)
        processLoadQueue();

        // Cleanup alte loading-Einträge
        cleanupLoadingChunks(playerChunkX, playerChunkZ);
    }

    private void updateLoadQueue(int playerChunkX, int playerChunkZ) {
        synchronized (loadQueue) {
            loadQueue.clear();

            int renderDist = Chunk.RENDER_DIST;
            List<ChunkLoadTask> tasks = new ArrayList<>();

            // Spiral-Pattern: Von innen nach außen, Layer für Layer
            for (int layer = 0; layer <= renderDist; layer++) {
                for (int dx = -layer; dx <= layer; dx++) {
                    for (int dz = -layer; dz <= layer; dz++) {
                        // Nur den Rand des aktuellen Layers
                        if (Math.abs(dx) != layer && Math.abs(dz) != layer) continue;

                        // Kreisförmiger Bereich
                        int distSq = dx * dx + dz * dz;
                        if (distSq > renderDist * renderDist) continue;

                        int chunkX = playerChunkX + dx;
                        int chunkZ = playerChunkZ + dz;
                        ChunkCoord coord = new ChunkCoord(chunkX, chunkZ);

                        // Skip wenn bereits geladen oder am Laden
                        if (ChunkHolder.getChunkByCoord(coord) != null) continue;
                        if (loadingChunks.contains(coord)) continue;

                        // Distanz für Sortierung
                        float distance = (float) Math.sqrt(distSq);
                        tasks.add(new ChunkLoadTask(coord, distance));
                    }
                }
            }

            // Sortiere nach Distanz (nächste zuerst)
            tasks.sort(Comparator.comparingDouble(t -> t.distance));
            loadQueue.addAll(tasks);
        }
    }

    private void processLoadQueue() {
        int loaded = 0;

        synchronized (loadQueue) {
            while (!loadQueue.isEmpty() && loaded < CHUNKS_PER_TICK) {
                ChunkLoadTask task = loadQueue.poll();
                if (task == null) break;

                ChunkCoord coord = task.coord;

                // Double-check
                if (ChunkHolder.getChunkByCoord(coord) != null) continue;
                if (loadingChunks.contains(coord)) continue;

                loadingChunks.add(coord);

                try {
                    Chunk chunk = new Chunk(coord.getChunkX(), coord.getChunkZ());
                    LightLevelThread lightThread = LightLevelThread.getInstance();

                    if (lightThread != null) {
                        lightThread.addChunkToLoad(chunk);
                        loaded++;
                    } else {
                        loadingChunks.remove(coord);
                    }
                } catch (Exception e) {
                    System.err.println("Fehler beim Laden von Chunk " + coord + ": " + e.getMessage());
                    e.printStackTrace();
                    loadingChunks.remove(coord);
                }
            }
        }
    }

    private void cleanupLoadingChunks(int playerChunkX, int playerChunkZ) {
        int maxDist = Chunk.RENDER_DIST + 3;

        loadingChunks.removeIf(coord -> {
            // Entferne wenn zu weit weg oder bereits geladen
            if (ChunkHolder.getChunkByCoord(coord) != null) return true;

            int dx = coord.getChunkX() - playerChunkX;
            int dz = coord.getChunkZ() - playerChunkZ;
            return dx * dx + dz * dz > maxDist * maxDist;
        });
    }

    /**
     * Callback wenn ein Chunk erfolgreich geladen wurde
     */
    public void onChunkLoaded(ChunkCoord coord) {
        loadingChunks.remove(coord);
    }

    /**
     * Erzwingt ein Update der Load-Queue
     */
    public void forceUpdate() {
        lastPlayerChunk = null;
    }

    public void shutdown() {
        running.set(false);
        thread.interrupt();
    }

    public static ChunkGenerator getInstance() {
        if (instance == null) {
            synchronized (ChunkGenerator.class) {
                if (instance == null) {
                    instance = new ChunkGenerator();
                }
            }
        }
        return instance;
    }

    public synchronized void removeChunkInQue(Chunk chunk) {
        loadQueue.remove(chunk);
        loadingChunks.remove(chunk);
    }

    // Inner class für Load-Tasks
    private static class ChunkLoadTask {
        final ChunkCoord coord;
        final float distance;

        ChunkLoadTask(ChunkCoord coord, float distance) {
            this.coord = coord;
            this.distance = distance;
        }
    }
}