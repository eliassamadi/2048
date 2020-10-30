package be.kdg.spel.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Highscores {
    public final static int AANTAL_HIGHSCORES = 10;
    private static final String SAVEDIR = "src/be/kdg/spel/files/";
    private String[] naam = new String[10];
    private String[] scores = new String[10];

    /**
     * Hier wordt telkens de gebruikersnaam samen met de highscore in de bestand ingelezen
     * Telkens wordt de naam en de highscre met een "-" gescheiden.
     */

    public void inlezenHighscore() {
        try {
            BufferedReader br = new BufferedReader(new FileReader((SAVEDIR + "highscores.txt")));
            String lijn = br.readLine();
            int i = 0;

            while (lijn != null) {
                String[] gegeven = lijn.split("-");
                naam[i] = gegeven[0];
                scores[i] = gegeven[1];
                i++;
                lijn = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNaam(int i) {
        return naam[i];
    }

    public String getScores(int i) {
        return scores[i];
    }
}
