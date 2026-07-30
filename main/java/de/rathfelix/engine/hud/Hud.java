package de.rathfelix.engine.hud;

import de.rathfelix.engine.Window;
import de.rathfelix.engine.objects.GameItem;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class Hud implements IHud{

    private static final int FONT_COLS = 16;
    private static final int FONT_ROWS = 16;
    private static final String FONT_TEXTURE = "/textures/font_texture.png";

    private static final List<GameItem> gameItems = new ArrayList<>();
    private final TextItem statusTextItem;

    public Hud(String statusText) {
        this.statusTextItem = new TextItem(statusText, FONT_TEXTURE, FONT_COLS, FONT_ROWS);
        this.statusTextItem.getMesh().setColour(new Vector4f(1, 1, 1, 1));
        gameItems.add(statusTextItem);
    }

    public void setStatusText(String statusText) {
        this.statusTextItem.setText(statusText);
    }

    public static List<GameItem> getGameItems() {
        return gameItems;
    }

    public void updateSize(Window window) {
        //this.statusTextItem.setPosition(10f, 10f, 0); // Oben links
        this.statusTextItem.setPosition(10f, window.getResolution().height - 100f, 0);
    }
}
