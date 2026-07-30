package de.rathfelix.engine.camera;

import de.rathfelix.engine.objects.GameItem;
import org.joml.Intersectionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class CameraBoxSelectionDetector {

    private static CameraBoxSelectionDetector instance;

    private Vector3f dir = new Vector3f();

    private Vector3f min = new Vector3f();
    private Vector3f max = new Vector3f();
    private Vector2f nearFar = new Vector2f();

    public void selectGameItem(GameItem[] gameItems, Camera camera) {
        GameItem selectedGameItem = null;
        float closestDistance = Float.POSITIVE_INFINITY;

        Vector3f camDir = new Vector3f(
                (float) (Math.sin(Math.toRadians(camera.getRotation().y)) * Math.cos(Math.toRadians(camera.getRotation().x))),
                (float) -Math.sin(Math.toRadians(camera.getRotation().x)),
                (float) -(Math.cos(Math.toRadians(camera.getRotation().y)) * Math.cos(Math.toRadians(camera.getRotation().x)))
        );
        dir = camDir.normalize();

        for (GameItem gameItem : gameItems) {
            gameItem.setSelected(false);
            min.set(gameItem.getPosition());
            max.set(gameItem.getPosition());
            min.add(-gameItem.getScale(), -gameItem.getScale(), -gameItem.getScale());
            max.add(gameItem.getScale(), gameItem.getScale(), gameItem.getScale());
            if (Intersectionf.intersectRayAab(camera.getPosition(), dir, min, max, nearFar) && nearFar.x < closestDistance) {
                closestDistance = nearFar.x;
                selectedGameItem = gameItem;
                System.out.println("Teest");
            }
        }

        if (selectedGameItem != null) {
            selectedGameItem.setSelected(true);
        }
    }

    // Getter setter

    public static CameraBoxSelectionDetector getInstance() {
        if (instance == null) instance = new CameraBoxSelectionDetector();
        return instance;
    }
}
