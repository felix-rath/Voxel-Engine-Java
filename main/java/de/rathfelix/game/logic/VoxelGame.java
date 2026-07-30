package de.rathfelix.game.logic;

import de.rathfelix.engine.Window;
import de.rathfelix.engine.input.MouseInput;
import de.rathfelix.engine.objects.GameItem;
import de.rathfelix.engine.render.Renderer;
import de.rathfelix.engine.threading.ThreadManager;
import de.rathfelix.game.logic.Skybox.SunLogic;
import de.rathfelix.game.worldgen.chunk.*;
import de.rathfelix.game.worldgen.chunk.ChunkHolder;

public class VoxelGame extends GameLogic {

    private Renderer renderer;
    private WiremodeLogic wireLogic;
    private SunLogic sunLogic;


    public VoxelGame() {
        this.renderer = new Renderer();
        this.wireLogic = new WiremodeLogic();
        this.sunLogic = new SunLogic();
    }

    @Override
    public void init() throws Exception {
        ThreadManager.getInstance();
        renderer.init();
    }

    @Override
    public void update(MouseInput mouseInput) {
        // Debug log chunk stats
        //chunkLoader = ChunkLoader.getInstance();
        //Debug.log("chunksToLoad: " + chunkLoader.getChunksToLoadSize());
        //Debug.log("chunksToRender: " + chunkLoader.getChunksToRenderSize());
        //Debug.log("gameitem chunk: " + ChunkHolder.chunkItemList.size());
        //Debug.log("cleannupo chunk: " + ChunkHolder.chunkItemCleanUpList.size());
    }//

    @Override
    public void render(Window window) {
        ChunkLoader.getInstance().render();
        renderer.render(window, ChunkHolder.chunkItemList, GameItem.gameItemList, sunLogic.getRenderList());
        ChunkHolder.cleanUp();
    }
}
