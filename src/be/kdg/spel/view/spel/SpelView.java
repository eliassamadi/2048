package be.kdg.spel.view.spel;


import be.kdg.spel.model.Spel;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SpelView extends BorderPane {
    private static final double TILE_HEIGHT = 90.0;
    private static final double TILE_WIDTH = 90.0;
    private static final double GRID_WIDTH = 410.0;
    private static final double GRID_HEIGHT = 410.0;
    private static final int MAX_TILES = 4;

    private GridPane valueLabels = new GridPane();

    private Label lblHuidigeScore;
    private Label lblBesteScore;
    private Label lblHuidigeScoreGetal;
    private Label lblBesteScoreGetal;
    private Label lblGebruiker;

    private Button btnRestart;
    private Button btnHighscore;
    private Button btnTerug;

    private MenuItem miLoad;
    private MenuItem miSave;
    private MenuItem miExit;


    private Label[][] lblTileValue;
    private GridPane grid;
    private StackPane[][] stack;

    public SpelView() {

        getStylesheets().add(getClass().getResource("TileStyle.css").toExternalForm());

        initialiseNodes();
        layoutNodes();
    }


    private void initialiseNodes() {
        lblBesteScore = new Label("Beste score:");
        lblHuidigeScore = new Label("Huidige score:");

        lblBesteScoreGetal = new Label("0");
        lblHuidigeScoreGetal = new Label("0");

        lblGebruiker = new Label("");
        lblGebruiker.setFont(Font.font(null, FontWeight.BOLD, 20));
        lblGebruiker.setAlignment(Pos.CENTER);

        lblTileValue = new Label[4][4];

        Image imageRestart = new Image("images/restart.png");
        btnRestart = new Button();
        btnRestart.setGraphic(new ImageView(imageRestart));
        btnRestart.setBackground(null);

        Image imageHigh = new Image("images/highscore.png");
        btnHighscore = new Button();
        btnHighscore.setGraphic(new ImageView(imageHigh));
        btnHighscore.setBackground(null);

        Image imageTerug = new Image("images/left.png");
        btnTerug = new Button();
        btnTerug.setGraphic(new ImageView(imageTerug));
        btnTerug.setBackground(null);

        grid = new GridPane();
        stack = new StackPane[4][4];

        miExit = new MenuItem("Exit");
        miLoad = new MenuItem("Load");
        miSave = new MenuItem("Save");
    }

    private void layoutNodes() {

        HBox hBoxLabels = new HBox(10, lblHuidigeScore, lblHuidigeScoreGetal, lblGebruiker, lblBesteScore, lblBesteScoreGetal);
        hBoxLabels.setAlignment(Pos.CENTER);

        HBox hBoxBtn = new HBox(5, btnTerug, btnHighscore, btnRestart);
        hBoxBtn.setAlignment(Pos.CENTER);

        VBox vBoxLabelGrid = new VBox(10, hBoxLabels, grid);
        vBoxLabelGrid.setAlignment(Pos.CENTER);

        Menu menuGame = new Menu("Game", null, miSave, miLoad, miExit);
        MenuBar menuBar = new MenuBar(menuGame);

        this.setTop(menuBar);
        this.setCenter(vBoxLabelGrid);
        this.setBottom(hBoxBtn);

        this.setBackground(new Background(new BackgroundFill(Color.rgb(250, 248, 239), new CornerRadii(0), new Insets(0))));

        btnRestart.setFocusTraversable(false);
        btnTerug.setFocusTraversable(false);
        btnHighscore.setFocusTraversable(false);

        this.setPadding(new Insets(0, 0, 20, 0));

        grid.setPrefWidth(GRID_WIDTH);
        grid.setMaxWidth(GRID_WIDTH);

        grid.setPrefHeight(GRID_HEIGHT);
        grid.setMaxHeight(GRID_HEIGHT);

        grid.setHgap(10);
        grid.setVgap(10);

        grid.setBackground(new Background(new BackgroundFill(Color.rgb(187, 173, 160), new CornerRadii(15.0), new Insets(0))));
        grid.setPadding(new Insets(10));

        DropShadow shadow = new DropShadow();

        //voegt de shadow erbij wanneer je op de knop bent

        this.btnRestart.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnRestart.setEffect(shadow);
                    }
                });
        this.btnRestart.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnRestart.setEffect(null);
                    }
                });

        valueLabels.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


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

        this.btnHighscore.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnHighscore.setEffect(shadow);
                    }
                });
        this.btnHighscore.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnHighscore.setEffect(null);
                    }
                });



        displayGrid();


    }

    private void displayGrid() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                stack[x][y] = new StackPane();
                stack[x][y].setMinHeight(TILE_HEIGHT);
                stack[x][y].setMinWidth(TILE_WIDTH);
                stack[x][y].setBackground(new Background(new BackgroundFill(Color.rgb(238, 228, 218), new CornerRadii(10), new Insets(0))));
                lblTileValue[x][y] = new Label();
                stack[x][y].getChildren().add(lblTileValue[x][y]);
                grid.add(stack[x][y], x, y);
            }
        }
    }

    private Rectangle createTile() {
        Rectangle tile = new Rectangle(TILE_WIDTH, TILE_HEIGHT);
        tile.setFill(Color.rgb(238, 228, 218));

        tile.setArcWidth(15.0);
        tile.setArcHeight(15.0);
        return tile;
    }

    GridPane getGrid() {
        return grid;
    }

    StackPane getStack(int x, int y) {
        return stack[x][y];
    }

    Label getTileValue(int x, int y) {
        return lblTileValue[x][y];
    }

    Label getLblHuidigeScoreGetal() {
        return lblHuidigeScoreGetal;
    }

    Button getBtnRestart() {
        return btnRestart;
    }

    Button getBtnHighscore() {
        return btnHighscore;
    }

    Label getLblGebruiker() {
        return lblGebruiker;
    }

    MenuItem getMiLoad() {
        return miLoad;
    }

    MenuItem getMiExit() {
        return miExit;
    }

    Label getLblBesteScoreGetal() {
        return lblBesteScoreGetal;
    }

    Button getBtnTerug() {
        return btnTerug;
    }

    MenuItem getMiSave() {
        return miSave;
    }
}
