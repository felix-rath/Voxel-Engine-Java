package de.rathfelix.game.logic;

import de.rathfelix.engine.Window;
import de.rathfelix.engine.hud.Hud;
import de.rathfelix.engine.camera.Camera;
import de.rathfelix.game.entities.player.Player;
import de.rathfelix.game.worldgen.chunk.ChunkHolder;

import javax.vecmath.Vector3f;

public class InfoHudLogic extends GameLogic {

    private Hud positionHud;
    private Hud activeChunkHud;
    private Hud speedHud;

    private Vector3f playerVel = new Vector3f();

    @Override
    public void init() throws Exception {
        createPositionHud();
        createChunksHud();
        createSpeedHud();
    }

    @Override
    public void render(Window window) {
        renderPositionHud();
        renderSpeedHud();
    }


    private void createPositionHud() {
        positionHud = new Hud("Coords: " + Camera.getInstance().getPosition());
        positionHud.updateSize(Window.getInstance());
        Hud.getGameItems().get(0).setPosition(0, 0, 0);
        Hud.getGameItems().get(0).setScale(0.4f);
    }

    private void renderPositionHud() {
        org.joml.Vector3f pos = Camera.getInstance().getPosition();
        positionHud.setStatusText("x: " + Math.round(pos.x) + " y: " + Math.round(pos.y) + " z: " + Math.round(pos.z));
        activeChunkHud.setStatusText("chunks: " + ChunkHolder.chunkItemList.size());
    }

    private void createChunksHud() {
        activeChunkHud = new Hud("chunks: " + ChunkHolder.chunkItemList.size());
        activeChunkHud.updateSize(Window.getInstance());
        Hud.getGameItems().get(1).setScale(0.4f);
        Hud.getGameItems().get(1).setPosition(0, 20, 0);
    }

    private void createSpeedHud() {
        speedHud = new Hud("speed: " + Math.round(playerVel.length()));
        speedHud.updateSize(Window.getInstance());
        Hud.getGameItems().get(2).setScale(0.4f);
        Hud.getGameItems().get(2).setPosition(0, 40, 0);
    }

    private void renderSpeedHud() {
        playerVel = new javax.vecmath.Vector3f();
        Player.getPlayer().getRigidbody().getLinearVelocity(playerVel);
        playerVel.y = 0;
        speedHud.setStatusText("speed: " + Math.round(playerVel.length()));
    }
}
