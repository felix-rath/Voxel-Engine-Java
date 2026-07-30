package de.rathfelix.game.worldgen.biome;

import de.rathfelix.engine.noise.FastNoiseLite;
import de.rathfelix.game.worldgen.Seed;

import java.util.ArrayList;
import java.util.List;

public class BiomeNoise {

    private static List<BiomeBase> biomeList = new ArrayList<>();

    private static FastNoiseLite biomeNoise;

    static {
        createNoise();
    }

    public static BiomeBase getBiome(float worldX, float worldZ) {
        int biomeValue = Math.round(getNoise(worldX, worldZ)); // z. B. 0..2
        return biomeList.get(biomeValue);
    }


    private static void createNoise(){
        biomeNoise = new FastNoiseLite(Seed.getSeed());
        biomeNoise.SetFrequency(0.001f);
        biomeNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);

        biomeNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        biomeNoise.SetFractalOctaves(6);
    }

    // Getter Setter
    private static float getNoise(float x, float z) {
        float height = biomeNoise.GetNoise(x, z);
        // Normalize
        height = (height + 1) / 2;
        height *= biomeList.size()-1;
        return height;
    }

    public static void addBiome(BiomeBase biome) {
        biomeList.add(biome);
    }
}
