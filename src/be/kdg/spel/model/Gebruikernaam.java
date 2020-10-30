package be.kdg.spel.model;


import javafx.scene.control.Label;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Gebruikernaam {
    private static final String SAVEDIR = "src/be/kdg/spel/files/";
    private String naam;

    public void setNaam(String naam) {
        this.naam = naam;
    }

    /**
     * Hier word de naam van de gebruiker opgesslagen
     */

    public void onthoudNaam() {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter((new FileWriter(SAVEDIR + "players.txt"))))) {
            pw.write(naam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
