package de.rathfelix.game.worldgen.biome;

import de.rathfelix.engine.texture.Material;

/**
 *
 *
 *
 *
 *
 *
 *
 *
 * A chunk will only be decorated if all neighboring chunks have their first pass generation complete.
 *
 * A chunk will only be rendered if it and its' neighbors have been decorated.
 *
 *
 *
 */
public enum EBiome {
    PLAINS(0, "Plains"),
    DESERT(1, "Desert"),
    MOUNTAIN(2, "Mountain"),;

    private final int id;
    private final String displayName;

    public static BiomeBase[] biomeList = {
            new PlainsBiome(0, Material.GRASS, Material.STONE),
            new DesertBiome(1, Material.SAND, Material.STONE),
            new MountainBiome(2, Material.STONE, Material.STONE),
    };

    EBiome(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }


    // Get Biome by id.
    public static EBiome getById(int id) {
        for (EBiome biome : values()) {
            if (biome.getId() == id) {
                return biome;
            }
        }
        return null;
    }

    // Get Biome by Name.
    public static EBiome getByName(String name) {
        for (EBiome biome : values()) {
            if (biome.getDisplayName().equalsIgnoreCase(name)) {
                return biome;
            }
        }
        return null;
    }
}
