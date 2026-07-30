package de.rathfelix.engine.hud;

import de.rathfelix.Util.ListUtils;
import de.rathfelix.engine.texture.Texture;
import de.rathfelix.engine.mesh.HudMesh;
import de.rathfelix.engine.mesh.MeshBase;
import de.rathfelix.engine.objects.GameItem;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TextItem extends GameItem {

    private static final float ZPOS = 0.0f;
    private static final int VERTICES_PER_QUAD = 4;

    private final int numCols;
    private final int numRows;
    private final Texture texture;

    private String text;

    public TextItem(String text, String fontFileName, int numCols, int numRows) {
        super();
        this.text = text;
        this.numCols = numCols;
        this.numRows = numRows;
        this.texture = new Texture(fontFileName);
        this.setMesh(buildMesh(texture, numCols, numRows));
    }

    private MeshBase buildMesh(Texture texture, int numCols, int numRows) {
        byte[] chars = text.getBytes(Charset.forName("ISO-8859-1"));
        int numChars = chars.length;

        List<Float> positions = new ArrayList<>();
        List<Float> textCoords = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        float tileWidth = (float) texture.getWidth() / (float) numCols;
        float tileHeight = (float) texture.getHeight() / (float) numRows;

        for(int i=0; i<numChars; i++) {
            byte currChar = chars[i];
            int col = currChar % numCols;
            int row = currChar / numCols;

            // Build a character tile composed by two triangles

            // Left Top vertex
            positions.add((float)i*tileWidth); // x
            positions.add(0.0f); //y
            positions.add(ZPOS); //z
            textCoords.add((float)col / (float)numCols );
            textCoords.add((float)row / (float)numRows );
            indices.add(i*VERTICES_PER_QUAD);

            // Left Bottom vertex
            positions.add((float)i*tileWidth); // x
            positions.add(tileHeight); //y
            positions.add(ZPOS); //z
            textCoords.add((float)col / (float)numCols );
            textCoords.add((float)(row + 1) / (float)numRows );
            indices.add(i*VERTICES_PER_QUAD + 1);

            // Right Bottom vertex
            positions.add((float)i*tileWidth + tileWidth); // x
            positions.add(tileHeight); //y
            positions.add(ZPOS); //z
            textCoords.add((float)(col + 1)/ (float)numCols );
            textCoords.add((float)(row + 1) / (float)numRows );
            indices.add(i*VERTICES_PER_QUAD + 2);

            // Right Top vertex
            positions.add((float)i*tileWidth + tileWidth); // x
            positions.add(0.0f); //y
            positions.add(ZPOS); //z
            textCoords.add((float)(col + 1)/ (float)numCols );
            textCoords.add((float)row / (float)numRows );
            indices.add(i*VERTICES_PER_QUAD + 3);

            // Add indices por left top and bottom right vertices
            indices.add(i*VERTICES_PER_QUAD);
            indices.add(i*VERTICES_PER_QUAD + 2);
        }

        float[] posArr = ListUtils.toFloatArray(positions);
        float[] texArr = ListUtils.toFloatArray(textCoords);
        int[] indArr   = ListUtils.toIntArray(indices);
        return new HudMesh(posArr, indArr, texArr, texture);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.getMesh().cleanup();
        this.setMesh(buildMesh(texture, numCols, numRows));
    }
}
