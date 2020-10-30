package be.kdg.spel.view.highscore;

import be.kdg.spel.model.Highscores;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class HighscoreView extends BorderPane {
    GridPane gridPane = new GridPane();
    private Label naamKop;
    private Label scoreKop;
    private Label[] namen;
    private Label[] scores;
    private Button btnTerug;

    public HighscoreView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        naamKop = new Label("Naam");
        scoreKop = new Label("Score");
        namen = new Label[Highscores.AANTAL_HIGHSCORES];
        scores = new Label[Highscores.AANTAL_HIGHSCORES];

        btnTerug = new Button();
        Image imageRestart = new Image("images/left.png");
        btnTerug.setPadding(new Insets(20, 0, 0, 90));
        btnTerug.setGraphic(new ImageView(imageRestart));
        btnTerug.setBackground(null);

        for (int i = 0; i < Highscores.AANTAL_HIGHSCORES; i++) {
            namen[i] = new Label("");
            scores[i] = new Label("");
        }
    }

    private void layoutNodes() {
        gridPane.setGridLinesVisible(true);

        naamKop.setPadding(new Insets(2, 10, 8, 10));
        naamKop.setPrefWidth(120);
        scoreKop.setPadding(new Insets(2, 10, 8, 10));
        scoreKop.setPrefWidth(120);

        this.setCenter(gridPane);
        this.setBottom(btnTerug);

        this.setPadding(new Insets(100, 75, 100, 180));

        this.setBackground(new Background(new BackgroundFill(Color.rgb(250, 248, 239), new CornerRadii(0), new Insets(0))));

        gridPane.add(naamKop, 0, 0);
        gridPane.add(scoreKop, 1, 0);


        for (int i = 0; i < Highscores.AANTAL_HIGHSCORES; i++) {
            gridPane.add(namen[i], 0, (i + 1));
            gridPane.add(scores[i], 1, (i + 1));
            namen[i].setPadding(new Insets(5, 0, 5, 10));
            scores[i].setPadding(new Insets(5, 0, 5, 10));
        }

        DropShadow shadow = new DropShadow();
        //voegt de shadow erbij wanneer je op de knop bent
        this.btnTerug.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnTerug.setEffect(shadow);
                    }
                });
        this.btnTerug.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnTerug.setEffect(null);
                    }
                });
    }

    /**
     * Geeft de waarde van de labels
     *
     * @param i
     * @return naam of een score
     */

    Label getNaamLabels(int i) {
        return namen[i];
    }

    Label getScoreLabels(int i) {
        return scores[i];
    }

    public Button getBtnTerug() {
        return btnTerug;
    }
}
