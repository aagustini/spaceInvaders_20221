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
public class Ball2 extends Ball {
    static private int pontos = 20;

    public Ball2(int px, int py) {
        super(px, py);
    }

    @Override
    public int getPontos() {
        return pontos;
    }

    @Override
    public void start() {
        setDirH(-1);
    }

    private static Image img = new Image(Ball.class.getResourceAsStream("/imagens/ball2.gif"));

    @Override
    public Image getImage() {
        return img;
    }
}
