package de.rathfelix.game.worldgen.chunk;
import de.rathfelix.engine.texture.Material;
import de.rathfelix.engine.objects.Cube;
import de.rathfelix.exceptions.ChunkUnloadException;
import de.rathfelix.game.worldgen.Terrain;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
/* Creates all the faces for the chunk/mesh.
 *
 */
// FIXME: Holes between chunks(class is trying to check chunks that don't exist).
public class ChunkLoader implements Runnable{
    private static ChunkLoader instance;
    private Thread chunkLoaderThread;
    private Queue<Chunk> chunksToLoad = new ConcurrentLinkedQueue<>();
    private Queue<Chunk> chunksToRender = new ConcurrentLinkedQueue<>();
    // Performance optimization
    private static final int BASE_SLEEP_MS = 30; // Thread sleep time
    Terrain terrain = Terrain.getInstance();
    private ChunkLoader() {
        chunkLoaderThread = new Thread(this, "CHUNK_LOADER");
        chunkLoaderThread.start();
    }
    @Override // Thread run Method.
    public void run() {
        chunkLoaderLoop();
    }
    // Logic
    public void chunkLoaderLoop() {
        while (true) {
            setNextChunk(); // Prepare the chunk
            try {
                Thread.sleep(BASE_SLEEP_MS);
            } catch (InterruptedException e) {
                throw new ChunkUnloadException(e.getMessage());
            }
        }
    }
    public void setNextChunk() {
        Chunk chunk;
        while ((chunk = chunksToLoad.poll()) != null) {
            if (chunk.getCube() == null) {
                createCube(chunk);
            }
            chunksToRender.add(chunk);
        }
    }
    // Create faces for mesh/renderer.
    private void createCube(Chunk chunk) {
        Cube cube = new Cube();
        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int z = 0; z < Chunk.SIZE; z++) {
                for (int y = 0; y < Chunk.HEIGHT; y++) {
                    byte blockId = chunk.getBlockData()[x][y][z];
                    Material material = Material.getById(blockId);
                    if (material == Material.AIR) continue;
                    cube.setPos(x + chunk.getWorldX(), y, z + chunk.getWorldZ());
                    // Top (+Y)
                    checkAndAddFace(chunk, x, y, z, 0, 1, 0, material, cube::addTopFace);
                    // Front (-Z)
                    checkAndAddFace(chunk, x, y, z, 0, 0, 1, material, cube::addFrontFace);
                    // Back (+Z)
                    checkAndAddFace(chunk, x, y, z, 0, 0, -1, material, cube::addBackFace);
                    // Left (-X)
                    checkAndAddFace(chunk, x, y, z, -1, 0, 0, material, cube::addLeftFace);
                    // Right (+X)
                    checkAndAddFace(chunk, x, y, z, 1, 0, 0, material, cube::addRightFace);
                    // Bottom (-Y)
                    checkAndAddFace(chunk, x, y, z, 0, -1, 0, material, cube::addBottomFace);
                }
            }
        }
        chunk.setCube(cube);
    }
    // Place online open faces.
    private void checkAndAddFace(Chunk chunk,
                                 int x, int y, int z,
                                 int offsetX, int offsetY, int offsetZ,
                                 Material currentMaterial,
                                 BiConsumer<Material, Float> addFaceMethod) {
        // addFaceMethod.accept(currentMaterial, chunk.getLightLevel(x, y, z));
        int nX = x + offsetX;
        int nY = y + offsetY;
        int nZ = z + offsetZ;
        boolean insideChunk = nX >= 0 && nX < Chunk.SIZE &&
                nY >= 0 && nY < Chunk.HEIGHT &&
                nZ >= 0 && nZ < Chunk.SIZE;
        if (insideChunk) {
            if (chunk.getBlockData()[nX][nY][nZ] == Material.AIR.getId() || Material.getById(chunk.getBlockData()[nX][nY][nZ]).isTransparent()) {
                addFaceMethod.accept(currentMaterial, chunk.getLightLevel(x, y, z));
            }
        } else {
            if (!terrain.getBlock(nX+ chunk.getWorldX(), nY, nZ+ chunk.getWorldZ())) {
                addFaceMethod.accept(currentMaterial, chunk.getLightLevel(x, y, z));
            }
        }
    }
    // Removes chunks for chunkUnloader
    public synchronized void removeChunkInQue(Chunk chunk) {
        chunksToLoad.remove(chunk);
        chunksToRender.remove(chunk);
    }
    // Getter Setter
    public static ChunkLoader getInstance() { // Singleton
        if (instance == null) instance = new ChunkLoader();
        return instance;
    }
    public int getChunksToLoadSize() {
        return chunksToLoad.size();
    }
    public int getChunksToRenderSize() {
        return chunksToRender.size();
    }
    public void addChunkToLoad(Chunk chunk) {
        chunksToLoad.add(chunk);
    }
    //
    // For render gameloop bc opengl context
    public void render() {
        Chunk chunk;
        while (((chunk = chunksToRender.poll()) != null)) {
            if (chunk.getMesh() == null)
                chunk.buildMesh();
            ChunkHolder.addChunkItem(chunk);
        }
    }
}