package poo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

/**
 * Represents the game Gun
 * 
 * @author Bernardo Copstein, Rafael Copstein
 */
public abstract class Canhao extends BasicElement implements KeyboardCtrl {
    private static int velocidade = 2;
    private static int cadencia = 50;
    private int espera;

    public Canhao(int px, int py) {
        super(px, py);
        espera = 0;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public int getCadencia() {
        return cadencia;
    }

    @Override
    public void start() {
        setLimH(20, Params.WINDOW_WIDTH - 20);
        setLimV(Params.WINDOW_HEIGHT - 100, Params.WINDOW_HEIGHT);
    }

    @Override
    public void Update() {
        if (jaColidiu()) {
            Game.getInstance().setGameOver();
        }

        if (getX() + getDirH() * getSpeed() > 0 && getX() + getDirH() * getSpeed() < Params.WINDOW_WIDTH - 35) {
            setPosX(getX() + getDirH() * getSpeed());
        }

        if (espera > 0) {
            espera--;
        }
    }

    @Override
    public void OnInput(KeyCode keyCode, boolean isPressed) {
        if (keyCode == KeyCode.LEFT) {
            int dh = isPressed ? -getVelocidade() : 0;
            setDirH(dh);
        }
        if (keyCode == KeyCode.RIGHT) {
            int dh = isPressed ? getVelocidade() : 0;
            setDirH(dh);
        }
        if (keyCode == KeyCode.SPACE && espera == 0) {
            Game.getInstance().addChar(new ShotCanhao(getX() + 16, getY() - 32));
            espera = getCadencia();
        }
        // if (keyCode == KeyCode.UP) do nothing
        // if (keyCode == KeyCode.DOWN) do nothing
    }

    @Override
    public void Draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Paint.valueOf("#FF0000"));
        graphicsContext.fillRect(getX(), getY() + 16, 32, 32);
        graphicsContext.fillRect(getX() + 8, getY() - 16, 16, 48);
    }
}
