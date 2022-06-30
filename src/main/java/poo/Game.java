package poo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Handles the game lifecycle and behavior
 * 
 * @author Bernardo Copstein and Rafael Copstein
 */
public class Game {
    private static Game game = null;
    private Canhao canhao;
    private List<Character> activeChars;
    private int stage;
    private int score;
    private int timer;
    private String tipoCanhao;
    private String player;
    private Boolean ended;

    private Game() {
        ended = false;
    }

    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }
        return (game);
    }

    public void setCanhao(String c) {
        tipoCanhao = c;
    }

    public void setPlayer(String p) {
        player = p;
    }

    public void addChar(Character c) {
        activeChars.add(c);
        c.start();
    }

    public void eliminate(Character c) {
        activeChars.remove(c);
    }

    public void Start() {
        stage = stage + 1;
        timer = 200;

        // Repositório de personagens
        activeChars = new LinkedList();

        // Adiciona o canhao
        if (tipoCanhao.equals("Canhão 1")) {
            canhao = new Canhao1(400, 550);
        } else {
            canhao = new Canhao2(400, 550);
        }
        activeChars.add(canhao);

        for (int i = 0; i < 8 * stage; i++) {
            createEnemy();
        }

        for (Character c : activeChars) {
            c.start();
        }
    }

    public void Update(long currentTime, long deltaTime) {
        if (ended || stage == -1) {
            return;
        }

        if (stage == 4) {
            ended = true;
            List<HashMap<String, String>> highScores = readHighScores();

            highScores.add(new HashMap<String, String>() {
                {
                    put("player", player);
                    put("score", String.valueOf(score));
                }
            });

            highScores = highScores.stream()
                    .sorted((h1, h2) -> Integer.parseInt(h2.get("score")) - Integer.parseInt(h1.get("score")))
                    .limit(10)
                    .collect(Collectors.toList());

            try {
                FileWriter fileWriter;
                fileWriter = new FileWriter(Params.FILE_NAME);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                for (HashMap<String, String> highScore : highScores) {
                    printWriter.printf("%s,%s%n", highScore.get("player"),
                            highScore.get("score"));
                }
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ended = true;
        }

        if (timer > 0) {
            timer--;
            return;
        }

        for (int i = 0; i < activeChars.size(); i++) {
            Character este = activeChars.get(i);
            este.Update();
            for (int j = 0; j < activeChars.size(); j++) {
                Character outro = activeChars.get(j);
                if (este instanceof Shot && este != outro) {
                    este.testaColisao(outro);
                }
            }
        }

        if (Math.random() < 0.01) {
            activeChars.stream().filter(c -> c instanceof Ball4).map(c -> (Ball4) c)
                    .forEach(c -> c.setDirH(-c.getDirH()));
        }

        if (activeChars.stream().filter(c -> c instanceof Ball).count() == 0) {
            Start();
        }
    }

    public void createEnemy() {
        double chance = Math.random();

        Ball novoInimigo;

        if (chance <= 0.10 * stage) {
            novoInimigo = new Ball4(
                    (int) (Math.random() * Params.WINDOW_WIDTH),
                    (int) (Math.random() * (Params.WINDOW_HEIGHT / 4)) + 75);
        } else if (chance <= 0.20 * stage) {
            novoInimigo = new Ball3(
                    (int) (Math.random() * Params.WINDOW_WIDTH),
                    (int) (Math.random() * (Params.WINDOW_HEIGHT / 4)) + 75);
        } else if (chance <= 0.30 * stage) {
            novoInimigo = new Ball2(
                    (int) (Math.random() * Params.WINDOW_WIDTH),
                    (int) (Math.random() * (Params.WINDOW_HEIGHT / 4)) + 75);
        } else {
            novoInimigo = new Ball1(
                    (int) (Math.random() * Params.WINDOW_WIDTH),
                    (int) (Math.random() * (Params.WINDOW_HEIGHT / 4)) + 75);
        }

        novoInimigo.start();
        activeChars.add(novoInimigo);
    }

    public void addScore(int pontos) {
        score = score + pontos;
    }

    public int getStage() {
        return stage;
    }

    public static List<HashMap<String, String>> readHighScores() {
        List<HashMap<String, String>> highScores = new ArrayList<>();
        try (Scanner scanner = new Scanner(
                new File(Params.FILE_NAME))) {
            // new File("src/mian/java/poo/highscores.csv"))) {
            while (scanner.hasNextLine()) {
                HashMap<String, String> highScore = new HashMap<>();
                try (Scanner rowScanner = new Scanner(scanner.nextLine())) {
                    rowScanner.useDelimiter(",");
                    while (rowScanner.hasNext()) {
                        highScore.put("player", rowScanner.next());
                        highScore.put("score", rowScanner.next());
                    }
                }
                highScores.add(highScore);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return highScores;
        }

        return highScores;
    }

    public void setGameOver() {
        stage = -1;
    }

    public void OnInput(KeyCode keyCode, boolean isPressed) {
        canhao.OnInput(keyCode, isPressed);
    }

    public void Draw(GraphicsContext graphicsContext) {
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setFont(Font.font("Arial", 20));
        graphicsContext.setFill(Paint.valueOf("#000000"));

        if (stage == -1) {
            graphicsContext.fillText("GAME OVER!", Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 180);
            graphicsContext.strokeText("GAME OVER!", Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 180);
            graphicsContext.fillText("Voce perdeu", Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 150);
            graphicsContext.strokeText("Voce perdeu", Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 150);
            graphicsContext.fillText("Pontuacao final: " + score, Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 120);
            graphicsContext.strokeText("Pontuacao final: " + score, Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 120);
            return;
        }

        if (stage == 4) {
            graphicsContext.fillText("Parabens!", Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 180);
            graphicsContext.strokeText("Parabens!", Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 180);
            graphicsContext.fillText("Voce ganhou", Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 150);
            graphicsContext.strokeText("Voce ganhou", Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 150);
            graphicsContext.fillText("Pontuacao final: " + score, Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 120);
            graphicsContext.strokeText("Pontuacao final: " + score, Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 120);

            graphicsContext.fillText("High Scores:", Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 80);
            graphicsContext.strokeText("High Scores:", Params.WINDOW_WIDTH / 2,
                    (Params.WINDOW_HEIGHT / 2) - 80);

            List<HashMap<String, String>> highScores = readHighScores();
            int i = 0;
            for (HashMap<String, String> highScore : highScores) {
                graphicsContext.fillText(highScore.get("player") + ": " + highScore.get("score"),
                        Params.WINDOW_WIDTH / 2,
                        (Params.WINDOW_HEIGHT / 2) - 55 + i * 25);
                graphicsContext.strokeText(highScore.get("player") + ": " + highScore.get("score"),
                        Params.WINDOW_WIDTH / 2,
                        (Params.WINDOW_HEIGHT / 2) - 55 + i * 25);
                i++;
            }
            return;
        }

        if (timer > 0) {
            graphicsContext.fillText("Fase: " + stage, Params.WINDOW_WIDTH / 2, Params.WINDOW_HEIGHT / 2);
            graphicsContext.strokeText("Fase: " + stage, Params.WINDOW_WIDTH / 2, Params.WINDOW_HEIGHT / 2);
            return;
        }

        graphicsContext.fillText("Pontos: " + score + " Fase: " + stage, 110, 50);
        graphicsContext.strokeText("Pontos: " + score + " Fase: " + stage, 110, 50);
        graphicsContext.fillText("Inimigos: " + activeChars.stream().filter(c -> c instanceof Ball).count(),
                Params.WINDOW_WIDTH - 90, 50);
        graphicsContext.strokeText("Inimigos: " + activeChars.stream().filter(c -> c instanceof Ball).count(),
                Params.WINDOW_WIDTH - 90, 50);

        for (Character c : activeChars) {
            c.Draw(graphicsContext);
        }
    }
}
