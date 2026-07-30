package de.rathfelix.engine.objects;

import de.rathfelix.engine.texture.Material;

import java.util.ArrayList;
import java.util.List;

public class Cube {

    private final float TOP_BRIGHT = 1f;
    private final float SIDE_BRIGHT = 0.9f;
    private final float BOTTOM_BRIGHT = 0.8f;

    private List<Float> positionList;
    private List<Float> textureCoordList;
    private List<Integer> vertexIndexList;
    private List<Integer> textureIndexList; // TexIndices the index for the right texture inside the texture array.
    private List<Float> brightnessList;
    private List<Float> lightLevelList; // List what Block selected.

    private float cubeXPos;
    private float cubeYPos;
    private float cubeZPos;

    public Cube() {
        positionList = new ArrayList<>();
        textureCoordList = new ArrayList<>();
        vertexIndexList = new ArrayList<>();
        brightnessList = new ArrayList<>();
        lightLevelList = new ArrayList<>();
        textureIndexList = new ArrayList<>();
    }

    public void addFrontFace(Material material, float lightLevel) {
        int startIndex = positionList.size() / 3;
        // Positions
        positionList.add(-0.5f + cubeXPos); positionList.add(0.5f + cubeYPos); positionList.add(0.5f + cubeZPos);
        positionList.add(-0.5f + cubeXPos); positionList.add(-0.5f + cubeYPos); positionList.add(0.5f + cubeZPos);
        positionList.add(0.5f + cubeXPos);  positionList.add(-0.5f + cubeYPos); positionList.add(0.5f + cubeZPos);
        positionList.add(0.5f + cubeXPos);  positionList.add(0.5f + cubeYPos);  positionList.add(0.5f + cubeZPos);

        // Indices
        vertexIndexList.add(startIndex);     vertexIndexList.add(startIndex + 1); vertexIndexList.add(startIndex + 3);
        vertexIndexList.add(startIndex + 2); vertexIndexList.add(startIndex + 3); vertexIndexList.add(startIndex + 1);

        // Texture Coords
        textureCoordList.add(0f); textureCoordList.add(0f);
        textureCoordList.add(0f); textureCoordList.add(1f);
        textureCoordList.add(1f); textureCoordList.add(1f);
        textureCoordList.add(1f); textureCoordList.add(0f);

        // Face Brightness
        brightnessList.add(SIDE_BRIGHT);
        brightnessList.add(SIDE_BRIGHT);
        brightnessList.add(SIDE_BRIGHT);
        brightnessList.add(SIDE_BRIGHT);

        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);

