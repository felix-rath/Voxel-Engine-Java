package de.rathfelix.game.logic.MovementMode;

import com.bulletphysics.dynamics.RigidBody;
import de.rathfelix.Util.MoveUtils;
import de.rathfelix.engine.input.KeyboardInput;
import de.rathfelix.game.entities.player.Player;

import javax.vecmath.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class FlyMovemode implements IMovemode {

    private final int MOVE_SPEED = 300;
    private final float DRAG = 0.05f;
    private final int ACCELERATION_MULTIPLIER = 10;

    private org.joml.Vector3f moveDir = new org.joml.Vector3f();
    private int moveSpeed;

    private Player player;
    private RigidBody rb;

    @Override
    public void loadMode() {
        this.player = Player.getPlayer();
        this.rb = player.getRigidbody();
    }

    @Override
    public void update() {
        updateMovePlayer();
        MoveUtils.updateDrag(rb, DRAG);
        MoveUtils.updateSpeedControl(rb, moveSpeed);
    }

    @Override
    public void input() {
        flyInput();
    }

    private void updateMovePlayer() {
        moveSpeed = MOVE_SPEED;
        moveDir.mul(moveSpeed * ACCELERATION_MULTIPLIER);
        org.joml.Vector3f newVec = MoveUtils.vecToCamVec(rb, moveDir.x, moveDir.y, moveDir.z);
        rb.applyCentralForce(new Vector3f(newVec.x, newVec.y, newVec.z));
    }

    private void flyInput() {
        moveDir.set(0,0,0);
        if (KeyboardInput.isKeyPressed(GLFW_KEY_W)) moveDir.z = -1;
        if (KeyboardInput.isKeyPressed(GLFW_KEY_S)) moveDir.z = 1;
        if (KeyboardInput.isKeyPressed(GLFW_KEY_A)) moveDir.x = -1;
        if (KeyboardInput.isKeyPressed(GLFW_KEY_D)) moveDir.x = 1;
        if (KeyboardInput.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) moveDir.y = -1;
        if (KeyboardInput.isKeyPressed(GLFW_KEY_SPACE)) moveDir.y = 1;
    }

}
