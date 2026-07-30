package de.rathfelix.game.entities.player;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;
import de.rathfelix.PhysicsEngine.PhysicsEngine;
import de.rathfelix.game.entities.Entity;
import de.rathfelix.game.logic.Gamemode.GamemodeManagerLogic;
import de.rathfelix.game.logic.InfoHudLogic;
import de.rathfelix.game.logic.Cameramode.PlayerCameraMovementLogic;
import de.rathfelix.game.logic.MovementMode.MovemodeManagerLogic;
import de.rathfelix.game.logic.PlayerModelLogic;

public class Player extends Entity {
    private static Player instance;
    public PhysicsEngine physicsEngine;

    private final RigidBody rigidbody;
    private final MovemodeManagerLogic movemodeManager;
    private final PlayerCameraMovementLogic playerCameraMovement;
    private final InfoHudLogic infoHud;
    private final PlayerModelLogic playerModel;
    private final GamemodeManagerLogic gamemodeManager;

    public static final float PLAYER_HEIGHT = 0.5f;

    public Player() {
        this.movemodeManager = new MovemodeManagerLogic();
        this.playerCameraMovement = new PlayerCameraMovementLogic();
        this.infoHud = new InfoHudLogic();
        this.playerModel = new PlayerModelLogic();
        this.gamemodeManager = new GamemodeManagerLogic();

        // Create player physics
        rigidbody = PhysicsEngine.createPlayerCollider();
        physicsEngine = new PhysicsEngine();
        physicsEngine.getWorld().addRigidBody(rigidbody);

        // Read player pos
        Transform transform = new Transform();
        rigidbody.getMotionState().getWorldTransform(transform);
    }












    // Setter Getter
    public static Player getPlayer() {
        if (instance == null) instance = new Player();
        return instance;
    }

    public GamemodeManagerLogic getGamemodeManager() {
        return gamemodeManager;
    }

    public RigidBody getRigidbody() {
        return rigidbody;
    }

    public MovemodeManagerLogic getMovemodeManager() {
        return movemodeManager;
    }

    public InfoHudLogic getInfoHud() {
        return infoHud;
    }
}
