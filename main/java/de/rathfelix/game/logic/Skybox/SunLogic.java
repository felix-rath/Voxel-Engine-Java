package de.rathfelix.game.logic.Skybox;

import de.rathfelix.engine.camera.Camera;
import de.rathfelix.engine.input.MouseInput;
import de.rathfelix.engine.mesh.MeshBase;
import de.rathfelix.engine.mesh.SunMesh;
import de.rathfelix.engine.objects.GameItem;
import de.rathfelix.engine.texture.Texture;
import de.rathfelix.game.logic.GameLogic;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class SunLogic extends GameLogic {

    private List<GameItem> renderList = new ArrayList<>();

    private final int RADIUS = 1000;
    private final int SCALE = 120;
    private final float SPEED = 0.05f;

    private Camera cam;
    private GameItem sunItem;
    private GameItem moonItem;
    private Texture sunTexture;
    private Texture moonTexture;

    private float degree;

    @Override
    public void init() throws Exception {
        this.sunTexture = new Texture("/textures/skybox/sun_texture.png");
        this.moonTexture = new Texture("/textures/skybox/moon_texture.png");
        this.sunItem = new GameItem();
        this.moonItem = new GameItem();
        this.cam = Camera.getInstance();
        createSun();
        createMoon();
    }

    @Override
    public void update(MouseInput mouseInput) {
        updateSun();
        updateMoon();
        updateDegree();
    }

    //
    private void updateSun() {
        radialMove(sunItem, RADIUS);
        lookAtCam(sunItem);
    }

    private void updateMoon() {
        radialMove(moonItem, -RADIUS);
        lookAtCam(moonItem);
    }

    private void updateDegree() {
        degree += SPEED;
        if (degree >= 360) degree = 0;
    }

    //
    private void createSun() {
        MeshBase sunMesh = new SunMesh(positions(), textureCoords(), indices(), sunTexture);
        sunItem.setMesh(sunMesh);
        sunItem.setScale(SCALE);
        sunItem.setMesh(sunMesh);
        renderList.add(sunItem);
    }

    private void createMoon() {
        MeshBase moonMesh = new SunMesh(positions(), textureCoords(), indices(), moonTexture);
        moonItem.setMesh(moonMesh);
        moonItem.setScale(SCALE);
        moonItem.setMesh(moonMesh);
        renderList.add(moonItem);
    }

    //
    private void radialMove(GameItem item, int radius) {
        Vector3f itemPos = item.getPosition();
        double theta = Math.toRadians(degree);
        item.setPosition((float) (radius * Math.cos(theta)), (float) (radius * Math.sin(theta)) , itemPos.z);
    }

    private void lookAtCam(GameItem item) {
        Vector3f camRot = cam.getRotation();
        Vector3f newRot = new Vector3f();

        newRot.y = (camRot.y - 90);
        newRot.z = camRot.x;
        item.setRotation(newRot.x, newRot.y, newRot.z);
    }

    // Create billboard mesh
    private float[] positions() {
        float size = 0.5f;
        return new float[] {
                0f,  size,  size,   // Vertex 0 (oben rechts)
                0f,  size, -size,   // Vertex 1 (oben links)
                0f, -size, -size,   // Vertex 2 (unten links)
                0f, -size,  size    // Vertex 3 (unten rechts)
        };
    }


    private int[] indices() {
        return new int[] {
                0, 1, 2,  // Erstes Dreieck
                2, 3, 0   // Zweites Dreieck
        };
    }

    private float[] textureCoords() {
        return new float[] {
                0f, 0f,  // Vertex 0
                1f, 0f,  // Vertex 1
                1f, 1f,  // Vertex 2
                0f, 1f   // Vertex 3
        };
    }

    // Getter Setter
    public List<GameItem> getRenderList() {
        return renderList;
    }
}
