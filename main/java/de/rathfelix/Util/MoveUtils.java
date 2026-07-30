package de.rathfelix.Util;

import com.bulletphysics.collision.dispatch.CollisionWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;
import de.rathfelix.engine.camera.Camera;
import de.rathfelix.game.entities.player.Player;
import org.joml.Quaternionf;

import javax.vecmath.Vector3f;

public class MoveUtils {

    // Convert vec into camera direction.forward vec
    public static org.joml.Vector3f vecToCamVec(RigidBody rb, float offsetX, float offsetY, float offsetZ) {
        org.joml.Vector3f rotation = Camera.getInstance().getRotation();
        org.joml.Vector3f newVec = new org.joml.Vector3f();
        if ( offsetZ != 0 ) {
            rb.activate();
            newVec.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            newVec.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            rb.activate();
            newVec.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            newVec.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        newVec.y += offsetY;
        return newVec;
    }

    // Speed control directly changes the rigidBody velocity.
    public static void updateSpeedControl(RigidBody rb, int moveSpeed) {
        Vector3f vel = new Vector3f();
        rb.getLinearVelocity(vel);
        Vector3f flatVel = new Vector3f(vel.x, 0f, vel.z);

        if (flatVel.length() > moveSpeed) {
            flatVel.normalize();
            flatVel.scale(moveSpeed);
            flatVel.y = vel.y;
            rb.setLinearVelocity(flatVel);
        }
    }

    // Check for ground from playerHeight.
    public static boolean updateGroundCheck(RigidBody rb) {
        Transform transform = new Transform();
        rb.getWorldTransform(transform);
        Vector3f start = transform.origin;

        // Boden etwas unterhalb der Füße prüfen
        Vector3f end = new Vector3f(start);
        end.y -= Player.PLAYER_HEIGHT; // 10 cm unterhalb des Players

        // Callback nur für den nächsten Treffer
        CollisionWorld.ClosestRayResultCallback callback = new CollisionWorld.ClosestRayResultCallback(start, end);

        Player.getPlayer().physicsEngine.getWorld().rayTest(start, end, callback);

        return callback.hasHit();
    }

    // Movement drag/damping
    public static void updateDrag(RigidBody rb, float drag) {
        Vector3f vel = rb.getLinearVelocity(new Vector3f());

        // Linear Damping (Unity-like)
        vel.x -= vel.x * drag;
        vel.y -= vel.y * drag;
        vel.z -= vel.z * drag;

        rb.setLinearVelocity(vel);
    }

}
