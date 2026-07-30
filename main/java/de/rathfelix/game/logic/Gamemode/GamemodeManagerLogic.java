package de.rathfelix.game.logic.Gamemode;

import de.rathfelix.engine.Window;
import de.rathfelix.engine.input.KeyboardInput;
import de.rathfelix.engine.input.MouseInput;
import de.rathfelix.game.logic.GameLogic;
import org.lwjgl.glfw.GLFW;

public class GamemodeManagerLogic extends GameLogic {

    private IGamemode currentMode;

    @Override
    public void init() throws Exception {
        setCurrentMode(new SurvivalGamemode());
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        if (!KeyboardInput.isKeyJustReleased(GLFW.GLFW_KEY_F1)) return;

        if (currentMode instanceof SurvivalGamemode)
            setCurrentMode(new SpectatorGamemode());
        else if(currentMode instanceof SpectatorGamemode)
            setCurrentMode(new SurvivalGamemode());
    }



    // Getter Setter
    public IGamemode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(IGamemode currentMode) {
        this.currentMode = currentMode;
        currentMode.loadMode();
    }
}
