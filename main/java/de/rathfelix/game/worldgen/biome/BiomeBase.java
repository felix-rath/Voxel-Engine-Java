package de.rathfelix.game.worldgen.biome;

import de.rathfelix.engine.texture.Material;


public abstract class BiomeBase {

    protected final Material topMaterial;
    protected final Material botMaterial;
    protected final int biomeId;



    public BiomeBase(int biomeId, Material topMaterial, Material botMaterial){
        this.topMaterial = topMaterial;
        this.botMaterial = botMaterial;
        this.biomeId = biomeId;
        BiomeNoise.addBiome(this);
    }

    // Getter Setter
    public Material getTopMaterial() {
        return topMaterial;
    }

    public Material getBotMaterial() {
        return botMaterial;
    }

    public int getId() {
        return  biomeId;
    }

}
