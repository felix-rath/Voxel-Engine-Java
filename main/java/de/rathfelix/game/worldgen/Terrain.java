package de.rathfelix.game.worldgen;

import de.rathfelix.game.worldgen.Elevation.Elevation;
import de.rathfelix.game.worldgen.Elevation.ElevationBase;

public class Terrain {
    /**
     * Main klasse für terrain.
     * getBlockAt(worldX, y, worldZ) gibt block an dieser position.
     * Berechnet Wasser, Ocean, Kontinenten-Noise, usw...
     * Damit ich alles an einem Ort habe.
     */

    // TODO: NVM FRAG CHAT GPT NACH CHUNNK STATES VIEL GEILER.
    private static Terrain instance;

    //public ElevationBase getElevation(int xWorld, int worldZ) {
    //    elevation = Elevation.getInstance();
    //    float elevationIndex = elevation.getElevationLevel(xWorld, worldZ);
    //    ElevationBase currentElevation = Elevation.elevationTypes.get(Math.round(elevationIndex));
    //    return currentElevation;
    //}

    // Checks if there is a block or not.
    public boolean getBlock(int worldX, int worldY, int worldZ) {
        float elvY = Elevation.getInstance().getHeight(worldX, worldZ);
        elvY = Math.round(elvY);

        if (worldY <= elvY) {
            return true;
        }
        return false;
    }

    public static Terrain getInstance() {
        if (instance == null) instance = new Terrain();
        return instance;
    }
}
