package de.rathfelix.engine.input;

import de.rathfelix.engine.Window;
import org.joml.Vector2d;
import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {

    private final Vector2d previousPos;
    private final Vector2d currentPos;
    private final Vector2f displVec;

    private boolean inWindow = false;
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;

    public MouseInput() {
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0, 0);
        displVec = new Vector2f();
    }

    public void init(Window window) {
        // Cursor verstecken & deaktivieren (wichtig für FPS-Steuerung)
        glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        glfwSetCursorPosCallback(window.getWindow(), (windowHandle, xpos, ypos) -> {
            // Keine Clamping hier, Position so übernehmen
            currentPos.x = xpos;
            currentPos.y = ypos;
        });

        glfwSetCursorEnterCallback(window.getWindow(), (windowHandle, entered) -> {
            inWindow = entered;
        });

        glfwSetMouseButtonCallback(window.getWindow(), (windowHandle, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_1) {
                leftButtonPressed = (action == GLFW_PRESS);
            }
            if (button == GLFW_MOUSE_BUTTON_2) {
                rightButtonPressed = (action == GLFW_PRESS);
            }
        });

        // Cursor initial in die Mitte setzen
        glfwSetCursorPos(window.getWindow(), window.getResolution().width / 2, window.getResolution().height / 2);
        previousPos.x = window.getResolution().width / 2;
        previousPos.y = window.getResolution().height / 2;
        currentPos.x = previousPos.x;
        currentPos.y = previousPos.y;
    }

    public Vector2f getDisplVec() {
        return displVec;
    }

    public void input(Window window) {
        displVec.x = 0;
        displVec.y = 0;

        if (previousPos.x >= 0 && previousPos.x < window.getResolution().width &&
                previousPos.y >= 0 && previousPos.y < window.getResolution().height) {

            double deltaX = currentPos.x - previousPos.x;
            double deltaY = currentPos.y - previousPos.y;

            // Horizontal bewegt Rotation um Y-Achse
            displVec.y = (float) deltaX;
            // Vertikal bewegt Rotation um X-Achse
            displVec.x = (float) deltaY;

            // Cursor in die Mitte zurücksetzen, damit er nicht am Rand hängt
            glfwSetCursorPos(window.getWindow(), window.getResolution().width / 2, window.getResolution().height / 2);
            currentPos.x = window.getResolution().width / 2;
            currentPos.y = window.getResolution().height / 2;
            previousPos.x = currentPos.x;
            previousPos.y = currentPos.y;
        } else {
            // Wenn vorherige Position ungültig ist, setze sie einfach neu
            previousPos.x = currentPos.x;
            previousPos.y = currentPos.y;
        }
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }
}
