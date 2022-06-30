package poo;

public class Canhao1 extends Canhao {
    private static int velocidade = 2;
    private static int cadencia = 30;

    @Override
    public int getVelocidade() {
        return velocidade;
    }

    @Override
    public int getCadencia() {
        return cadencia;
    }

    public Canhao1(int px, int py) {
        super(px, py);
    }
}
