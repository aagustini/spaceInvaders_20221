package poo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

public class ShotCanhao extends Shot {
    public ShotCanhao(int px, int py) {
        super(px, py);
        super.setColor("#00FF00");
    }

    @Override
    public void testaColisao(Character outro) {
        if (outro instanceof Ball) {
            // Monta pontos
            int p1x = this.getX();
            int p1y = this.getY();
            int p2x = p1x + this.getLargura();
            int p2y = p1y + this.getAltura();

            int op1x = outro.getX();
            int op1y = outro.getY();
            int op2x = op1x + outro.getLargura();
            int op2y = op1y + outro.getAltura();

            // Verifica colisão
            if (((p1x <= op1x && p2x >= op1x) && (p1y <= op1y && p2y >= op1y)) ||
                    ((p1x <= op2x && p2x >= op2x) && (p1y <= op2y && p2y >= op2y))) {
                this.setColidiu();
                outro.setColidiu();
            }
        }
    }
}
