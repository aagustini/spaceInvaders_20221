package poo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

/**
 * Represents a shot that crosses the screen from bottom to up and then dismiss
 * 
 * @author Bernardo Copstein and Rafael Copstein
 */
public abstract class Shot extends BasicElement {
    private String color;

    public Shot(int px, int py) {
        super(px, py);
        color = "#00FF00";
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void start() {
        setDirV(-1);
        setSpeed(3);
    }

    @Override
    public void testaColisao(Character outro) {
        // Não verifica colisão de um tiro com outro tiro
        if (outro instanceof Shot) {
            return;
        } else {
            super.testaColisao(outro);
        }
    }

    @Override
    public void Update() {
        if (jaColidiu()) {
            deactivate();
        } else {
            setPosY(getY() + getDirV() * getSpeed());
            // Se chegou na parte superior da tela ...
            if (getY() <= getLMinV()) {
                // Desaparece
                deactivate();
            }
        }
    }

    public void Draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Paint.valueOf(color));
        graphicsContext.fillOval(getX(), getY(), 8, 16);
    }
}
