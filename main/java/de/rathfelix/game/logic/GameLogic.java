package de.rathfelix.game.logic;

import de.rathfelix.engine.input.MouseInput;
import de.rathfelix.engine.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class GameLogic {

    public GameLogic() {
        logicList.add(this);
    }

    public void init() throws Exception{};

    public void input(Window window, MouseInput mouseInput){};

    public void update(MouseInput mouseInput){};

    public void render(Window window){};

    public void cleanup(){};

    public static List<GameLogic> logicList = new ArrayList<>();
    public static List<GameLogic> getList() {
        return logicList;
    }
}
