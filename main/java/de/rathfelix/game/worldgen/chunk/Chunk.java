package de.rathfelix.game.worldgen.chunk;

import de.rathfelix.engine.debug.Debug;
import de.rathfelix.engine.mesh.MeshBase;
import de.rathfelix.engine.objects.Cube;
import de.rathfelix.game.worldgen.Elevation.*;
import de.rathfelix.engine.mesh.ChunkMesh;
import de.rathfelix.engine.texture.Material;
import de.rathfelix.engine.noise.ContinentNoise;
import de.rathfelix.engine.shader.LightLevel;
import de.rathfelix.game.worldgen.Terrain;
import de.rathfelix.game.worldgen.biome.*;
import de.rathfelix.game.worldgen.structure.StructureSpawner;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Chunk class contains all chunk data.
 * Chunk class is creating the terrain.
 * Is handling elevations, cliffs, beaches, waterLevel...
 */

// TODO: Split chunk class and terrain placer into different classes(For cleaner code).
// FIXME: Blocks beneath ocean?!

public class Chunk {

    public static final int SIZE = 16; // Chunk Size in Blocks. 1 Block = 1x1x1x.
    public static final int HEIGHT = 300;
    public static final int RENDER_DIST = 50; // Render distance in Chunks.
    public static final int WATER_LEVEL = 30;
    public static final int BEACH_LEVEL = WATER_LEVEL+(5);
    public static final int SNOW_LEVEL = 250;
    public static final float WATER = 1f;
    public static final float BEACH = WATER + (0.005f * 2);
    public static final float SMOOTH = WATER + (0.005f * 5);

    private final byte[][][] blockData = new byte[SIZE][HEIGHT][SIZE]; // All blocks inside chunk with material id.
    private final byte[][][] lightData = new byte[SIZE][HEIGHT][SIZE]; // All blocks inside chunk with lightLevel.
    private final int chunkX, chunkZ; // Chunk own coordinateLayer(worldCoord / chunk size...).
    private final int worldX, worldZ; // Chunk world coordinates.

    private MeshBase mesh; // Chunk mesh contains the final vertex product for the renderer.
    private Cube cube; // Cube is like 1 block. Cube class can create a block and determine which side to show or not.

    Elevation elevation = Elevation.getInstance(); // World terrain noise elevation system.
    Terrain terrain = Terrain.getInstance();

    public Chunk(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.worldX = chunkX * SIZE;
        this.worldZ = chunkZ * SIZE;
        createChunk();
    }

    private void createChunk() {
        placeBlocks();
    }

    // Generates light levels.
    public void generateLightLevel() {
        LightLevel.lightBlocks(blockData, lightData);
    }

    // At first place all chunk blocks.
    private void placeBlocks() {

        float[][] heightMap = new float[SIZE + 1][SIZE + 1];
        for (int x = 0; x <= SIZE; x++) {
            for (int z = 0; z <= SIZE; z++) {
                heightMap[x][z] = elevation.getHeight(x + worldX, z + worldZ);
            }
        }

        for (int x = 0; x < SIZE; x++) {
            for (int z = 0; z < SIZE; z++) {
                EElevation elevationEnum = EElevation.getElevationEnumAt(x+worldX, z+worldZ);
                ElevationBase elevationType = elevationEnum.getElevation();
                Debug.log(elevationEnum.getDisplayName());
                BiomeBase biome = BiomeNoise.getBiome(worldX+x, worldZ+z);
                float contLevel = ContinentNoise.continentNoise(x+worldX, z+worldZ);
                float elevY = heightMap[x][z];
                elevY = Math.round(elevY);

                for (int y = 0; y < HEIGHT; y++) {
                    if (isBlock(x, y, z)) continue;

                    if (contLevel <= WATER) {
                        createWater(elevY, x, y, z);
                        continue;
                    }

                    if (contLevel <= BEACH) {
                        createBeach(elevationType, elevY, x, y, z);
                        createRockyBeach(elevationType, elevY, x, y, z);
                        continue;
                    }

                    // steepness
                    float heightCenter = heightMap[x][z];
                    float heightX = heightMap[x + 1][z];
                    float heightZ = heightMap[x][z + 1];
                    float dx = heightX - heightCenter;
                    float dz = heightZ - heightCenter;
                    float steepnessSq = dx*dx + dz*dz;

                    createLand(steepnessSq, elevY, elevationType, biome, x, y, z);
                }
            }
        }
    }

    // Create chunk mesh
    public void buildMesh() {
        if (mesh == null) {
            try {
                mesh = new ChunkMesh(
                        cube.getPositionArray(),
                        cube.getTextureCoordArray(),
                        cube.getVertexIndexArray(),
                        cube.getTextureIndexArray(),
                        cube.getBrightnessArray(),
                        cube.getLightLevelArray()
                );
            } catch (NullPointerException e) {
            }
        }
    }

