package pa.project.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pa.project.entities.Words;
import pa.project.manager.Manager;
import pa.project.repositories.WordsRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Window {
    private Scene startScene;
    private Scene gameScene;
    private String word;
    private String domain;
    private String wordToGuess;
    private int tries;
    private WordsRepository wordsRepository;
    private List<String> letterList;
    private String originalWord;

    public void createStartWindow(Stage window) {
        window.setOnCloseRequest(event -> {
            event.consume();
            closeProgram(window);
        });
        window.setTitle("Spanzuratoarea");

        VBox startLayout = new VBox(20);
        Label startGameLabel = new Label("Incepe jocul!");
        Button startGameButton = new Button("Incepe Jocul");
        startGameButton.setOnAction(event -> {
            createGameWindow(window);
            window.setScene(gameScene);
        });

        startLayout.getChildren().addAll(startGameLabel, startGameButton);
        startLayout.setAlignment(Pos.CENTER);
        startScene = new Scene(startLayout, 1280, 720);

        window.setScene(startScene);
        window.setTitle("Spanzuratoarea");
        window.show();
    }

    public void createGameWindow(Stage window) {

        initVariables();

        window.setOnCloseRequest(event -> {
            event.consume();
            closeProgram(window);
        });

        //Game Menu
        VBox gameMenu = new VBox(20);
        initGameMenu(gameMenu, window);

        //Domain for word
        HBox wordDomain = new HBox(10);
        initDomainSpace(wordDomain);

        //Input text field
        VBox letterInput = new VBox(10);
        TextField letterText = new TextField();
        letterText.setPromptText("Introdu o litera sau cuvantul");
        letterText.setMaxWidth(210);

        Button textFieldButton = new Button("Trimite");
        letterInput.getChildren().addAll(letterText, textFieldButton);
        letterInput.setAlignment(Pos.CENTER);

        //Center layout
        VBox content = new VBox(30);
        char[] fill = new char[word.length()];
        Arrays.fill(fill, '_');
        wordToGuess = new String(fill);
        Label wordToGuessLabel = new Label(wordToGuess);
        wordToGuessLabel.setFont(new Font(72));
        Label lettersUsedText = new Label("Litere folosite");
        lettersUsedText.setFont(new Font(32));
        Label lettersUsed = new Label();
        lettersUsed.setText("[]");
        lettersUsed.setFont(new Font(32));
        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(wordToGuessLabel, lettersUsedText, lettersUsed);

        //Spanzuratoare Layout
        Group drawing = new Group();
        drawSpanzuratoare(drawing);

        //Game Layout
        BorderPane gameLayout = new BorderPane();
        setGameLayout(gameLayout, gameMenu, wordDomain, letterInput, content, drawing);

        gameScene = new Scene(gameLayout, 1280, 720);
        window.setScene(gameScene);
        window.show();

        textFieldButton.setOnAction(event -> handleInput(letterText, wordToGuessLabel, drawing, window, lettersUsed));
    }

    private void closeProgram(Stage window) {
        boolean answer = ConfirmBox.display("Iesire", "Esti sigur ca vrei sa parasesti aplicatia?");
        if (answer) {
            window.close();
        }
    }

    private void resetProgram(Stage window) {
        boolean answer = ConfirmBox.display("Reset", "Esti sigur ca vrei sa incepi din nou?");
        if (answer) {
            createStartWindow(window);
        }
    }

    private boolean isLetter(TextField input, String text) {
        text = text.toUpperCase();
        //input.setStyle("-fx-text-fill: black;");
        return text.length() == 1 && text.charAt(0) >= 'A' && text.charAt(0) <= 'Z';
        //input.setStyle("-fx-text-fill: red;");
    }

    private int countOccurrences(char c, String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    private Line createLine(int x1, int x2, int y1, int y2) {
        Line line = new Line();
        line.setStartX(x1);
        line.setEndX(x2);
        line.setStartY(y1);
        line.setEndY(y2);
        line.setStrokeWidth(10);

        return line;
    }

    private Circle createCircle(int x, int y, int r) {
        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(r);
        circle.setStrokeWidth(10);

        return circle;
    }

    private void checkTries(Group drawing) {
        switch (tries) {
            case 1:
                Circle head = createCircle(300, 215, 30);
                drawing.getChildren().add(head);
                break;
            case 2:
                Line bodyLine = createLine(300, 300, 230, 420);
                drawing.getChildren().add(bodyLine);
                break;
            case 3:
                Line leftArmLine = createLine(295, 260, 270, 290);
                drawing.getChildren().add(leftArmLine);
                break;
            case 4:
                Line rightArmLine = createLine(305, 340, 270, 290);
                drawing.getChildren().add(rightArmLine);
                break;
            case 5:
                Line leftLegLine = createLine(295, 260, 423, 445);
                drawing.getChildren().add(leftLegLine);
                break;
            case 6:
                Line rightLegLine = createLine(305, 340, 423, 445);
                drawing.getChildren().add(rightLegLine);
                break;
        }
    }

    private void drawSpanzuratoare(Group drawing) {
        Line line1 = createLine(50, 50, 600, 100);
        Line line2 = createLine(50, 300, 100, 100);
        Line line3 = createLine(55, 100, 150, 105);
        Line line4 = createLine(300, 300, 100, 200);
        Line line5 = createLine(250, 295, 105, 150);
        drawing.getChildren().addAll(line1, line2, line3, line4, line5);
    }

    private Words initWord() {
        EntityManagerFactory factory = Manager.getFactory();
        EntityManager manager = factory.createEntityManager();
        wordsRepository = new WordsRepository(manager);

        int maxId = wordsRepository.getMaxId();

        Random rand = new Random();
        int word_id = rand.nextInt(maxId + 1);
        return wordsRepository.getById(word_id);
    }

    private void initGameMenu(VBox gameMenu, Stage window) {

        Button endGameButton = new Button("Paraseste Jocul");
        endGameButton.setOnAction(event -> closeProgram(window));
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> resetProgram(window));
        gameMenu.getChildren().addAll(resetButton, endGameButton);
        gameMenu.setAlignment(Pos.CENTER);
    }

    private void initDomainSpace(HBox wordDomain) {
        Label domainLabel = new Label(domain);
        domainLabel.setFont(new Font(50));
        wordDomain.getChildren().add(domainLabel);
        wordDomain.setAlignment(Pos.CENTER);
    }

    private void handleInput(TextField letterText, Label wordToGuessLabel, Group drawing, Stage window, Label lettersUsed) {
        if (isLetter(letterText, letterText.getText())) {
            //System.out.println(letterText.getText());
            wordToGuess = wordToGuessLabel.getText();
            String letter = letterText.getText().toUpperCase();
            if (!word.contains(letter)) {
                tries++;
                if (!letterList.contains(letter)) {
                    letterList.add(letter);
                }
                lettersUsed.setText(letterList.toString());
                checkTries(drawing);
            } else {
                while (word.contains(letter)) {
                    wordToGuess = wordToGuess.substring(0, word.indexOf(letter))
                            + letter
                            + wordToGuess.substring(word.indexOf(letter) + 1);
                    word = word.substring(0, word.indexOf(letter))
                            + '_'
                            + word.substring(word.indexOf(letter) + 1);
                }
                wordToGuessLabel.setText(wordToGuess);
                if (countOccurrences('_', wordToGuess) == 0) {
                    if (EndBox.display("Ai castigat", "Ai castigat")) {
                        window.close();
                    } else {
                        createStartWindow(window);
                    }
                }
            }
        }

        if (!isLetter(letterText, letterText.getText()) && letterText.getText().length() == originalWord.length()) {
            wordToGuessLabel.setText(originalWord);
            if (EndBox.display("Ai castigat", "Ai castigat")) {
                window.close();
            } else {
                createStartWindow(window);
            }
        }

        if (letterText.getText().length() != 0 && letterText.getText().length() != 1 && letterText.getText().length() != originalWord.length()) {
            tries++;
            checkTries(drawing);
        }

        if (tries > 5) {
            wordToGuessLabel.setText(originalWord);
            if (EndBox.display("Ai pierdut", "Ai pierdut")) {
                window.close();
            } else {
                createStartWindow(window);
            }
        }
        letterText.clear();
        letterText.requestFocus();
    }

    private void setGameLayout(BorderPane gameLayout, VBox gameMenu, HBox wordDomain, VBox letterInput, VBox content, Group drawing) {
        gameLayout.setRight(gameMenu);
        gameLayout.setTop(wordDomain);
        gameLayout.setBottom(letterInput);
        gameLayout.setCenter(content);
        gameLayout.setLeft(drawing);
        gameLayout.setPadding(new Insets(20, 20, 20, 20));
    }

    private void initVariables() {
        letterList = new ArrayList<>();

        Words words = initWord();
        word = words.getWord();
        originalWord = word;

        domain = words.getWordDomain();

        tries = 0;
    }
}