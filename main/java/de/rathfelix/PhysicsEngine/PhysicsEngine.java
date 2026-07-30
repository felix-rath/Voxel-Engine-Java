package de.rathfelix.PhysicsEngine;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CapsuleShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.Vector3f;

public class PhysicsEngine {

    private DynamicsWorld dynamicsWorld;

    private final int MAX_SUB_STEPS = 5;
    private final float GRAVITY = 8.91f; // Normal = -8.91f
    private final static Vector3f PLAYER_HITBOX = new Vector3f(0.5f, 0.5f, 0.5f);
    private final static float FRICTION = 0.2f;
    private final static int MASS = 1;
    private final static float ANGULAR_DAMPING = 0.1f;
    private final static Vector3f startPosition = new Vector3f(0, 50, 0);

    public PhysicsEngine() {
        // Broadphase looking for collision pairs
        BroadphaseInterface broadphase = new DbvtBroadphase();

        // Collision config
        CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);

        // Constraint solver (movement)
        SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();

        // Dynamic world creation
        dynamicsWorld = new DiscreteDynamicsWorld(
                dispatcher,
                broadphase,
                solver,
                collisionConfiguration
        );

        // Set gravity
        dynamicsWorld.setGravity(new Vector3f(0, -GRAVITY, 0));
    }

    public DynamicsWorld getWorld() {
        return dynamicsWorld;
    }

    public void update(float deltaTime) {
        dynamicsWorld.stepSimulation(deltaTime, MAX_SUB_STEPS);
    }

    public static RigidBody createPlayerCollider() {
        // Collider form capsule
        CollisionShape box = new BoxShape(PLAYER_HITBOX);

        // Start position
        Transform startTransform = new Transform();
        startTransform.setIdentity();
        startTransform.origin.set(startPosition);

        // Mass > 0 = dynamic body
        Vector3f inertia = new Vector3f(0, 0, 0);
        box.calculateLocalInertia(MASS, inertia);

        // Motion state connects JBullet with my engine
        DefaultMotionState motionState = new DefaultMotionState(startTransform);

        // Create body
        RigidBodyConstructionInfo info = new RigidBodyConstructionInfo(MASS, motionState, box, inertia);

        // Daming (drag)
        RigidBody rb = new RigidBody(info);
        //rb.setDamping(0, ANGULAR_DAMPING);
        rb.setAngularFactor(0); // Freeze auto rotation of body. dont fall
        rb.setFriction(FRICTION); // Object reibung
        rb.setRestitution(0); // Bounciness
        return rb;
    }
}
