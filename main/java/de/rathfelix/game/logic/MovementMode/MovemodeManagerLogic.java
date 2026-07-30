package de.rathfelix.game.logic.MovementMode;

import de.rathfelix.engine.Window;
import de.rathfelix.engine.input.MouseInput;
import de.rathfelix.game.logic.GameLogic;

public class MovemodeManagerLogic extends GameLogic {

    private IMovemode currentMode;

    @Override
    public void init() throws Exception {
        setCurrentMode(new GroundMovemode());
    }

    @Override
    public void update(MouseInput mouseInput) {
        currentMode.update();
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        currentMode.input();
    }

    // Getter Setter
    public IMovemode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(IMovemode currentMode) {
        this.currentMode = currentMode;
        currentMode.loadMode();
    }
}
