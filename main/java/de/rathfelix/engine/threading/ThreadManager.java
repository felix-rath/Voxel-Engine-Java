package de.rathfelix.engine.threading;

import de.rathfelix.game.worldgen.chunk.ChunkGenerator;
import de.rathfelix.game.worldgen.chunk.ChunkLoader;
import de.rathfelix.game.worldgen.chunk.ChunkUnloader;

public class ThreadManager {

    private static ThreadManager instance;

    private ThreadManager() {
        init();
    }

    // Start all threads.
    private void init() {
        ChunkLoader.getInstance();
        ChunkUnloader.getInstance();
        LoggerThread.getInstance();
        ChunkGenerator.getInstance();
        LightLevelThread.getInstance();
    }

    public static ThreadManager getInstance() {
        if (instance == null) instance = new ThreadManager();

        return instance;
    }
}
