package be.kdg.spel.view.gebruikersnaam;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class GebruikerView extends BorderPane {
    private Label lblGebruiker;
    private TextField txtGebruikernaam;
    private Button btnVolgende;

    public GebruikerView() {
        initialisNodes();
        layoutNodes();
    }

    public void initialisNodes() {
        lblGebruiker = new Label("Geef jouw gebruikersnaam in:");
        lblGebruiker.setFont(Font.font(null, FontWeight.BOLD, 20));


        txtGebruikernaam = new TextField();
        txtGebruikernaam.setMinHeight(50);
        txtGebruikernaam.setAlignment(Pos.CENTER);
        txtGebruikernaam.setFont(Font.font(null, FontWeight.BOLD, 20));

        Image imageRestart = new Image("images/right.png");
        btnVolgende = new Button();
        btnVolgende.setPadding(new Insets(0, 0, 0, 115));
        btnVolgende.setGraphic(new ImageView(imageRestart));
        btnVolgende.setBackground(null);
    }

    public void layoutNodes() {

        VBox vBox = new VBox(20, lblGebruiker, txtGebruikernaam, btnVolgende);

        this.setCenter(vBox);

        this.setPadding(new Insets(200, 150, 10, 150));

        this.setBackground(new Background(new BackgroundFill(Color.rgb(250, 248, 239), new CornerRadii(0), new Insets(0))));

        DropShadow shadow = new DropShadow();
        this.btnVolgende.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnVolgende.setEffect(shadow);
                    }
                });
        this.btnVolgende.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnVolgende.setEffect(null);
                    }
                });

    }

    /**
     * Vraagt de ingvoerde naam van de TextField gebruikersnaam
     *
     * @return De naam in string
     */

    public String getTxtGebruikernaam() {
        return txtGebruikernaam.getText();
    }

    public Button getBtnVolgende() {
        return btnVolgende;
    }
}
