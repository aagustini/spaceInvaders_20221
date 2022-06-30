package poo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

/**
 * Represents a simple ball that crosses the screen over and over again
 * 
 * @author Bernardo Copstein and Rafael Copstein
 */
public class Ball4 extends Ball {
    static private int pontos = 40;

    public Ball4(int px, int py) {
        super(px, py);
    }

    @Override
    public int getPontos() {
        return pontos;
    }

    private static Image img = new Image(Ball.class.getResourceAsStream("/imagens/ball4.gif"));

    @Override
    public Image getImage() {
        return img;
    }
}
