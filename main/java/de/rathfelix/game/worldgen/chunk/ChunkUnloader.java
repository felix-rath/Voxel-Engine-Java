package de.rathfelix.game.worldgen.chunk;

import de.rathfelix.exceptions.ChunkUnloadException;

public class ChunkUnloader implements Runnable {

    private static ChunkUnloader instance;

    private Thread chunkUnloaderThread;

    // Performance optimizations
    private static final int BASE_SLEEP_MS = 50; // Thread sleep time

    private ChunkUnloader() {
        chunkUnloaderThread = new Thread(this, "CHUNK_UNLOADER");
        chunkUnloaderThread.start();
    }

    @Override // Thread run Method.
    public void run() {
        chunkUnloadLoop();
    }

    private void chunkUnloadLoop() {
        while (true) {
            try {
                unloadChunk();
                Thread.sleep(BASE_SLEEP_MS);
            } catch (InterruptedException e) {
                throw new ChunkUnloadException(e.getMessage());
            }
        }
    }

    private void unloadChunk() {
        ChunkHolder.removeDistanceChunkLoop();
    }

    // Getter Setter
    public static ChunkUnloader getInstance() { // Singleton
        if (instance == null) instance = new ChunkUnloader();
        return instance;
    }

}
