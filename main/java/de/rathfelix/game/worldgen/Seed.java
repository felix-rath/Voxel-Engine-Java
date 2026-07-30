package de.rathfelix.game.worldgen;

public class Seed {

    private static int seed = 1;

    public static int getSeed() {
        return seed;
    }

    public static void setSeed(int seed) {
        Seed.seed = seed;
    }
}