    // Create normal sand beach, place blocks.
    private void createBeach(ElevationBase elevationType, float elevY, int x, int y, int z) {
        if (elevationType instanceof ElevationPlains) {
            // Surface block placer
            if (y == elevY) {
                addBlock(x, y, z, Material.SAND.getId());
                return;
            }

            // Underground block placer
            if (y < elevY)
                addBlock(x, y, z, Material.STONE.getId());
        }
    }

    // Create rocky/cliffs beach, place blocks.
    private void createRockyBeach(ElevationBase elevationType, float elevY, int x, int y, int z) {
        if (elevationType instanceof ElevationPeak || elevationType instanceof ElevationHills) {
            if (y == elevY) {
                // Make lowes layer sands
                if (y == WATER_LEVEL)
                    addBlock(x, y, z, Material.WATER.getId());
            } else if (y < elevY) {
                addBlock(x, y, z, Material.STONE.getId());
            }
        }
    }

    private void createLand(float steepness, float elevY, ElevationBase elevation,
                            BiomeBase biome, int x, int y, int z) {

        if (y == 0) {
            addBlock(x, y, z, Material.STONE.getId());
        } else if (y <= elevY) {

            if (y == elevY) {
                if (steepness > 2f) {
                    addBlock(x, y, z, Material.STONE.getId());
                } else {
                    if (!(biome instanceof DesertBiome)) {
                        spawnStructures(x, y, z);
                    }

                    int randomI = ThreadLocalRandom.current().nextInt(7);
                    if (y > (SNOW_LEVEL) && steepness < 1.3) {
                        addBlock(x, y, z, Material.SNOW.getId());
                        return;
                    } else if (y > (SNOW_LEVEL - randomI) - 20 && steepness < 1) {
                        addBlock(x, y, z, Material.SNOW.getId());
                        return;
                    }

                    if (elevation.hasBiome())
                        addBlock(x, y, z, elevation.getBiome().getTopMaterial().getId());
                    else
                        addBlock(x, y, z, biome.getTopMaterial().getId());
                }
            } else {
                if (elevation.hasBiome())
                    addBlock(x, y, z, elevation.getBiome().getBotMaterial().getId());
                else
                    addBlock(x, y, z, biome.getBotMaterial().getId());
            }
        }
    }

    // Spawn structures for normal terrain.
    private void spawnStructures(int x,  int y, int z) {
        if (y > 150) return;
        int randomI = ThreadLocalRandom.current().nextInt(500);
        if (randomI == 2) {
            StructureSpawner.spawnStructure(this, StructureSpawner.getOakTreeStructure(), x, y, z);
        } else if (randomI == 3) {
            StructureSpawner.spawnStructure(this, StructureSpawner.getBirchTreeStructure(), x, y, z);

        }
    }

    // Create ocean if height is water level.
    private void createWater(float elevY, int x, int y, int z) {
        if (y > 0 && y <= WATER_LEVEL)
            addBlock(x, y, z, Material.WATER.getId());
        else if (y == 0)
            addBlock(x, y, z, Material.SAND.getId());
    }

    // Getter Setter
    public boolean isReady() {
        if (lightData.length != 0 && blockData.length != 0 && cube != null && mesh != null)
            return true;
        return false;
    }
    public MeshBase getMesh() {
        return mesh;
    }
    public void setMesh(ChunkMesh mesh) {
        this.mesh = mesh;
    }

    public byte[][][] getBlockData() {
        return blockData;
    }


    public int getWorldX() {
        return chunkX * SIZE;
    }
    public int getWorldZ() {
        return chunkZ * SIZE;
    }
    public ChunkCoord getChunkCoord() {
        return new ChunkCoord(chunkX, chunkZ);
    }


    public Cube getCube() {
        return cube;
    }
    public void setCube(Cube cube) {
        this.cube = cube;
    }

    public float getLightLevel(int x, int y, int z) {
        return lightData[x][y][z];
    }

    // Override contains equals...
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof Chunk) {
            Chunk other = (Chunk) o;
            return this.getChunkCoord().getChunkX() == other.getChunkCoord().getChunkX()
                    && this.getChunkCoord().getChunkZ() == other.getChunkCoord().getChunkZ();
        }

        if (o instanceof ChunkCoord) {
            ChunkCoord other = (ChunkCoord) o;
            return this.getChunkCoord().getChunkX() == other.getChunkX()
                    && this.getChunkCoord().getChunkZ() == other.getChunkZ();
        }

        return false;
    }

    // Add block to the final blockData 3D Array.
    public void addBlock(int x, int y, int z, byte materialId) {
        blockData[x][y][z] = materialId;
    }
    private Byte getBlock(int x, int y, int z) {
        return blockData[x][y][z];
    }
    private boolean isBlock(int x, int y, int z) {
        if(blockData[x][y][z] == 0) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(getChunkCoord().getChunkX());
        result = 31 * result + Integer.hashCode(getChunkCoord().getChunkZ());
        return result;
    }


}
