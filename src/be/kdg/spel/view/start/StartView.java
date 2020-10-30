package be.kdg.spel.view.start;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StartView extends BorderPane {
    private Label lblTitel;
    private Button btnStart;
    private Button btnHighscore;
    private Button btnExit;

    public StartView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        lblTitel = new Label("2048");
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        lblTitel.setEffect(ds);
        lblTitel.setFont(Font.font(100));

        btnStart = new Button("Start");
        btnStart.setMinWidth(300);
        btnStart.setMinHeight(80);
        btnStart.setAlignment(Pos.CENTER);
        btnStart.setFont(Font.font(30));

        btnHighscore = new Button("Highscore");
        btnHighscore.setMinWidth(300);
        btnHighscore.setMinHeight(80);
        btnHighscore.setFont(Font.font(30));
        btnHighscore.setAlignment(Pos.CENTER);

        btnExit = new Button("Exit");
        btnExit.setMinWidth(300);
        btnExit.setMinHeight(80);
        btnExit.setAlignment(Pos.CENTER);
        btnExit.setFont(Font.font(30));

    }

    private void layoutNodes() {
        BorderPane.setAlignment(lblTitel, Pos.TOP_CENTER);
        VBox vBoxMenu = new VBox(10, btnStart, btnHighscore, btnExit);
        this.setTop(lblTitel);
        this.setPadding(new Insets(10, 150, 10, 150));
        this.setCenter(vBoxMenu);

        //opmaak van de knoppen
        this.btnStart.setBackground(new Background(new BackgroundFill(Color.rgb(255, 184, 143), new CornerRadii(10), Insets.EMPTY)));

        this.btnHighscore.setBackground(new Background(new BackgroundFill(Color.rgb(255, 184, 143), new CornerRadii(10), Insets.EMPTY)));

        this.btnExit.setBackground(new Background(new BackgroundFill(Color.rgb(255, 184, 143), new CornerRadii(10), Insets.EMPTY)));


        DropShadow shadow = new DropShadow();
        //voegt de shadow erbij wanneer je op de knop bent
        this.btnStart.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnStart.setEffect(shadow);
                    }
                });
        //de schaduw verdwijnt wanneer je niet op de knop bent
        this.btnStart.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnStart.setEffect(null);
                    }
                });
        this.btnHighscore.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnHighscore.setEffect(shadow);
                    }
                });
        //de schaduw verdwijnt wanneer je niet op de knop bent
        this.btnHighscore.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnHighscore.setEffect(null);
                    }
                });
        this.btnExit.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnExit.setEffect(shadow);
                    }
                });
        this.btnExit.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnExit.setEffect(null);
                    }
                });


    }

    public Button getBtnStart() {
        return btnStart;
    }

    public Button getBtnHighscore() {
        return btnHighscore;
    }

    public Button getBtnExit() {
        return btnExit;
    }
}
