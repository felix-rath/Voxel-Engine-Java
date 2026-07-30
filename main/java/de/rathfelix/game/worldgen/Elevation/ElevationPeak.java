package de.rathfelix.game.worldgen.Elevation;

import de.rathfelix.engine.math.Mathf;
import de.rathfelix.engine.noise.FastNoiseLite;
import de.rathfelix.game.worldgen.biome.EBiome;
import de.rathfelix.game.worldgen.chunk.Chunk;

public class ElevationPeak extends ElevationBase {

    ElevationPeak(float botValue, float topValue) {
        super(botValue, topValue);
    }

    @Override
    public void noiseSetup() {
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
        noise.SetFrequency(0.001f);

        //noise.SetFractalType(FastNoiseLite.FractalType.FBm);
        noise.SetFractalType(FastNoiseLite.FractalType.Ridged);
        noise.SetFractalOctaves(3);

        //noise.SetFractalOctaves(1);
        setNoiseScale(270);
        setBiome(EBiome.biomeList[EBiome.MOUNTAIN.getId()]);
        setMathPow(0.5f);
    }

    @Override
    public float beachSetup(float finalY, float contValue) {
        float t = (contValue - Chunk.WATER) / (Chunk.BEACH - Chunk.WATER);
        t = Mathf.smoothstep(0.95f, 1f, t);
        finalY = Mathf.lerp(Chunk.WATER_LEVEL, Chunk.BEACH_LEVEL, t);
        return finalY;
    }

    @Override
    public float getNoiseSetup(float noiseValue) {
        return noiseValue;
    }

}
