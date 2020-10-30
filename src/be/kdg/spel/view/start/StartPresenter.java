package be.kdg.spel.view.start;

import be.kdg.spel.model.Gebruikernaam;
import be.kdg.spel.model.Highscores;
import be.kdg.spel.view.gebruikersnaam.GebruikerPresenter;
import be.kdg.spel.view.gebruikersnaam.GebruikerView;
import be.kdg.spel.view.highscore.HighscorePresenter;
import be.kdg.spel.view.highscore.HighscoreView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;


public class StartPresenter {
    private StartView view;

    public StartPresenter(StartView view) {
        this.view = view;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
        view.getBtnExit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Alert benJeZeker = new Alert(Alert.AlertType.CONFIRMATION);
                benJeZeker.setHeaderText("Je bent toch zeker?");
                Optional<ButtonType> keuze = benJeZeker.showAndWait();
                if (keuze.get().getText().equalsIgnoreCase("CANCEL")) {
                    event.consume();
                } else if (keuze.get().getText().equalsIgnoreCase("OK")) {
                    Platform.exit();
                }
            }
        });

        view.getBtnStart().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GebruikerView gebruikerView = new GebruikerView();
                Gebruikernaam gmodel = new Gebruikernaam();
                GebruikerPresenter gebruikerPresenter =
                        new GebruikerPresenter(gebruikerView, gmodel);
                view.getScene().setRoot(gebruikerView);

            }
        });

        view.getBtnHighscore().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Highscores highscoreModel = new Highscores();
                HighscoreView highscoreView = new HighscoreView();
                HighscorePresenter highscorePresenter =
                        new HighscorePresenter(highscoreModel, highscoreView);
                view.getScene().setRoot(highscoreView);
            }
        });


    }

    private void updateView() {

    }

}
