package de.rathfelix.game.entities;

public class Entity {

    private Stats stats;

    public Stats getStats() {
        return stats;
    }

    public Entity() {
        stats = new Stats();
    }
}
