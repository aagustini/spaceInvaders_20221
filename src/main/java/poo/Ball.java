package poo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;


/**
 * Represents a simple ball that crosses the screen over and over again
 * @author Bernardo Copstein and Rafael Copstein
 */
public class Ball extends BasicElement{
    public Ball(int px,int py){
        super(px,py);
    }
    
    @Override
    public void start(){
        setDirH(1);
    }
    
        
    @Override
    public void Update(){
        if (jaColidiu()){
            deactivate();
        }else{
            setPosX(getX() + getDirH() * getSpeed());
            // Se chegou no lado direito da tela ...
            if (getX() >= getLMaxH()){
                // Reposiciona no lado esquerdo e ...
                setPosX(getLMinH());
                // Sorteia o passo de avanço [1,5]
                setSpeed(Params.getInstance().nextInt(5)+1);
            }
        }
    }    

    //
    // atenção!
    //    a alteração abaixo mostra como utilizar uma imagem  
    //       no lugar do desenho
    //    note que isto altera unicamente a apresentação, 
    //       e não introduzi um novo personagem no jogo
    //

    //public void Draw(GraphicsContext graphicsContext){
    //    graphicsContext.setFill(Paint.valueOf("#0000FF"));
    //    graphicsContext.fillOval(getX(), getY(), 32, 32);
    //}
    
    private  Image img = new Image(getClass().getResourceAsStream("/imagens/monster.gif"));

    public void Draw(GraphicsContext graphicsContext){
       graphicsContext.drawImage(img,getX(),getY(),48,48);  
    }
}

