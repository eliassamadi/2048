package be.kdg.spel.view.highscore;

import be.kdg.spel.model.Highscores;
import be.kdg.spel.view.start.StartPresenter;
import be.kdg.spel.view.start.StartView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class HighscorePresenter {
    private Highscores model;
    private HighscoreView view;

    public HighscorePresenter(Highscores model, HighscoreView view) {
        this.model = model;
        this.view = view;

        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
        view.getBtnTerug().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StartView startView = new StartView();
                StartPresenter startPresenter = new StartPresenter(startView);
                view.getScene().setRoot(startView);

            }
        });
    }


    /**
     * Vul de view met de highscores uit de tekstbestand dat opgevraagd word door de model
     */
    private void updateView() {
        model.inlezenHighscore();
        for (int i = 0; i < 10; i++) {
            view.getNaamLabels(i).setText(model.getNaam(i));
            view.getScoreLabels(i).setText(model.getScores(i));
        }
    }
}
