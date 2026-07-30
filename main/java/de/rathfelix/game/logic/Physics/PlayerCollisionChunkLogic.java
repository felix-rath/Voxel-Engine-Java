package de.rathfelix.game.logic.Physics;

import com.bulletphysics.collision.shapes.BvhTriangleMeshShape;
import com.bulletphysics.collision.shapes.IndexedMesh;
import com.bulletphysics.collision.shapes.TriangleIndexVertexArray;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.Transform;
import de.rathfelix.engine.input.MouseInput;
import de.rathfelix.game.entities.player.Player;
import de.rathfelix.game.logic.GameLogic;
import de.rathfelix.game.worldgen.chunk.Chunk;
import de.rathfelix.game.worldgen.chunk.ChunkCoord;
import de.rathfelix.game.worldgen.chunk.ChunkHolder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class PlayerCollisionChunkLogic extends GameLogic {
    private static PlayerCollisionChunkLogic instance;

    private TriangleIndexVertexArray meshArray;
    private BvhTriangleMeshShape meshShape;
    private RigidBody chunkBody;

    private Chunk currentChunk;
    private List<Chunk> chunkList;

    private boolean isOn = true;

    private Transform pTrans;
    private RigidBody rb;

    @Override
    public void init() throws Exception {
        this.pTrans = new Transform();
        this.rb = Player.getPlayer().getRigidbody();
    }

    @Override
    public void update(MouseInput mouseInput) {
        if (!isOn) return;
        rb.getWorldTransform(pTrans);

        ChunkCoord chunkCoord = new ChunkCoord((int) Math.floor(pTrans.origin.x/Chunk.SIZE), (int) Math.floor(pTrans.origin.z/Chunk.SIZE));
        Chunk newChunk = ChunkHolder.getChunkByCoord(chunkCoord);

        if (newChunk == null || currentChunk == newChunk) return;

        if (currentChunk != null)
            destroy();
        currentChunk = newChunk;
        createChunkCollider();
    }

    private void createChunkCollider() {
        float[] vertices = currentChunk.getCube().getPositionArray();
        int[] indices = currentChunk.getCube().getVertexIndexArray();

        IndexedMesh indexedMesh = new IndexedMesh();
        indexedMesh.numTriangles = indices.length / 3;
        indexedMesh.triangleIndexBase = ByteBuffer.allocateDirect(indices.length * 4)
                .order(ByteOrder.nativeOrder());
        indexedMesh.triangleIndexBase.asIntBuffer().put(indices);

        indexedMesh.numVertices = vertices.length / 3;
        indexedMesh.vertexBase = ByteBuffer.allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder());
        indexedMesh.vertexBase.asFloatBuffer().put(vertices);

        indexedMesh.triangleIndexStride = 3 * 4;
        indexedMesh.vertexStride = 3 * 4;

        meshArray = new TriangleIndexVertexArray();
        meshArray.addIndexedMesh(indexedMesh);

        meshShape = new BvhTriangleMeshShape(meshArray, true);

        // Static body
        RigidBodyConstructionInfo info = new RigidBodyConstructionInfo(0, null, meshShape);

        chunkBody = new RigidBody(info);
        chunkBody.setRestitution(0); // Bounciness

        // Add to world
        Player.getPlayer().physicsEngine.getWorld().addRigidBody(chunkBody);
    }

    public void destroy() {
        Player.getPlayer().physicsEngine.getWorld().removeRigidBody(chunkBody);

        // 2. Bullet-Objekte löschen
        chunkBody.destroy();
        //meshShape.destroy();
        //meshArray.clear();
    }

    public void start() {
        isOn = true;
    }

    public void stop() {
        isOn = false;
        destroy();
    }

    // Setter/getter


    public static PlayerCollisionChunkLogic getInstance() {
        if (instance == null) instance = new PlayerCollisionChunkLogic();
        return instance;
    }
}
