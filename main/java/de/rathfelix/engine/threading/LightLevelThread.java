package de.rathfelix.engine.threading;

import de.rathfelix.exceptions.LightLevelException;
import de.rathfelix.game.worldgen.chunk.Chunk;
import de.rathfelix.game.worldgen.chunk.ChunkLoader;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LightLevelThread implements Runnable {

    private static LightLevelThread instance;

    private static final int BASE_SLEEP_MS = 30; // Thread sleep time

    private Thread lightLevelThread;

    public Queue<Chunk> chunksToLight = new ConcurrentLinkedQueue<>(); // If chunks ready to load, put it in que.

    private LightLevelThread() {
        lightLevelThread = new Thread(this, "LIGHT_LEVEL");
        lightLevelThread.start();
    }
    @Override
    public void run() {
        lightLevelLoop();
    }

    private void lightLevelLoop() {
        while (true) {
            setChunkLightLevel();
            try {
                Thread.sleep(BASE_SLEEP_MS);
            } catch (InterruptedException e) {
                throw new LightLevelException(e.getMessage());
            }
        }
    }

    private void setChunkLightLevel() {
        Chunk chunk;
        while ((chunk = chunksToLight.poll()) != null) {
            chunk.generateLightLevel();
            ChunkLoader.getInstance().addChunkToLoad(chunk);
        }
    }

    public synchronized void removeChunkInQue(Chunk chunk) {
        chunksToLight.remove(chunk);
    }

    // Getter Setter
    public static LightLevelThread getInstance() {
        if (instance == null) instance = new LightLevelThread();
        return instance;
    }

    public void addChunkToLoad(Chunk chunk) {
        chunksToLight.add(chunk);
    }
}
