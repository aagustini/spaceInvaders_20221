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
public class Ball3 extends Ball {
    private int vida;
    static private int pontos = 30;

    public Ball3(int px, int py) {
        super(px, py);
        vida = 2;
    }

    @Override
    public int getPontos() {
        return pontos;
    }

    @Override
    public boolean jaColidiu() {
        return vida == 0;
    }

    @Override
    public void setColidiu() {
        vida--;
    }

    private static Image img = new Image(Ball.class.getResourceAsStream("/imagens/ball3.gif"));

    @Override
    public Image getImage() {
        return img;
    }
}
