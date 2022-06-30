package poo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Canhao2 extends Canhao {
    private static int velocidade = 1;
    private static int cadencia = 15;

    @Override
    public int getVelocidade() {
        return velocidade;
    }

    @Override
    public int getCadencia() {
        return cadencia;
    }

    public Canhao2(int px, int py) {
        super(px, py);
    }

    @Override
    public void Draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Paint.valueOf("#0000FF"));
        graphicsContext.fillRect(getX(), getY() + 16, 32, 32);
        graphicsContext.fillRect(getX() + 8, getY() - 16, 16, 48);
    }
}
