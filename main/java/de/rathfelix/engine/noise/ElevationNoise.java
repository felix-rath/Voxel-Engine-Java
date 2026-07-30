package de.rathfelix.engine.noise;


import de.rathfelix.game.worldgen.Seed;

public class ElevationNoise {

    private static FastNoiseLite noise;

    static {
        createNoise();
    }

    private static void createNoise() {
        noise = new FastNoiseLite(Seed.getSeed());
        noise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        noise.SetFrequency(0.001f);

        //noise.SetFractalType(FastNoiseLite.FractalType.FBm);
        //noise.SetFractalOctaves(3);
    }

    // Getter Setter
    public static float getNoise(int x, int z) {
        float value = noise.GetNoise(x, z);
        value = (value+1) / 2;
        return value;
    }
}
