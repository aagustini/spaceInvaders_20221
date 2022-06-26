package poo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;



/**
 * Represents the basic game character
 * @author Bernardo Copstein and Rafael Copstein
 */
public interface KeyboardCtrl {
    void OnInput(KeyCode keyCode, boolean isPressed);
}
