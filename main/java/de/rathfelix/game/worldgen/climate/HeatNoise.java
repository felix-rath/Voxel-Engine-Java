package de.rathfelix.game.worldgen.climate;

import de.rathfelix.engine.noise.FastNoiseLite;
import de.rathfelix.game.worldgen.Seed;

public class HeatNoise {

    private static HeatNoise instance;

    private FastNoiseLite noise;

    public HeatNoise() {
        noise = new FastNoiseLite(Seed.getSeed());
        noiseSetup();
    }

    private void noiseSetup() {
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
        noise.SetFrequency(0.01f);
    }

    // Getter Setter
    public static HeatNoise getInstance() {
        if (instance == null) instance = new HeatNoise();
        return instance;
    }

    public float getNoise(int x, int z) {
        return noise.GetNoise(x, z);
    }
}
