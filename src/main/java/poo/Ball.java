package poo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Represents a simple ball that crosses the screen over and over again
 * 
 * @author Bernardo Copstein and Rafael Copstein
 */
public abstract class Ball extends BasicElement {
    static private int pontos = 10;

    public Ball(int px, int py) {
        super(px, py);
    }

    public int getPontos() {
        return pontos;
    }

    @Override
    public void deactivate() {
        super.deactivate();
        if (Math.random() < 0.40 + 0.10 * Game.getInstance().getStage()) {
            Game.getInstance().createEnemy();
        }
    }

    @Override
    public void start() {
        setDirH(1);
    }

    @Override
    public void Update() {
        if (jaColidiu()) {
            deactivate();
            Game.getInstance().addScore(getPontos());
        } else {
            setPosX(getX() + getDirH() * getSpeed());
            // Se chegou no lado direito da tela ...
            if (getX() >= getLMaxH()) {
                // Reposiciona no lado esquerdo e ...
                setPosX(getLMinH());
                // Sorteia o passo de avanço [1,5]
                setSpeed(Params.getInstance().nextInt(5) + 1);
                setPosY(getY() + 35);
            } else if (getX() <= getLMinH()) {
                setPosX(getLMaxH());
                setSpeed(Params.getInstance().nextInt(5) + 1);
                setPosY(getY() + 35);
            }

            if (getY() >= Params.WINDOW_HEIGHT - 50) {
                Game.getInstance().setGameOver();
            }
        }
    }

    private static Image img = new Image(Ball.class.getResourceAsStream("/imagens/monster.gif"));

    public Image getImage() {
        return img;
    }

    public void Draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(getImage(), getX(), getY(), 48, 48);
    }
}