        // Texture Indices
        int layerIndex = material.getId();
        for (int i = 0; i < 4; i++) textureIndexList.add(layerIndex);
    }

    public void addBackFace(Material material, float lightLevel) {
        int startIndex = positionList.size() / 3;
        positionList.add(0.5f + cubeXPos);  positionList.add(0.5f + cubeYPos);  positionList.add(-0.5f + cubeZPos);
        positionList.add(0.5f + cubeXPos);  positionList.add(-0.5f + cubeYPos); positionList.add(-0.5f + cubeZPos);
        positionList.add(-0.5f + cubeXPos); positionList.add(-0.5f + cubeYPos); positionList.add(-0.5f + cubeZPos);
        positionList.add(-0.5f + cubeXPos); positionList.add(0.5f + cubeYPos);  positionList.add(-0.5f + cubeZPos);

        vertexIndexList.add(startIndex);     vertexIndexList.add(startIndex + 1); vertexIndexList.add(startIndex + 3);
        vertexIndexList.add(startIndex + 2); vertexIndexList.add(startIndex + 3); vertexIndexList.add(startIndex + 1);

        textureCoordList.add(0f); textureCoordList.add(0f);
        textureCoordList.add(0f); textureCoordList.add(1f);
        textureCoordList.add(1f); textureCoordList.add(1f);
        textureCoordList.add(1f); textureCoordList.add(0f);

        // Face Brightness
        brightnessList.add(SIDE_BRIGHT);
        brightnessList.add(SIDE_BRIGHT);
        brightnessList.add(SIDE_BRIGHT);
        brightnessList.add(SIDE_BRIGHT);

        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);

        int layerIndex = material.getId();
        for (int i = 0; i < 4; i++) textureIndexList.add(layerIndex);
    }

    public void addLeftFace(Material material, float lightLevel) {
        int startIndex = positionList.size() / 3;
        positionList.add(-0.5f + cubeXPos); positionList.add(0.5f + cubeYPos);  positionList.add(0.5f + cubeZPos);
        positionList.add(-0.5f + cubeXPos); positionList.add(-0.5f + cubeYPos); positionList.add(0.5f + cubeZPos);
        positionList.add(-0.5f + cubeXPos); positionList.add(-0.5f + cubeYPos); positionList.add(-0.5f + cubeZPos);
        positionList.add(-0.5f + cubeXPos); positionList.add(0.5f + cubeYPos);  positionList.add(-0.5f + cubeZPos);

        vertexIndexList.add(startIndex);     vertexIndexList.add(startIndex + 3); vertexIndexList.add(startIndex + 1);
        vertexIndexList.add(startIndex + 2); vertexIndexList.add(startIndex + 1); vertexIndexList.add(startIndex + 3);

        textureCoordList.add(0f); textureCoordList.add(0f);
        textureCoordList.add(0f); textureCoordList.add(1f);
        textureCoordList.add(1f); textureCoordList.add(1f);
        textureCoordList.add(1f); textureCoordList.add(0f);

        // Face Brightness
        brightnessList.add(SIDE_BRIGHT);
        brightnessList.add(SIDE_BRIGHT);
        brightnessList.add(SIDE_BRIGHT);
        brightnessList.add(SIDE_BRIGHT);

        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);

        int layerIndex = material.getId();
        for (int i = 0; i < 4; i++) textureIndexList.add(layerIndex);
    }

    public void addRightFace(Material material, float lightLevel) {
        int startIndex = positionList.size() / 3;
        positionList.add(0.5f + cubeXPos); positionList.add(0.5f + cubeYPos);  positionList.add(-0.5f + cubeZPos);
        positionList.add(0.5f + cubeXPos); positionList.add(-0.5f + cubeYPos); positionList.add(-0.5f + cubeZPos);
        positionList.add(0.5f + cubeXPos); positionList.add(-0.5f + cubeYPos); positionList.add(0.5f + cubeZPos);
        positionList.add(0.5f + cubeXPos); positionList.add(0.5f + cubeYPos);  positionList.add(0.5f + cubeZPos);

        vertexIndexList.add(startIndex);     vertexIndexList.add(startIndex + 3); vertexIndexList.add(startIndex + 1);
        vertexIndexList.add(startIndex + 1); vertexIndexList.add(startIndex + 3); vertexIndexList.add(startIndex + 2);

        textureCoordList.add(0f); textureCoordList.add(0f);
        textureCoordList.add(0f); textureCoordList.add(1f);
        textureCoordList.add(1f); textureCoordList.add(1f);
        textureCoordList.add(1f); textureCoordList.add(0f);

        // Face Brightness
        brightnessList.add(SIDE_BRIGHT);
        brightnessList.add(SIDE_BRIGHT);
        brightnessList.add(SIDE_BRIGHT);
        brightnessList.add(SIDE_BRIGHT);

        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);

        int layerIndex = material.getId();
        for (int i = 0; i < 4; i++) textureIndexList.add(layerIndex);
    }

    public void addTopFace(Material material, float lightLevel) {
        int startIndex = positionList.size() / 3;
        positionList.add(-0.5f + cubeXPos); positionList.add(0.5f + cubeYPos); positionList.add(0.5f + cubeZPos);
        positionList.add(-0.5f + cubeXPos); positionList.add(0.5f + cubeYPos); positionList.add(-0.5f + cubeZPos);
        positionList.add(0.5f + cubeXPos);  positionList.add(0.5f + cubeYPos); positionList.add(-0.5f + cubeZPos);
        positionList.add(0.5f + cubeXPos);  positionList.add(0.5f + cubeYPos); positionList.add(0.5f + cubeZPos);

        vertexIndexList.add(startIndex);     vertexIndexList.add(startIndex + 3); vertexIndexList.add(startIndex + 1);
        vertexIndexList.add(startIndex + 2); vertexIndexList.add(startIndex + 1); vertexIndexList.add(startIndex + 3);

        textureCoordList.add(0f); textureCoordList.add(0f);
        textureCoordList.add(0f); textureCoordList.add(1f);
        textureCoordList.add(1f); textureCoordList.add(1f);
        textureCoordList.add(1f); textureCoordList.add(0f);

        // Face Brightness
        brightnessList.add(TOP_BRIGHT);
        brightnessList.add(TOP_BRIGHT);
        brightnessList.add(TOP_BRIGHT);
        brightnessList.add(TOP_BRIGHT);

        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);

        int layerIndex = material.getId();
        for (int i = 0; i < 4; i++) textureIndexList.add(layerIndex);
    }

    public void addBottomFace(Material material, float lightLevel) {
        int startIndex = positionList.size() / 3;
        positionList.add(-0.5f + cubeXPos); positionList.add(-0.5f + cubeYPos); positionList.add(0.5f + cubeZPos);
        positionList.add(-0.5f + cubeXPos); positionList.add(-0.5f + cubeYPos); positionList.add(-0.5f + cubeZPos);
        positionList.add(0.5f + cubeXPos);  positionList.add(-0.5f + cubeYPos); positionList.add(-0.5f + cubeZPos);
        positionList.add(0.5f + cubeXPos);  positionList.add(-0.5f + cubeYPos); positionList.add(0.5f + cubeZPos);

        vertexIndexList.add(startIndex);     vertexIndexList.add(startIndex + 1); vertexIndexList.add(startIndex + 3);
        vertexIndexList.add(startIndex + 2); vertexIndexList.add(startIndex + 3); vertexIndexList.add(startIndex + 1);

        textureCoordList.add(0f); textureCoordList.add(0f);
        textureCoordList.add(0f); textureCoordList.add(1f);
        textureCoordList.add(1f); textureCoordList.add(1f);
        textureCoordList.add(1f); textureCoordList.add(0f);

        // Face Brightness
        brightnessList.add(BOTTOM_BRIGHT);
        brightnessList.add(BOTTOM_BRIGHT);
        brightnessList.add(BOTTOM_BRIGHT);
        brightnessList.add(BOTTOM_BRIGHT);

        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);
        lightLevelList.add(lightLevel);

        int layerIndex = material.getId();
        for (int i = 0; i < 4; i++) textureIndexList.add(layerIndex);
    }

    public void setPos(float x, float y, float z) {
        cubeXPos = x;
        cubeYPos = y;
        cubeZPos = z;
    }

    // Getter for list to array.
    public float[] getPositionArray() {
        float[] arr = new float[positionList.size()];
        for (int i = 0; i < positionList.size(); i++) {
            arr[i] = positionList.get(i);
        }
        return arr;
    }

    public float[] getTextureCoordArray() {
        float[] arr = new float[textureCoordList.size()];
        for (int i = 0; i < textureCoordList.size(); i++) {
            arr[i] = textureCoordList.get(i);
        }
        return arr;
    }

    public int[] getVertexIndexArray() {
        int[] arr = new int[vertexIndexList.size()];
        for (int i = 0; i < vertexIndexList.size(); i++) {
            arr[i] = vertexIndexList.get(i);
        }
        return arr;
    }

    public int[] getTextureIndexArray() {
        int[] arr = new int[textureIndexList.size()];
        for (int i = 0; i < textureIndexList.size(); i++) {
            arr[i] = textureIndexList.get(i);
        }
        return arr;
    }

    public float[] getBrightnessArray() {
        float[] arr = new float[brightnessList.size()];
        for (int i = 0; i < brightnessList.size(); i++) {
            arr[i] = brightnessList.get(i);
        }
        return arr;
    }

    public float[] getLightLevelArray() {
        float[] arr = new float[lightLevelList.size()];
        for (int i = 0; i < lightLevelList.size(); i++) {
            arr[i] = lightLevelList.get(i);
        }
        return arr;
    }

}
