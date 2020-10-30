package be.kdg.spel.view.spel;

import be.kdg.spel.model.*;
import be.kdg.spel.view.highscore.HighscorePresenter;
import be.kdg.spel.view.highscore.HighscoreView;
import be.kdg.spel.view.start.StartPresenter;
import be.kdg.spel.view.start.StartView;
import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpelPresenter {
    private static final String SAVEDIR = "src/be/kdg/spel/files/";
    private static final int MAX_TILE_X = 4;
    private static final int MAX_TILE_Y = 4;
    private List<Integer> numbers = new ArrayList<>();
    private Spel model;
    private SpelView view;
    private Random random;
    private int xRandom;
    private int yRandom;
    private String[] ingelezen = new String[17];



    public SpelPresenter(Spel model, SpelView view) {
        this.model = model;
        this.view = view;
        addEventHandelers();
        updateView();
    }

    private void addEventHandelers() {
        view.getGrid().setFocusTraversable(true);
        view.getGrid().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        removeStyleTile();
                        moveTiles(Richting.BOVEN);
                        addRandomTile();
                        setStyleTile();
                        youLose();
                        youWin();
                        break;
                    case DOWN:
                        removeStyleTile();
                        moveTiles(Richting.BENEDEN);
                        addRandomTile();
                        setStyleTile();
                        youLose();
                        youWin();
                        break;
                    case LEFT:
                        removeStyleTile();
                        moveTiles(Richting.LINKS);
                        addRandomTile();
                        setStyleTile();
                        youLose();
                        youWin();
                        break;
                    case RIGHT:
                        removeStyleTile();
                        moveTiles(Richting.RECHTS);
                        addRandomTile();
                        setStyleTile();
                        youLose();
                        youWin();
                        break;
                    case W:
                        view.getTileValue(xRandom, yRandom).setText("1024");
                        view.getTileValue(xRandom, yRandom).setText("1024");
                        setStyleTile();
                }
            }
        });

        view.getMiLoad().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader((SAVEDIR
                            + view.getLblGebruiker().getText() + ".txt")));
                    String lijn = br.readLine();
                    int i = 0;
                    while (lijn != null) {
                        ingelezen[i] = lijn;
                        lijn = br.readLine();
                        i++;
                    }
                    if (ingelezen[0].equals(view.getLblGebruiker().getText())) {
                        setSave(ingelezen);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        view.getBtnRestart().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SpelView spelView = new SpelView();
                Spel spelmodel = new Spel();
                SpelPresenter spelPresenter = new SpelPresenter(spelmodel, spelView);
                view.getScene().setRoot(spelView);
            }
        });

        view.getBtnTerug().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StartView startView = new StartView();
                StartPresenter startPresenter = new StartPresenter(startView);
                view.getScene().setRoot(startView);
            }
        });

        view.getBtnHighscore().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HighscoreView highscoreView = new HighscoreView();
                Highscores model = new Highscores();
                HighscorePresenter highscorePresenter = new HighscorePresenter(model, highscoreView);
                view.getScene().setRoot(highscoreView);
            }
        });

        /**
         * Bij het saven van het spel gaat men de waardes dat op de gridpane staan 16 keer wegschrijven in bestand
         */

        view.getMiSave().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try (PrintWriter pw = new PrintWriter(new BufferedWriter((new FileWriter(SAVEDIR
                        + view.getLblGebruiker().getText() + ".txt"))))) {
                    pw.write(view.getLblGebruiker().getText() + "\n");
                    for (int i = 0; i < MAX_TILE_X; i++) {
                        for (int j = 0; j < MAX_TILE_Y; j++) {
                            pw.write(view.getTileValue(i, j).getText() + "\n");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        view.getMiExit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alertExit = new Alert(Alert.AlertType.WARNING);
                alertExit.setTitle("Spel saven?");
                alertExit.setContentText("Wil je de huidige spel opslaan?");
                alertExit.getButtonTypes().clear();
                ButtonType ja = new ButtonType("Yes");
                ButtonType nee = new ButtonType("No");
                ButtonType cansel = new ButtonType("Cancel");
                alertExit.getButtonTypes().addAll(ja, nee, cansel);
                alertExit.showAndWait();
                if (alertExit.getResult().equals(ja)) {
                    try (PrintWriter pw = new PrintWriter(new BufferedWriter((new FileWriter(SAVEDIR
                            + view.getLblGebruiker().getText() + ".txt"))))) {
                        pw.write(view.getLblGebruiker().getText() + "\n");
                        for (int i = 0; i < MAX_TILE_X; i++) {
                            for (int j = 0; j < MAX_TILE_Y; j++) {
                                pw.write(view.getTileValue(i, j).getText() + "\n");
                            }
                        }
                        model.inlezenScores();
                        model.scoreOpslaan();
                        Platform.exit();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SpelException se) {
                        alertExit = new Alert(Alert.AlertType.ERROR);
                        alertExit.setTitle("Saven mislukt!");
                        alertExit.setContentText(se.getMessage());
                        alertExit.showAndWait();
                    }
                } else if (alertExit.getResult().equals(nee)) {
                    Platform.exit();
                } else if (alertExit.getResult().equals(cansel)) {
                    event.consume();
                }
            }
        });
    }

    private void updateView() {
        addRandomTile();
        addRandomTile();
        model.naamInlezen();
        view.getLblGebruiker().setText(model.getNaam());
        model.inlezenScores();
        view.getLblBesteScoreGetal().setText(model.getBest());

    }

    /**
     * Hier worden de waardes op een random positie geplaatst op de grid
     */

    private void addRandomTile() {
        this.random = new Random();
        int randomGetal = model.randomTile();

        xRandom = random.nextInt(MAX_TILE_X);
        yRandom = random.nextInt(MAX_TILE_Y);
        int conditionLimit = 1;
        while (!view.getTileValue(xRandom, yRandom).getText().equals("")) {
            xRandom = random.nextInt(MAX_TILE_X);
            yRandom = random.nextInt(MAX_TILE_Y);
            if (conditionLimit >= 16) {
                break;
            }
            conditionLimit++;
        }
        if (view.getTileValue(xRandom, yRandom).getText().equals("")) {
            view.getTileValue(xRandom, yRandom).setText(Integer.toString(randomGetal));
        }
        setStyleTile();
    }

    /**
     * Deze methode bepaalt hoe de tile beweegt in de juiste richting
     * Telkens afhankelijk van de richting die u de tile beweegt
     * zal men checken of er plaats is en als dat zo is zal het zich daar verplaatsen
     *
     * @param r dit is de richting
     */


    private void moveTiles(Richting r) {
        switch (r) {
            case BOVEN:
                for (int x = 0; x < MAX_TILE_X; x++) {
                    for (int y = 0; y < MAX_TILE_Y; y++) {
                        int checkLeeg = 0;
                        if (y == 0) {
                            continue;
                        }
                        if (y != 0) {
                            if (view.getTileValue(x, y).getText().equals(view.getTileValue(x, y - 1).getText()) &&
                                    !view.getTileValue(x, y).getText().equals("")) {

                                view.getTileValue(x, y - 1).setText(mergeTiles(view.getTileValue(x, y).getText(),
                                        view.getTileValue(x, y - 1).getText()));
                                view.getTileValue(x, y).setText("");
                                continue;
                            }
                        }
                        if (view.getTileValue(x, checkLeeg).getText().equals("")) {
                            while (view.getTileValue(x, checkLeeg).getText().equals("")) {
                                view.getTileValue(x, checkLeeg).setText(view.getTileValue(x, y).getText());
                                view.getTileValue(x, y).setText("");
                                if (checkLeeg == 3) {
                                    break;
                                }
                                checkLeeg++;
                            }
                        } else if (view.getTileValue(x, checkLeeg + 1).getText().equals("")) {
                            while (view.getTileValue(x, checkLeeg + 1).getText().equals("")) {
                                view.getTileValue(x, checkLeeg + 1).setText(view.getTileValue(x, y).getText());
                                view.getTileValue(x, y).setText("");
                                if (checkLeeg == 2) {
                                    break;
                                }
                                checkLeeg++;
                            }
                        } else {
                            while (view.getTileValue(x, checkLeeg + 2).getText().equals("")) {
                                view.getTileValue(x, checkLeeg + 2).setText(view.getTileValue(x, y).getText());
                                view.getTileValue(x, y).setText("");
                                if (checkLeeg == 1) {
                                    break;
                                }
                                checkLeeg++;
                            }
                        }
                    }
                }
                break;
            case BENEDEN:
                for (int x = 0; x < MAX_TILE_X; x++) {
                    for (int y = MAX_TILE_Y - 1; y >= 0; y--) {
                        int checkLeeg = 3;
                        if (y == 3) {
                            continue;
                        }
                        if (y != 3) {
                            if (view.getTileValue(x, y).getText().equals(view.getTileValue(x, y + 1).getText()) &&
                                    !view.getTileValue(x, y).getText().equals("")) {
                                view.getTileValue(x, y + 1).setText(mergeTiles(view.getTileValue(x, y).getText(),
                                        view.getTileValue(x, y + 1).getText()));
                                view.getTileValue(x, y).setText("");
                                continue;
                            }
                        }

                        if (view.getTileValue(x, checkLeeg).getText().equals("")) {
                            while (view.getTileValue(x, checkLeeg).getText().equals("")) {
                                view.getTileValue(x, checkLeeg).setText(view.getTileValue(x, y).getText());
                                view.getTileValue(x, y).setText("");
                                if (checkLeeg == 0) {
                                    break;
                                }
                                checkLeeg--;
                            }
                        } else if (view.getTileValue(x, checkLeeg - 1).getText().equals("")) {
                            while (view.getTileValue(x, checkLeeg - 1).getText().equals("")) {
                                view.getTileValue(x, checkLeeg - 1).setText(view.getTileValue(x, y).getText());
                                view.getTileValue(x, y).setText("");
                                if (checkLeeg == 1) {
                                    break;
                                }
                                checkLeeg--;
                            }
                        } else if (view.getTileValue(x, checkLeeg - 2).getText().equals("")) {
                            while (view.getTileValue(x, checkLeeg - 2).getText().equals("")) {
                                view.getTileValue(x, checkLeeg - 2).setText(view.getTileValue(x, y).getText());
                                view.getTileValue(x, y).setText("");
                                if (checkLeeg == 2) {
                                    break;
                                }
                                checkLeeg--;
                            }
                        }
                    }
                }
                break;
            case LINKS:
                for (int x = 0; x < MAX_TILE_X; x++) {
                    for (int y = 0; y < MAX_TILE_Y; y++) {
                        int checkLeeg = 0;
                        if (x == 0) {
                            continue;
                        }
                        if (x != 0) {
                            if (view.getTileValue(x, y).getText().equals(view.getTileValue(x - 1, y).getText()) &&
                                    !view.getTileValue(x, y).getText().equals("")) {
                                view.getTileValue(x - 1, y).setText(mergeTiles(view.getTileValue(x, y).getText(),
                                        view.getTileValue(x - 1, y).getText()));
                                view.getTileValue(x, y).setText("");
                                continue;
                            }
                        }
                        if (view.getTileValue(checkLeeg, y).getText().equals("")) {
                            while (view.getTileValue(checkLeeg, y).getText().equals("")) {
                                view.getTileValue(checkLeeg, y).setText(view.getTileValue(x, y).getText());
                                view.getTileValue(x, y).setText("");
                                if (checkLeeg == 3) {
                                    break;
                                }
                                checkLeeg++;
                            }
                        } else if (view.getTileValue(checkLeeg + 1, y).getText().equals("")) {
                            while (view.getTileValue(checkLeeg + 1, y).getText().equals("")) {
                                view.getTileValue(checkLeeg + 1, y).setText(view.getTileValue(x, y).getText());
                                view.getTileValue(x, y).setText("");
                                if (checkLeeg == 2) {
                                    break;
                                }
                                checkLeeg++;
                            }
                        } else {
                            while (view.getTileValue(checkLeeg + 2, y).getText().equals("")) {
                                view.getTileValue(checkLeeg + 2, y).setText(view.getTileValue(x, y).getText());
                                view.getTileValue(x, y).setText("");
                                if (checkLeeg == 1) {
                                    break;
                                }
                                checkLeeg++;
                            }
                        }
                    }
                }
                break;
            case RECHTS:
                for (int x = MAX_TILE_X - 1; x >= 0; x--) {
                    for (int y = 0; y < MAX_TILE_Y; y++) {
                        int checkLeeg = 3;
                        if (x == 3) {
                            continue;
                        }
                        if (x != 3) {
                            if (view.getTileValue(x, y).getText().equals(view.getTileValue(x + 1, y).getText()) &&
                                    !view.getTileValue(x, y).getText().equals("")) {

                                view.getTileValue(x + 1, y).setText(mergeTiles(view.getTileValue(x, y).getText(),
                                        view.getTileValue(x + 1, y).getText()));
                                view.getTileValue(x, y).setText("");
                                continue;
                            }
                        }

                        if (view.getTileValue(checkLeeg, y).getText().equals("")) {
                            while (view.getTileValue(checkLeeg, y).getText().equals("")) {
                                view.getTileValue(checkLeeg, y).setText(view.getTileValue(x, y).getText());
                                view.getTileValue(x, y).setText("");
                                if (checkLeeg == 0) {
                                    break;
                                }
                                checkLeeg--;
                            }
                        } else if (view.getTileValue(checkLeeg - 1, y).getText().equals("")) {
                            while (view.getTileValue(checkLeeg - 1, y).getText().equals("")) {
                                view.getTileValue(checkLeeg - 1, y).setText(view.getTileValue(x, y).getText());
                                view.getTileValue(x, y).setText("");
                                if (checkLeeg == 1) {
                                    break;
                                }
                                checkLeeg--;
                            }
                        } else {
                            while (view.getTileValue(checkLeeg - 2, y).getText().equals("")) {
                                view.getTileValue(checkLeeg - 2, y).setText(view.getTileValue(x, y).getText());
                                view.getTileValue(x, y).setText("");
                                if (checkLeeg == 2) {
                                    break;
                                }
                                checkLeeg--;
                            }
                        }
                    }
                }
                break;
        }
    }

    /**
     * Men bepaalt of de tile + de tile die ernaast is kan samengevoegd worden met zelfde waarde
     *
     * @param currentTile     Huidige tile
     * @param destinationTile Tile die ernaast is
     * @return Returneert de samengevoegde waarde
     */

    private String mergeTiles(String currentTile, String destinationTile) {
        int currentValue = Integer.parseInt(currentTile);
        int otherValue = Integer.parseInt(destinationTile);
        int scoreGetal = Integer.parseInt(view.getLblHuidigeScoreGetal().getText());
        int besteScore = Integer.parseInt(view.getLblBesteScoreGetal().getText());

        if (currentValue == otherValue) {
            otherValue += currentValue;
            scoreGetal += otherValue;
            if (otherValue == 2048) {
                model.inlezenScores();
                model.scoreOpslaan();
            }
            if (scoreGetal > besteScore) {
                besteScore = scoreGetal;
                view.getLblBesteScoreGetal().setText(Integer.toString(besteScore));
            }
        }
        model.setScore(scoreGetal);
        view.getLblHuidigeScoreGetal().setText(Integer.toString(scoreGetal));

        return Integer.toString(otherValue);
    }

    /**
     * Hier kijkt men na welke value er komt op de gridpane en geeft die de specifieke kleur
     * dat van de css komt
     */


    private void setStyleTile() {
        for (int x = 0; x < MAX_TILE_X; x++) {
            for (int y = 0; y < MAX_TILE_Y; y++) {
                if (!view.getTileValue(x, y).getText().equals("")) {
                    int value = Integer.parseInt(view.getTileValue(x, y).getText());
                    view.getTileValue(x, y).getStyleClass().add("game-tile-" + Integer.toString(value));
                    view.getTileValue(x, y).setBackground(null);
                    view.getStack(x, y).getStyleClass().add("game-tile-" + Integer.toString(value));
                }
            }
        }
    }

    /**
     * Verwijderd de style in de gridpane
     */

    private void removeStyleTile() {
        for (int x = 0; x < MAX_TILE_X; x++) {
            for (int y = 0; y < MAX_TILE_Y; y++) {
                if (!view.getTileValue(x, y).getText().equals("")) {
                    view.getTileValue(x, y).getStyleClass().clear();
                    view.getStack(x, y).getStyleClass().clear();
                }
            }
        }
    }

    private void setSave(String[] inlezing) {
        int getal = 1;
        for (int i = 0; i < MAX_TILE_X; i++) {
            for (int j = 0; j < MAX_TILE_Y; j++) {
                view.getTileValue(i, j).setText(inlezing[getal]);
                getal++;
            }
        }
        setStyleTile();
    }

    /**
     * Men checked of de waardes die horizontaal liggen, kunnen worden samengevoegd
     *
     * @return false als dit het geval is
     */

    private boolean horizontalCheck() {
        for (int x = 0; x < MAX_TILE_X; x++) {
            for (int y = 0; y < MAX_TILE_Y; y++) {
                if (y != MAX_TILE_Y - 1) {
                    String value2 = view.getTileValue(x, y + 1).getText();
                    String value1 = view.getTileValue(x, y).getText();
                    if (value1.equals("") || value2.equals("")) return false;
                    if (value1.equals(value2)) return false;
                }
            }
        }
        return true;
    }

    /**
     * Men checked of de waardes die verticaal liggen, kunnen worden samengevoegd
     *
     * @return false als dit het geval is
     */

    private boolean verticalCheck() {
        for (int x = 0; x < MAX_TILE_X; x++) {
            for (int y = 0; y < MAX_TILE_Y; y++) {
                if (x != MAX_TILE_X - 1) {
                    String value1 = view.getTileValue(x, y).getText();
                    String value2 = view.getTileValue(x + 1, y).getText();
                    if (value1.equals("") || value2.equals("")) return false;
                    if (value1.equals(value2)) return false;
                }
            }
        }
        return true;
    }

    private void youLose() {
        if (horizontalCheck() == true && verticalCheck() == true) {
            model.scoreOpslaan();
            Alert alertLose = new Alert(Alert.AlertType.CONFIRMATION);
            alertLose.setTitle("You lose!!");
            alertLose.setContentText("Wilt u terug opnieuw spelen?");
            alertLose.getButtonTypes().clear();
            ButtonType restart = new ButtonType("Restart");
            ButtonType close = new ButtonType("Close");
            alertLose.getButtonTypes().addAll(restart, close);
            alertLose.showAndWait();
            if (alertLose.getResult().equals(restart)) {
                SpelView spelView = new SpelView();
                Spel spelmodel = new Spel();
                SpelPresenter spelPresenter = new SpelPresenter(spelmodel, spelView);
                view.getScene().setRoot(spelView);

            } else if (alertLose.getResult().equals(close)) {
                Platform.exit();
            }
        }
    }

    private void youWin() {
        for (int x = 0; x < MAX_TILE_X; x++) {
            for (int y = 0; y < MAX_TILE_Y; y++) {
                if (view.getTileValue(x, y).getText().equals("2048")) {
                    try {
                        Thread.sleep(500);
                        Alert alertWin = new Alert(Alert.AlertType.CONFIRMATION);
                        alertWin.setTitle("You Win!!");
                        alertWin.setContentText("Wilt u terug opnieuw spelen?");
                        alertWin.getButtonTypes().clear();
                        ButtonType restartwin = new ButtonType("Restart");
                        ButtonType verder = new ButtonType("Verder spelen");
                        ButtonType closeWin = new ButtonType("Spel Afsluiten");
                        alertWin.getButtonTypes().addAll(restartwin, verder, closeWin);
                        alertWin.showAndWait();

                        if (alertWin.getResult().equals(restartwin)) {

                            SpelView spelView = new SpelView();
                            Spel spelmodel = new Spel();
                            SpelPresenter spelPresenter = new SpelPresenter(spelmodel, spelView);
                            view.getScene().setRoot(spelView);

                        } else if (alertWin.getResult().equals(closeWin)) {
                            Platform.exit();
                        } else if (alertWin.getResult().equals(verder)) {
                            alertWin.close();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
