package de.rathfelix.game.worldgen.Elevation;

import de.rathfelix.engine.math.Mathf;
import de.rathfelix.engine.noise.FastNoiseLite;
import de.rathfelix.game.worldgen.biome.EBiome;
import de.rathfelix.game.worldgen.chunk.Chunk;

public class ElevationHills extends ElevationBase{

    ElevationHills(float botValue, float topValue) {
        super(botValue, topValue);
    }

    @Override
    public void noiseSetup() {
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
        noise.SetFrequency(0.003f);

        noise.SetFractalOctaves(2);
        setNoiseScale(100);
        setBiome(EBiome.biomeList[EBiome.MOUNTAIN.getId()]);
        //setMathPow(0.5f);
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
        int step = 10;
        return noiseValue; //Math.round(noiseValue / step) * step;
    }
}
