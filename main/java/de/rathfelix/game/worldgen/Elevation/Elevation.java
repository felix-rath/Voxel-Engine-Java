package de.rathfelix.game.worldgen.Elevation;

import de.rathfelix.engine.math.Mathf;
import de.rathfelix.engine.noise.ContinentNoise;
import de.rathfelix.engine.noise.ElevationNoise;
import de.rathfelix.engine.noise.FastNoiseLite;
import de.rathfelix.game.worldgen.Terrain;
import de.rathfelix.game.worldgen.chunk.Chunk;

public class Elevation {

    private static Elevation instance;

    private final Terrain terrain;

    private Elevation() {
        this. terrain = Terrain.getInstance();
    }


    public float getHeight(int x, int z) { // final terrain height.
        EElevation elevationEnum = EElevation.getElevationEnumAt(x, z);
        ElevationBase elevationType = elevationEnum.getElevation();
        float elevationValue = ElevationNoise.getNoise(x, z);

        float contValue = ContinentNoise.continentNoise(x, z);

        float finalY = elevationType.getNoise(x, z) + Chunk.WATER_LEVEL;
        if (contValue >= Chunk.WATER && contValue <= Chunk.BEACH) { // Beach creator per elevation type
            return elevationType.beachSetup(finalY, contValue);
        } else if (contValue >= Chunk.BEACH && contValue <= Chunk.SMOOTH){ // Continental inside land smooth
            float t = (contValue - Chunk.BEACH) / (Chunk.SMOOTH - Chunk.BEACH);
            t = Mathf.smoothstep(0f, 1f, t);
            finalY = Mathf.lerp(Chunk.BEACH_LEVEL, finalY, t);
            return finalY;
        }
        return finalY;
    }

    // Getter setter
    public static Elevation getInstance() {
        if (instance == null) instance = new Elevation();
        return instance;
    }
}
