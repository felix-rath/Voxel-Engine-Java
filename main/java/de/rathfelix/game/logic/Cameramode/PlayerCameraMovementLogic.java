package de.rathfelix.game.logic.Cameramode;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;
import de.rathfelix.engine.input.MouseInput;
import de.rathfelix.engine.camera.Camera;
import de.rathfelix.game.entities.player.Player;
import de.rathfelix.game.logic.GameLogic;
import org.joml.Vector2f;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class PlayerCameraMovementLogic extends GameLogic {

    private final float MOUSE_SENSITIVITY = 0.1f;
    private final Vector3f CAMERA_OFFSET = new Vector3f(0, 1f, 0);

    private Camera camera;
    private Player player;
    private RigidBody rb;

    @Override
    public void init() throws Exception {
        this.camera = Camera.getInstance();
        this.player = Player.getPlayer();
        this.rb = player.getRigidbody();
    }

    @Override
    public void update(MouseInput mouseInput) {
        updateCamera(mouseInput);
        updateFollowPlayer();
    }

    private void updateCamera(MouseInput mouseInput) {
        Transform transform = new Transform();
        Quat4f quat = new Quat4f();
        rb.getWorldTransform(transform);
        transform.getRotation(quat);

        // Cam rotation
        Vector2f rotVec = mouseInput.getDisplVec();
        quat.x = rotVec.x * MOUSE_SENSITIVITY;
        quat.y = rotVec.y * MOUSE_SENSITIVITY;
        transform.setRotation(quat);
        camera.moveRotation(quat.x, quat.y, quat.z);
    }

    private void updateFollowPlayer() {
        Transform transform = new Transform();
        rb.getWorldTransform(transform);
        Vector3f playerPos = transform.origin;
        playerPos.add(CAMERA_OFFSET);
        Camera.getInstance().setPosition(playerPos.x, playerPos.y, playerPos.z);
    }
}
