package de.rathfelix.engine.noise;

import de.rathfelix.game.worldgen.Seed;

public class ContinentNoise {

    public static float continentNoise(int x, int z) {
        FastNoiseLite noise = new FastNoiseLite(Seed.getSeed());
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
        noise.SetFrequency(0.00005f);

        noise.SetFractalType(FastNoiseLite.FractalType.FBm);
        noise.SetFractalOctaves(6);
        float value = -noise.GetNoise(x, z);
        value = (value+1) / 2;
        return value * 2;
    }

}
