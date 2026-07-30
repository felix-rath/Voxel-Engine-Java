package de.rathfelix.engine.texture;

public enum Material {
    AIR((byte) 0, "Air", true, false),
    DIRT((byte) 1, "Grass", false, true),
    GRASS((byte) 2, "Grass", false, true),
    STONE((byte) 3, "Stone", false, true),
    SAND((byte) 4, "Sand", false, true),
    WATER((byte) 5, "Water", false, false),
    WOOD_OAK((byte) 6, "Wood Oak", false, true),
    LEAVE_OAK((byte) 7, "Leave Oak", true, true),
    SNOW((byte) 8, "Snow", false, true),
    WOOD_BIRCH((byte) 9, "Wood Birch", false, true),
    LEAVE_BIRCH((byte) 10, "Leave Birch", true, true);

    private final byte id;
    private final String displayName;
    private final boolean transparent;
    private final boolean collision;

    Material(byte id, String displayName, boolean transparent, boolean collision) {
        this.id = id;
        this.displayName = displayName;
        this.transparent = transparent;
        this.collision = collision;
    }

    public byte getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Get Material by id.
    public static Material getById(byte id) {
        for (Material mat : values()) {
            if (mat.getId() == id) {
                return mat;
            }
        }
        return null; // Default id air.
    }

    // Get Material by Name.
    public static Material getByName(String name) {
        for (Material mat : values()) {
            if (mat.name().equalsIgnoreCase(name)) {
                return mat;
            }
        }
        return null;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public boolean isCollision() {
        return collision;
    }
}

