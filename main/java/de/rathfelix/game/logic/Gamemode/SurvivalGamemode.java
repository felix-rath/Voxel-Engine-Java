package de.rathfelix.game.logic.Gamemode;

import com.bulletphysics.dynamics.RigidBody;
import de.rathfelix.game.entities.player.Player;
import de.rathfelix.game.logic.MovementMode.GroundMovemode;
import de.rathfelix.game.logic.Physics.PlayerCollisionChunkLogic;

import javax.vecmath.Vector3f;

public class SurvivalGamemode implements IGamemode{

    private final float GRAVITY = -8.91f;

    @Override
    public void loadMode() {
        Player player = Player.getPlayer();
        RigidBody rb = player.getRigidbody();

        rb.setLinearVelocity(new Vector3f());
        rb.setGravity(new Vector3f(0, GRAVITY, 0));
        player.getMovemodeManager().setCurrentMode(new GroundMovemode());
        PlayerCollisionChunkLogic.getInstance().start();
    }
}
