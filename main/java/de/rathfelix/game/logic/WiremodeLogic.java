package de.rathfelix.game.logic;

import de.rathfelix.engine.Window;
import de.rathfelix.engine.input.KeyboardInput;
import de.rathfelix.engine.input.MouseInput;
import org.lwjgl.glfw.GLFW;

public class WiremodeLogic extends GameLogic {

    private final int KEY_BIND = GLFW.GLFW_KEY_F2;

    private boolean wireMode = false;

    @Override
    public void input(Window window, MouseInput mouseInput) {
        if (!KeyboardInput.isKeyJustPressed(KEY_BIND)) return;

        if (wireMode) {
            wireMode = false;
            window.setWireMode(false);
        } else {
            wireMode = true;
            window.setWireMode(true);
        }
    }
}
