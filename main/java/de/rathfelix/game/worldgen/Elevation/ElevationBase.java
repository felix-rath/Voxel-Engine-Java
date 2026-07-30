package de.rathfelix.game.worldgen.Elevation;

import de.rathfelix.engine.noise.FastNoiseLite;
import de.rathfelix.game.worldgen.Seed;
import de.rathfelix.game.worldgen.biome.BiomeBase;

public abstract class ElevationBase {

    protected final FastNoiseLite noise;

    private int noiseScale;

    private float mathPow = 0;

    private final float BOT_VALUE;
    private final float TOP_VALUE;

    private BiomeBase biome;

    ElevationBase(float botValue, float topValue) {
        this.noise = new FastNoiseLite(Seed.getSeed());
        this.BOT_VALUE = botValue;
        this.TOP_VALUE = topValue;
        noiseSetup();
    }

    public abstract void noiseSetup();
    public abstract float beachSetup(float finalY, float continentValue);
    public abstract float getNoiseSetup(float noiseValue);

    // Getter Setter
    public FastNoiseLite getNoiseType() {
        return noise;
    }
    public int getNoiseScale() {
        return noiseScale;
    }
    public void setNoiseScale(int noiseScale) {
        this.noiseScale = noiseScale;
    }

    public float getNoise(int x, int z) {
        float value = noise.GetNoise(x, z);

        value = (value+1) / 2;

        if (mathPow != 0)
            value = (float) Math.pow(value, mathPow);

        value *= noiseScale;

        value = getNoiseSetup(value);
        return value;
    }

    public BiomeBase getBiome() {
        return biome;
    }

    public void setBiome(BiomeBase biome) {
        this.biome = biome;
    }

    public boolean hasBiome() {
        if (biome == null)
            return false;
        return true;
    }

    public float getBotValue() {
        return BOT_VALUE;
    }

    public float getTopValue() {
        return TOP_VALUE;
    }

    public float getMathPow() {
        return mathPow;
    }

    public void setMathPow(float mathPow) {
        this.mathPow = mathPow;
    }
}
