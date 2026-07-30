package de.rathfelix.engine.noise;

import de.rathfelix.game.worldgen.Seed;

public class UnderGroundNoise {

    private static UnderGroundNoise instance;

    private final FastNoiseLite noise;
    private final float startValue = 0.7f; // everything bellow will start underground.

    private UnderGroundNoise() {
        noise = new FastNoiseLite(Seed.getSeed());
        noiseSetup();
    }

    private void noiseSetup() {
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        noise.SetFrequency(0.015f);
        noise.SetFractalType(FastNoiseLite.FractalType.FBm);
        noise.SetFractalOctaves(3);
    }

    // Getter Setter
    public static UnderGroundNoise getInstance() {
        if (instance == null) instance = new UnderGroundNoise();
        return instance;
    }

    public float getNoise(int x, int y, int z) {
        return noise.GetNoise(x, y, z);
    }

    public float getStartValue() {
        return startValue;
    }
}
