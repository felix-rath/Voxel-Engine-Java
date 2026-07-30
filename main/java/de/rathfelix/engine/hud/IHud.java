package de.rathfelix.engine.hud;

import de.rathfelix.engine.objects.GameItem;

import java.util.List;

public interface IHud {

    //List<GameItem> getGameItems();

    default void cleanup() {
        List<GameItem> gameItems = Hud.getGameItems();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanup();
        }
    }
}
