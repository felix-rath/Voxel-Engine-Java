package de.rathfelix.game.logic.MovementMode;

import com.bulletphysics.dynamics.RigidBody;
import de.rathfelix.Util.MoveUtils;
import de.rathfelix.engine.input.KeyboardInput;
import de.rathfelix.engine.math.Time;
import de.rathfelix.game.entities.player.Player;

import javax.vecmath.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class GroundMovemode implements IMovemode {

    private final int MOVE_SPEED = 4;
    private final int SPRINT_SPEED = 8;
    private final int ACCELERATION_MULTIPLIER = 10;
    private final int JUMP_FORCE = 5;
    private final float JUMP_COOLDOWN = 0.4f;
    private final float DRAG = 0.1f;

    private boolean isSprinting = false;
    private boolean canJump = false;
    private boolean isGrounded = false;
    private boolean lastGroundedAir = true;

    private org.joml.Vector3f moveDir = new org.joml.Vector3f();
    private float jumpTimer = 0;
    private int moveSpeed;

    private RigidBody rb;

    @Override
    public void loadMode() {
        rb = Player.getPlayer().getRigidbody();
    }

    @Override
    public void update() {
        isGrounded = MoveUtils.updateGroundCheck(rb);
        updateMovePlayer();
        if (isGrounded)
            MoveUtils.updateDrag(rb, DRAG);
        MoveUtils.updateSpeedControl(rb, moveSpeed);
        updateJumpTimer();
    }

    @Override
    public void input() {
        handleMoveInput();
        handleSprintInput();
        handleJumpInput();
    }

    private void updateMovePlayer() {
        if (isSprinting) {
            moveSpeed = SPRINT_SPEED;
            moveDir.mul(moveSpeed * ACCELERATION_MULTIPLIER);
            org.joml.Vector3f newVec = MoveUtils.vecToCamVec(rb, moveDir.x, moveDir.y, moveDir.z);
            rb.applyCentralForce(new Vector3f(newVec.x, newVec.y, newVec.z));
        } else {
            moveSpeed = MOVE_SPEED;
            moveDir.mul(moveSpeed * ACCELERATION_MULTIPLIER);
            org.joml.Vector3f newVec = MoveUtils.vecToCamVec(rb, moveDir.x, moveDir.y, moveDir.z);
            rb.applyCentralForce(new Vector3f(newVec.x, newVec.y, newVec.z));
        }
    }

    private void jump() {
        // Reset y for norm jump
        Vector3f flatVec = new Vector3f();
        rb.getLinearVelocity(flatVec);
        flatVec.y = 0;
        rb.setLinearVelocity(flatVec);

        // Jump vector impulse
        Vector3f jumpVec = new Vector3f(0, JUMP_FORCE, 0);
        rb.applyCentralImpulse(jumpVec);
        canJump = false;
    }

    private void updateJumpTimer() {
        if (!canJump) {
            jumpTimer += Time.getDeltaTime();
            if (jumpTimer >= JUMP_COOLDOWN && isGrounded) {
                canJump = true;
                jumpTimer = 0;
            }
        }
    }

    private void handleMoveInput() {
        moveDir.set(0,0,0);
        if (KeyboardInput.isKeyPressed(GLFW_KEY_W)) moveDir.z = -1;
        if (KeyboardInput.isKeyPressed(GLFW_KEY_S)) moveDir.z = 1;
        if (KeyboardInput.isKeyPressed(GLFW_KEY_A)) moveDir.x = -1;
        if (KeyboardInput.isKeyPressed(GLFW_KEY_D)) moveDir.x = 1;
    }

    private void handleSprintInput() {
        isSprinting = (KeyboardInput.isKeyPressed(GLFW_KEY_LEFT_CONTROL));
    }

    private void handleJumpInput() {
        if (!canJump) return;
        if (KeyboardInput.isKeyPressed(GLFW_KEY_SPACE)) jump();
    }

    // Getter Setter
    public boolean isSprinting() {
        return isSprinting;
    }

}
