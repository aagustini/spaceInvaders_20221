package poo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;



/**
 * Handles window initialization and primary game setup
 * @author Bernardo Copstein, Rafael Copstein
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Initialize Window
        stage.setTitle(Params.WINDOW_TITLE);
 
        Group root = new Group();
        Scene scene = new Scene( root );
        stage.setScene( scene );

        Canvas canvas = new Canvas(Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT );
        root.getChildren().add( canvas );

        // Setup Game object
        Game.getInstance().Start();
        
        // Register User Input Handler
        scene.setOnKeyPressed((KeyEvent event) -> {
            Game.getInstance().OnInput(event.getCode(), true);
        });
        
        scene.setOnKeyReleased((KeyEvent event) -> {
            Game.getInstance().OnInput(event.getCode(), false);
        });
        
        // Register Game Loop       
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        new AnimationTimer()
        {
            long lastNanoTime = System.nanoTime();
            
            @Override
            public void handle(long currentNanoTime)
            {
                long deltaTime = currentNanoTime - lastNanoTime;
                
                Game.getInstance().Update(currentNanoTime, deltaTime);
                gc.clearRect(0, 0, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);
                Game.getInstance().Draw(gc);
                
                lastNanoTime = currentNanoTime;
            }
            
        }.start();

        // Show window
        stage.show();
    }
    
    public static void main(String args[]) {
        launch();
    }
}
