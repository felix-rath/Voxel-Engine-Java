package de.rathfelix.game.logic.Cameramode;

import de.rathfelix.engine.Window;
import de.rathfelix.engine.input.KeyboardInput;
import de.rathfelix.engine.input.MouseInput;
import de.rathfelix.game.logic.GameLogic;
import org.lwjgl.glfw.GLFW;

public class CameramodeManagerLogic extends GameLogic {

    private ICameramode currentMode;


    @Override
    public void init() throws Exception {
        setCurrentMode(new FirstPersonCameramode());
    }

    @Override
    public void update(MouseInput mouseInput) {
        currentMode.update();
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        if (!KeyboardInput.isKeyJustPressed(GLFW.GLFW_KEY_F5)) return;

        if (currentMode instanceof FirstPersonCameramode)
            setCurrentMode(new ThirdPersonCameramode());
        else if (currentMode instanceof ThirdPersonCameramode)
            setCurrentMode(new FirstPersonCameramode());
    }

    // Getter Setter
    public ICameramode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(ICameramode currentMode) {
        this.currentMode = currentMode;
        currentMode.loadMode();
    }
}
