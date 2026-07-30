package de.rathfelix.game.worldgen.structure;

import de.rathfelix.engine.texture.Material;

public class OakTreeStructure extends StructureBase {

    public OakTreeStructure() {
        int center = getSize() / 2; // Mitte = 4 bei Größe 9

        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                for (int z = 0; z < getSize(); z++) {
                    setBlock(x, y, z, Material.AIR.getId());
                }
            }
        }

        // Stamm in der Mitte
        for (int y = 0; y < 5; y++) {
            this.setBlock(center, y, center, Material.WOOD_OAK.getId());
        }

        int radius = 3;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    double distance = Math.sqrt(dx*dx + dy*dy + dz*dz);
                    if (distance <= radius) {
                        // Verschiebe Koordinaten in den Array-Bereich
                        int ax = center + dx;
                        int ay = 5 + dy;
                        int az = center + dz;

                        if (ay >= 0 && ay < getSize()) {
                            if (getBlock(ax, ay, az) == Material.WOOD_OAK.getId()) continue;
                            this.setBlock(ax, ay, az, Material.LEAVE_OAK.getId());
                        }
                    }
                }
            }
        }
    }

    @Override
    void structurePropeties() {

    }
}

