package be.kdg.spel.view.gebruikersnaam;
import be.kdg.spel.model.Gebruikernaam;
import be.kdg.spel.model.Spel;
import be.kdg.spel.model.SpelException;
import be.kdg.spel.view.spel.SpelPresenter;
import be.kdg.spel.view.spel.SpelView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;


public class GebruikerPresenter {
    private GebruikerView view;
    private Gebruikernaam model;


    public GebruikerPresenter(GebruikerView view, Gebruikernaam model) {
        this.view = view;
        this.model = model;
        addEventHandelers();
        updateView();
    }

    /**
     * Hier word gecontroleerd of de gebruiker wel een naam heeft in gegeven
     * als u op de  button volgende klikt.
     *
     * @throws RuntimeException als de gebruiker geen naam ingeeft
     */


    public void addEventHandelers() {


        view.getBtnVolgende().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alertGeenGebruiker = new Alert(Alert.AlertType.WARNING);
                try {
                    if (!view.getTxtGebruikernaam().isEmpty()) {
                        model.setNaam(view.getTxtGebruikernaam());
                        model.onthoudNaam();
                        SpelView spelView = new SpelView();
                        Spel spel = new Spel();
                        SpelPresenter spelPresenter = new SpelPresenter(spel, spelView);
                        view.getScene().setRoot(spelView);
                    } else {
                        throw new SpelException("Geef een geldige gebruikersnaam in!");
                    }
                } catch (SpelException se) {
                    alertGeenGebruiker.setTitle("Gebruikersnaam niet geldig!");
                    alertGeenGebruiker.setContentText(se.getMessage());
                    alertGeenGebruiker.showAndWait();
                }
            }
        });
    }

    public void updateView() {

    }
}
