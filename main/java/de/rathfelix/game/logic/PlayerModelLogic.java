package de.rathfelix.game.logic;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;
import de.rathfelix.engine.input.MouseInput;
import de.rathfelix.engine.mesh.MeshBase;
import de.rathfelix.engine.objects.GameItem;
import de.rathfelix.engine.objects.OBJLoader;
import de.rathfelix.game.entities.player.Player;
import org.joml.Vector4f;

public class PlayerModelLogic extends GameLogic {

    private GameItem modelItem;
    private Transform pTransform;
    private RigidBody rb;

    @Override
    public void init() throws Exception {
        this.modelItem = new GameItem();
        this.pTransform = new Transform();
        this.rb = Player.getPlayer().getRigidbody();;

        MeshBase modelMesh = OBJLoader.loadMesh("/models/playerModel.obj");
        modelMesh.setUseTexture(false);
        modelMesh.setColour(new Vector4f(0, 1, 0, 0.5f));
        modelItem.setMesh(modelMesh);
        modelItem.setScale(8);
        GameItem.gameItemList.add(modelItem);
    }

    @Override
    public void update(MouseInput mouseInput) {
        rb.getWorldTransform(pTransform);
        modelItem.setPosition(pTransform.origin.x, pTransform.origin.y-0.5f, pTransform.origin.z);
    }
}
