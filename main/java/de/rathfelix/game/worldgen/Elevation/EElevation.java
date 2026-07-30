package de.rathfelix.game.worldgen.Elevation;

import de.rathfelix.engine.noise.ElevationNoise;

public enum EElevation {
    PLAINS(0, "Plains"),
    HILLS(1, "Hills"),
    PEAKS(2, "Peaks");

    private final int id;
    private final String displayName;

    private static ElevationBase[] elevationList = {
            new ElevationPlains(0, 0.4f),
            new ElevationHills(0.4f, 0.5f),
            new ElevationPeak(0.5f, 1.0f)
    };

    EElevation(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    //Getter Setter
    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ElevationBase getElevation() {
        return elevationList[id];
    }

    public ElevationBase getDownElevation() {
        ElevationBase downType = elevationList[id-1];
        if (downType != null) return downType;
        else return elevationList[id];
    }

    // Statics
    public static ElevationBase getElevationById(int id) {
        return elevationList[id];
    }

    public static ElevationBase[] getElevationList() {
        return elevationList;
    }

    public static EElevation getElevationEnumAt(int x, int z) {
        for (EElevation e : values()) {
            ElevationBase elevation = e.getElevationById(e.getId());
            float noiseValue = ElevationNoise.getNoise(x, z);
            if (noiseValue >= elevation.getBotValue() && noiseValue <= elevation.getTopValue()) {
                return e;
            }
        }
        return null;
    }


}
