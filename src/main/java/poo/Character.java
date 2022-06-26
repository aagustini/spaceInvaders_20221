
package poo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;



/**
 * Represents the basic game character
 * @author Bernardo Copstein and Rafael Copstein
 */
public interface Character {
    int getX();
    int getY();
    int getAltura();
    int getLargura();
    
    void testaColisao(Character c);
    boolean jaColidiu();
    void setColidiu();
    
    void start();
    boolean isActive();
    void Update();
    void Draw(GraphicsContext graphicsContext);
}
