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
public class Ball1 extends Ball {
    static private int pontos = 10;

    public Ball1(int px, int py) {
        super(px, py);
    }

    @Override
    public int getPontos() {
        return pontos;
    }

    @Override
    public void Update() {
        super.Update();

        if (!jaColidiu()) {
            if (Math.random() <= 0.005) {
                Game.getInstance().addChar(new ShotBall(getX() + 16, getY() + 40));
            }
        }
    }

    private static Image img = new Image(Ball.class.getResourceAsStream("/imagens/ball1.gif"));

    @Override
    public Image getImage() {
        return img;
    }
}
