package poo;

import java.util.HashMap;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Handles window initialization and primary game setups
 * 
 * @author Bernardo Copstein, Rafael Copstein
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Initialize Window
        stage.setTitle(Params.WINDOW_TITLE);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        Scene scene = new Scene(root, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);
        stage.setScene(scene);

        Text text1 = new Text("Space Invaders");
        text1.setFont(new Font("Arial", 50));
        Text text2 = new Text("Nome:");
        text2.setFont(new Font("Arial", 20));
        TextField textField = new TextField("Player");
        Text text3 = new Text("Selecione seu canhão:");
        text3.setFont(new Font("Arial", 20));
        Text text4 = new Text("Highscores:");
        text4.setFont(new Font("Arial", 20));

        ChoiceBox<String> choiceBox = new ChoiceBox<String>();
        choiceBox.getItems().add("Canhão 1");
        choiceBox.getItems().add("Canhão 2");

        Button btn = new Button();
        btn.setText("Começar");

        root.getChildren().add(text1);
        root.getChildren().add(text2);
        root.getChildren().add(textField);
        root.getChildren().add(text3);
        root.getChildren().add(choiceBox);
        root.getChildren().add(btn);
        root.getChildren().add(text4);
        VBox.setMargin(textField, new Insets(0, 300, 0, 300));

        List<HashMap<String, String>> highScores = Game.readHighScores();
        for (HashMap<String, String> highScore : highScores) {
            root.getChildren().add(new Text(highScore.get("player") + ": " + highScore.get("score")));
        }

        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if (choiceBox.getValue() != null) {
                    Game.getInstance().setPlayer(textField.getText());
                    Game.getInstance().setCanhao(choiceBox.getValue());
                    stage.setScene(gameScene());
                }
            }
        });

        stage.show();

    }

    public Scene gameScene() {
        Group root = new Group();
        Scene scene = new Scene(root);

        Canvas canvas = new Canvas(Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);
        root.getChildren().add(canvas);

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

        new AnimationTimer() {
            long lastNanoTime = System.nanoTime();

            @Override
            public void handle(long currentNanoTime) {
                long deltaTime = currentNanoTime - lastNanoTime;

                Game.getInstance().Update(currentNanoTime, deltaTime);
                gc.clearRect(0, 0, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);
                Game.getInstance().Draw(gc);

                lastNanoTime = currentNanoTime;
            }

        }.start();

        return scene;
    }

    public static void main(String args[]) {
        launch();
    }
}
